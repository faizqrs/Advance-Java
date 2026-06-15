package com.ecom.user.service;

import java.math.BigDecimal;

public interface PaymentService {
	String createPayment(String userId,String sessionId, String method);
    public BigDecimal calculateTotal(String sessionId);
    
}