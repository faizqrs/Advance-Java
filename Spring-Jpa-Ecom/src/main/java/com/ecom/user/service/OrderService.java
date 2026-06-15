package com.ecom.user.service;

public interface OrderService {
	public void placeOrder(String userId,String sessionId,
            String paymentId,
            String method,
            String upiId,
            String cardNumber,
            String expiry,
            String cvv);
}