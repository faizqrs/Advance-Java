package com.ecom.seller.entity;

import java.util.UUID;

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

	    @PrePersist
	    public void generateId() {
	        if (this.id == null) {
	            this.id = UUID.randomUUID().toString();
	        }
	    }

	    // Getters and setters
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
	}


