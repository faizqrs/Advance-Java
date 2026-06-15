package com.productcategory.entity;

import jakarta.persistence.*;

/**
 * Entity class mapped to the "product" table in the database.
 *
 * Represents a product that belongs to a single category.
 * Uses a bidirectional Many-To-One relationship with {@link Category}.
 *
 * Table    : product
 * Relation : Many Products → One Category
 */
@Entity
@Table(name = "product")
public class Product {

    /**
     * Auto-generated primary key for the product record.
     * GenerationType.AUTO lets Hibernate choose the strategy
     * based on the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    /** Name of the product (e.g., "iPhone 15", "Rice 5kg") */
    private String name;

    /** Brief description of the product */
    private String description;

    /** Current status of the product (e.g., "ACTIVE", "INACTIVE") */
    private String status;

    /**
     * Many-To-One relationship with Category entity.
     *
     * fetch      : LAZY — category is loaded only when explicitly accessed,
     *              improving performance by avoiding unnecessary joins.
     * @JoinColumn : Specifies "category_id" as the foreign key column
     *               in the "product" table that references the "category" table.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getStatus() { return status; }
    public Category getCategory() { return category; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setStatus(String status) { this.status = status; }
    public void setCategory(Category category) { this.category = category; }
}