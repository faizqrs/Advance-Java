package com.ecom.seller.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "product_inventory")
public class ProductInventory {

    @Id
    @Column(name = "id", length = 36)
    private String id;

    @Column(name = "product_id", nullable = false)
    private String productId;

    @Column(name = "available_qty", nullable = false)
    private int availableQty = 0;

    @Column(name = "status", nullable = false)
    private String status = "ACTIVE";

//    @PrePersist is used to execute logic just before inserting a new record into the database.”
    
    @PrePersist
    public void generateId() {
        if (this.id == null) {
            this.id = UUID.randomUUID().toString();
        }
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }
    public int getAvailableQty() { return availableQty; }
    public void setAvailableQty(int availableQty) { this.availableQty = availableQty; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}