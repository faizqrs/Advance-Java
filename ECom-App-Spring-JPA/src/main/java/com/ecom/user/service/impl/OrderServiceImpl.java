package com.ecom.user.service.impl;

import com.ecom.user.entity.Cart;
import com.ecom.user.entity.CartItem;
import com.ecom.user.entity.Order;
import com.ecom.user.entity.OrderItem;
import com.ecom.user.repository.CartItemRepository;
import com.ecom.user.repository.CartRepository;
import com.ecom.user.repository.OrderItemRepository;
import com.ecom.user.repository.OrderRepository;
import com.ecom.user.service.OrderService;
import com.ecom.seller.entity.ProductInventory;
import com.ecom.seller.entity.ProductPrice;
import com.ecom.seller.repository.ProductInventoryRepository;
import com.ecom.seller.repository.ProductPriceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductInventoryRepository inventoryRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ProductPriceRepository priceRepository;

    @Override
    public void placeOrder(String sessionId,
                           String paymentId,
                           String method,
                           String upiId,
                           String cardNumber,
                           String expiry,
                           String cvv) {

        //  PAYMENT VALIDATION

        // UPI validation
        if ("UPI".equals(method) && (upiId == null || upiId.isBlank())) {
            throw new RuntimeException("UPI ID is required");
        }

        // CARD validation
        if ("CARD".equals(method)) {

            if (cardNumber == null || cardNumber.isBlank()) {
                throw new RuntimeException("Card number is required");
            }

            if (expiry == null || expiry.isBlank()) {
                throw new RuntimeException("Expiry date is required");
            }

            if (cvv == null || cvv.isBlank()) {
                throw new RuntimeException("CVV is required");
            }
        }

        //  FETCH CART
        Cart cart = cartRepository.findBySessionIdAndStatus(sessionId, "ACTIVE");

        if (cart == null) {
            throw new RuntimeException("Cart not found");
        }

        List<CartItem> items = cartItemRepository.findByCartId(cart.getId());

        if (items.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        //  CREATE ORDER
        Order order = new Order();
        order.setSessionId(sessionId);
        order.setStatus("PLACED");
        order.setPaymentId(paymentId);

        // store payment info
        order.setPaymentMethod(method);
        order.setUpiId(upiId);

        orderRepository.save(order);

        //  PROCESS ITEMS
        for (CartItem item : items) {

            ProductInventory inventory = inventoryRepository
                    .findByProductId(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("Inventory not found"));

            if (inventory.getAvailableQty() < item.getQuantity()) {
                throw new RuntimeException("Not enough stock for product: " + item.getProductId());
            }

            // reduce stock
            inventory.setAvailableQty(
                    inventory.getAvailableQty() - item.getQuantity()
            );
            inventoryRepository.save(inventory);

            // fetch price
            ProductPrice price = priceRepository
                    .findByProductIdAndStatus(item.getProductId(), "ACTIVE")
                    .orElseThrow(() -> new RuntimeException("Price not found"));

            // save order item
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(order.getId());
            orderItem.setProductId(item.getProductId());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPriceAtPurchase(price.getPrice());

            orderItemRepository.save(orderItem);
        }

        //  CLEAR CART
        cartItemRepository.deleteByCartId(cart.getId());
    }
}