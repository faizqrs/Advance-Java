package com.ecom.user.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

import com.ecom.admin.entity.Product;

@Entity
@Table(name = "order_item")
public class OrderItem {

    @Id
    @Column(length = 36)
    private String id;

    // 🔥 KEEP (temporary - read only)
    @Column(name = "order_id", nullable = false, insertable = false, updatable = false)
    private String orderId;

    @Column(name = "product_id", nullable = false, insertable = false, updatable = false)
    private String productId;

    // 🔥 NEW RELATIONS

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(nullable = false)
    private int quantity;

    @Column(name = "price_at_purchase", nullable = false)
    private BigDecimal priceAtPurchase;

    @PrePersist
    public void prePersist() {
        if (this.id == null) {
            this.id = UUID.randomUUID().toString();
        }
    }

    // Getters & Setters

    public String getId() { return id; }

    public String getOrderId() { return orderId; }

    public String getProductId() { return productId; }

    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public BigDecimal getPriceAtPurchase() { return priceAtPurchase; }
    public void setPriceAtPurchase(BigDecimal priceAtPurchase) { this.priceAtPurchase = priceAtPurchase; }
}