package com.ecom.user.service;

public interface OrderService {
    void placeOrder(String sessionId, String paymentId);
}