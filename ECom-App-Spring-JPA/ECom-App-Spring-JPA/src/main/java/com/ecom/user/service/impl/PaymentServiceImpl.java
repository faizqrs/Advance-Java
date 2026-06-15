package com.ecom.user.service.impl;

import com.ecom.user.entity.Cart;
import com.ecom.user.entity.CartItem;
import com.ecom.user.entity.Payment;
import com.ecom.user.repository.CartItemRepository;
import com.ecom.user.repository.CartRepository;
import com.ecom.user.repository.PaymentRepository;
import com.ecom.user.service.PaymentService;
import com.ecom.seller.entity.ProductPrice;
import com.ecom.seller.repository.ProductPriceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ProductPriceRepository priceRepository;

    // ✅ COMMON METHOD (REUSABLE)
    private BigDecimal calculateCartTotal(List<CartItem> items) {

        BigDecimal total = BigDecimal.ZERO;

        for (CartItem item : items) {

            ProductPrice price = priceRepository
                    .findByProductIdAndStatus(item.getProductId(), "ACTIVE")
                    .orElseThrow(() -> new RuntimeException("Price not found"));

            BigDecimal itemTotal = price.getPrice()
                    .multiply(BigDecimal.valueOf(item.getQuantity()));

            total = total.add(itemTotal);
        }

        return total;
    }

    //  CREATE PAYMENT
    @Override
    public String createPayment(String sessionId, String method) {

        Cart cart = cartRepository.findBySessionIdAndStatus(sessionId, "ACTIVE");

        if (cart == null) {
            throw new RuntimeException("Cart not found");
        }

        List<CartItem> items = cartItemRepository.findByCartId(cart.getId());

        if (items.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        BigDecimal total = calculateCartTotal(items);

        Payment payment = new Payment();
        payment.setCartId(cart.getId());
        payment.setAmount(total);
        payment.setStatus("SUCCESS");
        payment.setMethod(method);

        paymentRepository.save(payment);

        return payment.getId();
    }

    // CALCULATE TOTAL 
    @Override
    public BigDecimal calculateTotal(String sessionId) {

        Cart cart = cartRepository.findBySessionIdAndStatus(sessionId, "ACTIVE");

        if (cart == null) {
            throw new RuntimeException("Cart not found");
        }

        List<CartItem> items = cartItemRepository.findByCartId(cart.getId());

        return calculateCartTotal(items);
    }
}