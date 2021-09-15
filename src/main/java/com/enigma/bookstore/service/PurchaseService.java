package com.enigma.bookstore.service;

import com.enigma.bookstore.entity.Purchase;

public interface PurchaseService {
    Purchase transaction(Purchase purchase);
}
