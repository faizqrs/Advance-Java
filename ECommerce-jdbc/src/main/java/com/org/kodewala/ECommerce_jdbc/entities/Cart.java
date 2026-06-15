package com.org.kodewala.ECommerce_jdbc.entities;

/**
 * Cart Entity class
 * This class represents a Cart item in the e-commerce system.
 * It stores which customer added which product and in what quantity.
 */
public class Cart {

	// Unique id for the cart item
	private int cartId;

	// Id of the customer who owns the cart
	private int customerId;

	// Id of the product added to the cart
	private int productId;

	// Quantity of the product added to the cart
	private int quantity;

	// Price of the product (can be used to calculate total cost)
	private double price;

	// Name of the product (useful when displaying cart items)
	private String productName;

	/**
	 * Parameterized constructor to initialize cart fields
	 */
	public Cart(int cartId, int customerId, int productId, int quantity, double price, String productName) {
		super();
		this.cartId = cartId;
		this.customerId = customerId;
		this.productId = productId;
		this.quantity = quantity;
		this.price = price;
		this.productName = productName;
	}

	// Getter method to get cart id
	public int getCartId() {
		return cartId;
	}

	// Setter method to update cart id
	public void setCartId(int cartId) {
		this.cartId = cartId;
	}

	// Getter method to get customer id
	public int getCustomerId() {
		return customerId;
	}

	// Setter method to update customer id
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	// Getter method to get product id
	public int getProductId() {
		return productId;
	}

	// Setter method to update product id
	public void setProductId(int productId) {
		this.productId = productId;
	}

	// Getter method to get quantity
	public int getQuantity() {
		return quantity;
	}

	// Setter method to update quantity
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	// Getter method to get product price
	public double getPrice() {
		return price;
	}

	// Setter method to update product price
	public void setPrice(double price) {
		this.price = price;
	}

	// Getter method to get product name
	public String getProductName() {
		return productName;
	}

	// Setter method to update product name
	public void setProductName(String productName) {
		this.productName = productName;
	}
}