package com.ecom.admin.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "product_inventory")
public class ProductInventory {

    @Id
    @Column(name = "id", length = 36)
    private String id;

    // 🔥 CLEAN RELATION (FINAL)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", unique = true)
    private Product product;

    @Column(name = "available_qty", nullable = false)
    private int availableQty = 0;

    @Column(name = "status", nullable = false)
    private String status = "ACTIVE";

    @PrePersist
    public void generateId() {
        if (this.id == null) {
            this.id = UUID.randomUUID().toString();
        }
    }

    // Getters & Setters

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public int getAvailableQty() { return availableQty; }
    public void setAvailableQty(int availableQty) { this.availableQty = availableQty; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}