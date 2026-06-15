package com.ecom.user.service;

import com.ecom.user.entity.Cart;
import com.ecom.user.entity.CartItem;

import java.util.List;

public interface CartService {

    Cart getOrCreateCart(String sessionId);

    void addToCart(String sessionId, String productId, int qty);

    List<CartItem> getCartItems(String sessionId);
}