package com.productcategory.entity;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;

/**
 * Entity class mapped to the "category" table in the database.
 *
 * Represents a product category which can contain multiple products.
 * Uses a bidirectional One-To-Many relationship with {@link Product}.
 *
 * Table    : category
 * Relation : One Category → Many Products
 */
@Entity
@Table(name = "category")
public class Category {

    /**
     * Auto-generated primary key for the category record.
     * GenerationType.AUTO lets Hibernate choose the strategy
     * based on the database (usually SEQUENCE or IDENTITY for MySQL).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    /** Name of the category (e.g., "Electronics", "Groceries") */
    @Column(name = "category_name")
    private String categoryName;

    /** Brief description of the category */
    private String description;

    /** Current status of the category (e.g., "ACTIVE", "INACTIVE") */
    private String status;

    /**
     * One-To-Many relationship with Product entity.
     *
     * mappedBy   : "category" — the foreign key is managed by the Product side
     * cascade    : ALL — any operation on Category (save, delete) cascades to Products
     * fetch      : EAGER — products are loaded immediately with the category
     *
     * Note: EAGER fetch can impact performance with large product lists.
     *       Consider switching to LAZY fetch in production.
     */
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Product> products = new ArrayList<>();

    // Getters
    public int getId() { return id; }
    public String getCategoryName() { return categoryName; }
    public String getDescription() { return description; }
    public String getStatus() { return status; }
    public List<Product> getProducts() { return products; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
    public void setDescription(String description) { this.description = description; }
    public void setStatus(String status) { this.status = status; }
    public void setProducts(List<Product> products) { this.products = products; }
}