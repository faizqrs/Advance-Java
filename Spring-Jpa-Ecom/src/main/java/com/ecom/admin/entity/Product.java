package com.ecom.admin.entity;

import java.util.UUID;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @Column(name = "id", length = 36)
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "brand")
    private String brand;

    @Column(name = "category")
    private String category;

    @Column(name = "status", nullable = false)
    private String status = "ACTIVE";

    // ✅ INVENTORY (still 1-1)
    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ProductInventory inventory;

    // 🔥 FIX: ONE PRODUCT → MANY PRICES
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductPrice> prices;

    // OTHER RELATIONS
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<com.ecom.user.entity.CartItem> cartItems;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<com.ecom.user.entity.OrderItem> orderItems;

    @PrePersist
    public void generateId() {
        if (this.id == null) {
            this.id = UUID.randomUUID().toString();
        }
    }

    // Getters & Setters

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public ProductInventory getInventory() { return inventory; }
    public void setInventory(ProductInventory inventory) { this.inventory = inventory; }

    public List<ProductPrice> getPrices() { return prices; }
    public void setPrices(List<ProductPrice> prices) { this.prices = prices; }

    public List<com.ecom.user.entity.CartItem> getCartItems() { return cartItems; }
    public void setCartItems(List<com.ecom.user.entity.CartItem> cartItems) { this.cartItems = cartItems; }

    public List<com.ecom.user.entity.OrderItem> getOrderItems() { return orderItems; }
    public void setOrderItems(List<com.ecom.user.entity.OrderItem> orderItems) { this.orderItems = orderItems; }
}