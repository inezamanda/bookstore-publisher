package com.enigma.bookstore.service.impl;

import com.enigma.bookstore.constant.ResponseMessage;
import com.enigma.bookstore.entity.Book;
import com.enigma.bookstore.entity.Purchase;
import com.enigma.bookstore.entity.PurchaseDetail;
import com.enigma.bookstore.repository.PurchaseDetailRepository;
import com.enigma.bookstore.repository.PurchaseRepository;
import com.enigma.bookstore.service.BookService;
import com.enigma.bookstore.service.PurchaseDetailService;
import com.enigma.bookstore.service.PurchaseService;
import exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
public class PurchaseServiceImpl implements PurchaseService {
    @Autowired
    PurchaseRepository purchaseRepository;

    @Autowired
    PurchaseDetailService purchaseDetailService;

    @Autowired
    BookService bookService;

    @Override
    @Transactional
    public Purchase transaction(Purchase purchase) {
        Purchase purchase1 = purchaseRepository.save(purchase);

        for (PurchaseDetail purchaseDetail: purchase.getPurchaseDetails()) {
            purchaseDetail.setPurchase(purchase1);
            Book book = bookService.getBookById(purchaseDetail.getBook().getId());
            if (book.getStock() < purchaseDetail.getQuantity()) {
                String message = String.format(ResponseMessage.BAD_REQUEST, book.getStock(), purchaseDetail.getQuantity());
                throw new ResourceNotFoundException(message);
            }
            book.setStock(book.getStock()- purchaseDetail.getQuantity());
            bookService.updateBook(book);
            purchaseDetail.setPriceSell((double) (book.getPrice() * purchaseDetail.getQuantity()));
            purchaseDetailService.savePurchaseDetail(purchaseDetail);
        }

        return purchase1;
    }
}