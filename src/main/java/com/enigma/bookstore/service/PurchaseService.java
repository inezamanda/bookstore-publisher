package com.enigma.bookstore.service;

import com.enigma.bookstore.entity.Purchase;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface PurchaseService {
    void transaction(Purchase purchase) throws JsonProcessingException;
}
