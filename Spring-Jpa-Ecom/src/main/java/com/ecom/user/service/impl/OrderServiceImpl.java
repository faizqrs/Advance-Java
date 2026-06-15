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

import com.ecom.admin.entity.Product;
import com.ecom.admin.entity.ProductInventory;
import com.ecom.admin.entity.ProductPrice;
import com.ecom.admin.repository.ProductInventoryRepository;

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

    // 🔥 resolve owner
    private String resolveOwner(String userId, String sessionId) {
        return (userId != null) ? userId : sessionId;
    }

    // 🔥 FINAL METHOD
    @Override
    public void placeOrder(String userId,
                           String sessionId,
                           String paymentId,
                           String method,
                           String upiId,
                           String cardNumber,
                           String expiry,
                           String cvv) {

        String owner = resolveOwner(userId, sessionId);

        // 🔥 PAYMENT VALIDATION

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

        // 🔥 FETCH CART
        Cart cart = cartRepository.findBySessionIdAndStatus(owner, "ACTIVE");

        if (cart == null) {
            throw new RuntimeException("Cart not found");
        }

        List<CartItem> items = cartItemRepository.findByCartId(cart.getId());

        if (items.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        // 🔥 CREATE ORDER
        Order order = new Order();

        order.setUserId(userId); // ✅ IMPORTANT FIX
        order.setSessionId(sessionId);

        order.setStatus("PLACED");
        order.setPaymentId(paymentId);

        // payment info
        order.setPaymentMethod(method);
        order.setUpiId(upiId);

        orderRepository.save(order);

        // 🔥 PROCESS ITEMS
        for (CartItem item : items) {

            Product product = item.getProduct();

            if (product == null) {
                throw new RuntimeException("Product not found");
            }

            // inventory via relation
            ProductInventory inventory = product.getInventory();

            if (inventory == null) {
                throw new RuntimeException("Inventory not found");
            }

            // stock check
            if (inventory.getAvailableQty() < item.getQuantity()) {
                throw new RuntimeException(
                        "Not enough stock for product: " + product.getName()
                );
            }

            // reduce stock
            inventory.setAvailableQty(
                    inventory.getAvailableQty() - item.getQuantity()
            );

            inventoryRepository.save(inventory);

            // 🔥 GET ACTIVE PRICE
            ProductPrice price = product.getPrices()
                    .stream()
                    .filter(p -> "ACTIVE".equals(p.getStatus()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Price not found"));

            // 🔥 CREATE ORDER ITEM
            OrderItem orderItem = new OrderItem();

            orderItem.setOrder(order);
            orderItem.setProduct(product);

            orderItem.setQuantity(item.getQuantity());
            orderItem.setPriceAtPurchase(price.getPrice());

            orderItemRepository.save(orderItem);
        }

        // 🔥 CLEAR CART
        cartItemRepository.deleteByCartId(cart.getId());

        cart.setStatus("INACTIVE");

        cartRepository.save(cart);
    }
}