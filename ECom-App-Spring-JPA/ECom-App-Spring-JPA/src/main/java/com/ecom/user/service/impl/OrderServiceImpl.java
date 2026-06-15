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
    private CartItemRepository cartItemRepository; // 

    @Autowired
    private ProductInventoryRepository inventoryRepository; 

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ProductPriceRepository priceRepository;

    @Override
    public void placeOrder(String sessionId, String paymentId) {

        Cart cart = cartRepository.findBySessionIdAndStatus(sessionId, "ACTIVE");

        if (cart == null) {
            throw new RuntimeException("Cart not found");
        }

        List<CartItem> items = cartItemRepository.findByCartId(cart.getId());

        //  Create Order
        Order order = new Order();
        order.setSessionId(sessionId);
        order.setStatus("PLACED");
        orderRepository.save(order);

        for (CartItem item : items) {

            ProductInventory inventory = inventoryRepository
                    .findByProductId(item.getProductId())
                    .orElse(null);

            if (inventory == null || inventory.getAvailableQty() < item.getQuantity()) {
                throw new RuntimeException("Not enough stock");
            }

            //  Reduce stock
            inventory.setAvailableQty(
                    inventory.getAvailableQty() - item.getQuantity()
            );
            inventoryRepository.save(inventory);

            //  Fetch price
            ProductPrice price = priceRepository
                    .findByProductIdAndStatus(item.getProductId(), "ACTIVE")
                    .orElseThrow(() -> new RuntimeException("Price not found"));

            order.setPaymentId(paymentId);
            
            // ✅ Save OrderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(order.getId());
            orderItem.setProductId(item.getProductId());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPriceAtPurchase(price.getPrice());

            orderItemRepository.save(orderItem);
        }

        // ✅ Clear cart
        cartItemRepository.deleteByCartId(cart.getId());
    }
}