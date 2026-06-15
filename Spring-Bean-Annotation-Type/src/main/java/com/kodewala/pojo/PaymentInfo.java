package com.kodewala.pojo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * PaymentInfo class represents payment details.
 * This class is managed by Spring as a Bean.
 */
@Component  // Marks this class as a Spring Bean (auto-detected via component scanning)
public class PaymentInfo {

    /**
     * Injecting default value into 'amount' field
     */
    @Value("1000")
    private int amount;

    /**
     * Injecting default value into 'paymentMethod' field
     */
    @Value("UPI")
    private String paymentMethod;

    /**
     * Injecting default value into 'status' field
     */
    @Value("Success")
    private String status;

    /**
     * Getter method for amount
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Getter method for payment method
     */
    public String getPaymentMethod() {
        return paymentMethod;
    }

    /**
     * Getter method for status
     */
    public String getStatus() {
        return status;
    }
}