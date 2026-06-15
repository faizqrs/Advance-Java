package com.org.kodewala.ECommerce_jdbc.entities;

/**
 * Product Entity class
 * This class represents the Product table in the database.
 * It contains product details like id, name, price and stock.
 */
public class Product {

	// Unique id of the product
	private int productId;

	// Name of the product
	private String productName;

	// Price of the product
	private double price;

	// Available stock quantity of the product
	private int stock;

	/**
	 * Parameterized constructor to initialize product fields
	 */
	public Product(int productId, String productName, double price, int stock) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.price = price;
		this.stock = stock;
	}

	// Getter method to get product id
	public int getProductId() {
		return productId;
	}

	// Setter method to update product id
	public void setProductId(int productId) {
		this.productId = productId;
	}

	// Getter method to get product name
	public String getProductName() {
		return productName;
	}

	// Setter method to update product name
	public void setProductName(String productName) {
		this.productName = productName;
	}

	// Getter method to get product price
	public double getPrice() {
		return price;
	}

	// Setter method to update product price
	public void setPrice(double price) {
		this.price = price;
	}

	// Getter method to get product stock
	public int getStock() {
		return stock;
	}

	// Setter method to update product stock
	public void setStock(int stock) {
		this.stock = stock;
	}

	/**
	 * equals() method overridden to compare products based on productId
	 * Two products are considered equal if their ids are the same
	 */
	@Override
	public boolean equals(Object obj) {
		Product product = (Product) obj;
		return this.productId == product.productId;
	}

	/**
	 * hashCode() method overridden for hash based collections
	 * Returns productId as the hash value
	 */
	@Override
	public int hashCode() {
		return this.productId;
	}

}