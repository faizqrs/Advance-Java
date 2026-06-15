package com.ecom.user.entity;

import jakarta.persistence.*;
import java.util.UUID;

import com.ecom.admin.entity.Product;

@Entity
@Table(name = "cart_item")
public class CartItem {

    @Id
    @Column(length = 36)
    private String id;

    // 🔥 KEEP (temporary - read only)
    @Column(name = "cart_id", nullable = false, insertable = false, updatable = false)
    private String cartId;

    @Column(name = "product_id", nullable = false, insertable = false, updatable = false)
    private String productId;

    // 🔥 NEW RELATIONS

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(nullable = false)
    private int quantity = 1;

    @PrePersist
    public void prePersist() {
        if (this.id == null) {
            this.id = UUID.randomUUID().toString();
        }
    }

    // Getters & Setters

    public String getId() { return id; }

    public String getCartId() { return cartId; }

    public String getProductId() { return productId; }

    public Cart getCart() { return cart; }
    public void setCart(Cart cart) { this.cart = cart; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}