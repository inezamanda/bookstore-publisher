package com.enigma.bookstore.controller;

import com.enigma.bookstore.entity.Purchase;
import com.enigma.bookstore.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PurchaseController {
    @Autowired
    PurchaseService purchaseService;

    @PostMapping("/transaction")
    Purchase saveTransaction(@RequestBody Purchase purchase){
        return purchaseService.transaction(purchase);
    }
}
