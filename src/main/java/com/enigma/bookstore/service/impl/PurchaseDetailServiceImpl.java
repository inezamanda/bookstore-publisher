package com.enigma.bookstore.service.impl;

import com.enigma.bookstore.entity.PurchaseDetail;
import com.enigma.bookstore.repository.PurchaseDetailRepository;
import com.enigma.bookstore.service.PurchaseDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchaseDetailServiceImpl implements PurchaseDetailService {

    @Autowired
    PurchaseDetailRepository purchaseDetailRepository;

    @Override
    public PurchaseDetail savePurchaseDetail(PurchaseDetail purchaseDetail) {
        return purchaseDetailRepository.save(purchaseDetail);
    }
}
