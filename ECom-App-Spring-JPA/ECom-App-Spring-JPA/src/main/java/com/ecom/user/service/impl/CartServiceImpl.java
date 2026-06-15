package com.ecom.user.service.impl;

import com.ecom.user.entity.Cart;
import com.ecom.user.entity.CartItem;
import com.ecom.user.repository.CartItemRepository;
import com.ecom.user.repository.CartRepository;
import com.ecom.user.service.CartService;
import com.ecom.seller.entity.ProductInventory;
import com.ecom.seller.repository.ProductInventoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductInventoryRepository inventoryRepository;

    // 1. Get or create cart
    @Override
    public Cart getOrCreateCart(String sessionId) {

        Cart cart = cartRepository.findBySessionIdAndStatus(sessionId, "ACTIVE");

        if (cart == null) {
            cart = new Cart();
            cart.setSessionId(sessionId);
            cart.setStatus("ACTIVE");
            cartRepository.save(cart);
        }

        return cart;
    }

    //  2. Add to cart
    @Override
    public void addToCart(String sessionId, String productId, int qty) {

        Cart cart = getOrCreateCart(sessionId);

        //  Get inventory using Optional
        Optional<ProductInventory> optionalInventory =
                inventoryRepository.findByProductId(productId);

        if (optionalInventory.isEmpty()) {
            throw new RuntimeException("Product not found in inventory");
        }

        ProductInventory inventory = optionalInventory.get();

        //  Check stock
        if (inventory.getAvailableQty() < qty) {
            throw new RuntimeException("Not enough stock");
        }

        //  Check if item already exists in cart
        CartItem item = cartItemRepository
                .findByCartIdAndProductId(cart.getId(), productId);

        if (item != null) {
            item.setQuantity(item.getQuantity() + qty);
        } else {
            item = new CartItem();
            item.setCartId(cart.getId());
            item.setProductId(productId);
            item.setQuantity(qty);
        }

        cartItemRepository.save(item);
    }

    //  3. Get cart items
    @Override
    public List<CartItem> getCartItems(String sessionId) {

        Cart cart = getOrCreateCart(sessionId);

        return cartItemRepository.findByCartId(cart.getId());
    }

    //  4. Clear cart (for future use - order placement)
    public void clearCart(String sessionId) {

        Cart cart = cartRepository.findBySessionIdAndStatus(sessionId, "ACTIVE");

        if (cart != null) {
            cartItemRepository.deleteByCartId(cart.getId());
        }
    }
}