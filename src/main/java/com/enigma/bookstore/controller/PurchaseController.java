package com.enigma.bookstore.controller;

import com.enigma.bookstore.entity.Purchase;
import com.enigma.bookstore.service.PurchaseService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PurchaseController {
    @Autowired
    PurchaseService purchaseService;

    @PostMapping("/transaction")
    public String saveTransaction(@RequestBody Purchase purchase) throws JsonProcessingException {
        purchaseService.transaction(purchase);

        return "Transaksi berhasil dilakukan";
    }
}
