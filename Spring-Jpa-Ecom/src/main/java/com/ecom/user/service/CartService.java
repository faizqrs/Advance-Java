package com.ecom.user.service;

import com.ecom.user.entity.Cart;
import com.ecom.user.entity.CartItem;

import java.util.List;

public interface CartService {

    Cart getOrCreateCart(String userId,
                         String sessionId);

    void addToCart(String userId,
                   String sessionId,
                   String productId,
                   int qty);

    List<CartItem> getCartItems(String userId,
                                String sessionId);

    void clearCart(String userId,
                   String sessionId);
}