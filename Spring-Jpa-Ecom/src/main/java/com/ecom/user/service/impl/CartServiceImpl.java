package com.ecom.user.service.impl;

import com.ecom.user.entity.Cart;
import com.ecom.user.entity.CartItem;
import com.ecom.user.repository.CartItemRepository;
import com.ecom.user.repository.CartRepository;
import com.ecom.user.service.CartService;
import com.ecom.admin.entity.Product;
import com.ecom.admin.entity.ProductInventory;
import com.ecom.admin.repository.ProductInventoryRepository;
import com.ecom.admin.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductInventoryRepository inventoryRepository;
    
    @Autowired
    private ProductRepository productRepository;

    // 🔥 NEW: Unified method (userId OR sessionId)
    private String resolveCartOwner(String userId, String sessionId) {
        return (userId != null) ? userId : sessionId;
    }

    // 1. Get or create cart
    @Override
    public Cart getOrCreateCart(String sessionId) {
        // backward compatible
        return getOrCreateCart(null, sessionId);
    }

    // 🔥 NEW METHOD
    public Cart getOrCreateCart(String userId, String sessionId) {

        String owner = resolveCartOwner(userId, sessionId);

        Cart cart = cartRepository.findBySessionIdAndStatus(owner, "ACTIVE");

        if (cart == null) {
            cart = new Cart();
            cart.setSessionId(owner); // using same column
            cart.setStatus("ACTIVE");
            cartRepository.save(cart);
        }

        return cart;
    }

    // 2. Add to cart
    @Override
    public void addToCart(String sessionId, String productId, int qty) {
        addToCart(null, sessionId, productId, qty);
    }

    // 🔥 NEW METHOD
    public void addToCart(String userId, String sessionId, String productId, int qty) {

        Cart cart = getOrCreateCart(userId, sessionId);

        // ✅ THIS LINE MUST EXIST (before using product)
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        ProductInventory inventory = product.getInventory();

        if (inventory == null) {
            throw new RuntimeException("Inventory not found");
        }

        CartItem item = cartItemRepository
                .findByCartIdAndProductId(cart.getId(), productId);

        int existingQty = (item != null) ? item.getQuantity() : 0;

        if (inventory.getAvailableQty() < (existingQty + qty)) {
            throw new RuntimeException("Not enough stock");
        }

        if (item != null) {
            item.setQuantity(existingQty + qty);
        } else {
            item = new CartItem();
            item.setCart(cart);
            item.setProduct(product);  // ✅ NOW product exists
            item.setQuantity(qty);
        }

        cartItemRepository.save(item);
    }
    // 3. Get cart items
    @Override
    public List<CartItem> getCartItems(String sessionId) {
        return getCartItems(null, sessionId);
    }

    // 🔥 NEW METHOD
    public List<CartItem> getCartItems(String userId, String sessionId) {

        Cart cart = getOrCreateCart(userId, sessionId);

        return cartItemRepository.findByCartId(cart.getId());
    }

    // 4. Clear cart (FIXED)
    public void clearCart(String userId, String sessionId) {

        String owner = resolveCartOwner(userId, sessionId);

        Cart cart = cartRepository.findBySessionIdAndStatus(owner, "ACTIVE");

        if (cart != null) {
            cartItemRepository.deleteByCartId(cart.getId()); // ✅ FIXED

            cart.setStatus("INACTIVE");
            cartRepository.save(cart);
        }
    }
}