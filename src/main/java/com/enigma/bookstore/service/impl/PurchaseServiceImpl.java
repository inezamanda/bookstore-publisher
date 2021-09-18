package com.enigma.bookstore.service.impl;

import com.enigma.bookstore.config.KafkaConfig;
import com.enigma.bookstore.constant.ResponseMessage;
import com.enigma.bookstore.entity.Book;
import com.enigma.bookstore.entity.Member;
import com.enigma.bookstore.entity.Purchase;
import com.enigma.bookstore.entity.PurchaseDetail;
import com.enigma.bookstore.repository.PurchaseRepository;
import com.enigma.bookstore.service.BookService;
import com.enigma.bookstore.service.MemberService;
import com.enigma.bookstore.service.PurchaseDetailService;
import com.enigma.bookstore.service.PurchaseService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PurchaseServiceImpl implements PurchaseService {
    @Autowired
    PurchaseRepository purchaseRepository;

    @Autowired
    PurchaseDetailService purchaseDetailService;

    @Autowired
    BookService bookService;

    @Autowired
    MemberService memberService;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @Override
    @Transactional
    public void transaction(Purchase purchase) throws JsonProcessingException {
        Purchase purchase1 = purchaseRepository.save(purchase);

        Member member = memberService.getMemberById(purchase.getMember().getId());
        purchase1.setMember(member);

        Double grandTotal = 0.0;
        for (PurchaseDetail purchaseDetail: purchase.getPurchaseDetails()) {
            purchaseDetail.setPurchase(purchase1);
            Book book = bookService.getBookById(purchaseDetail.getBook().getId());

            if (book.getStock() == 0)
                throw new ResourceNotFoundException("Book is out of stock");

            if (book.getStock() < purchaseDetail.getQuantity()) {
                String message = String.format(ResponseMessage.BAD_REQUEST, book.getStock(), purchaseDetail.getQuantity(), book.getTitle());
                throw new ResourceNotFoundException(message);
            }

            book.setStock(book.getStock()- purchaseDetail.getQuantity());

            purchaseDetail.setPriceSell((double) (book.getPrice() * purchaseDetail.getQuantity()));
            purchaseDetail.setBook(book);
            purchaseDetailService.savePurchaseDetail(purchaseDetail);

            purchase1.setGrandTotal(grandTotal+=purchaseDetail.getPriceSell());
        }

        String response = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(purchase1);
        this.kafkaTemplate.send(KafkaConfig.TOPIC, response);
    }
}
