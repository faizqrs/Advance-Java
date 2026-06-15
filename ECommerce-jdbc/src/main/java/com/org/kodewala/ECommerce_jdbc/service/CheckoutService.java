package com.org.kodewala.ECommerce_jdbc.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.org.kodewala.ECommerce_jdbc.entities.Cart;
import com.org.kodewala.ECommerce_jdbc.exception.EmptyCartException;
import com.org.kodewala.ECommerce_jdbc.exception.OutOfStockException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * CheckoutService
 * 
 * Handles the checkout process of the e-commerce system.
 * 
 * Responsibilities:
 * 1. Fetch cart items
 * 2. Validate stock
 * 3. Create order
 * 4. Insert order items
 * 5. Reduce product stock
 * 6. Clear cart
 * 7. Commit transaction
 */
public class CheckoutService {

	private static final Logger logger = LogManager.getLogger(CheckoutService.class);

	public void doCheckout(Connection connection, int customerId)
			throws EmptyCartException, OutOfStockException, SQLException {

		logger.info("Checkout started for customerId: {}", customerId);

		List<Cart> items = new ArrayList<>();
		double totalAmount = 0.0;

		try {

			connection.setAutoCommit(false);
			logger.debug("Transaction started");

			String cartQuery =
					"SELECT c.product_id, c.quantity, p.price, p.stock, p.product_name, c.cart_id "
					+ "FROM cart c "
					+ "JOIN products p ON c.product_id = p.product_id "
					+ "WHERE c.customer_id = ?";

			try (PreparedStatement stmt = connection.prepareStatement(cartQuery)) {

				stmt.setInt(1, customerId);

				try (ResultSet rs = stmt.executeQuery()) {

					while (rs.next()) {

						int productId = rs.getInt(1);
						int qty = rs.getInt(2);
						double price = rs.getDouble(3);
						int stock = rs.getInt(4);
						String name = rs.getString(5);
						int cartId = rs.getInt(6);

						if (qty > stock) {

							logger.warn("Out of stock during checkout. Product={}, requested={}, available={}",
									name, qty, stock);

							throw new OutOfStockException(
									"Product '" + name + "' is out of stock! Only " + stock + " left.");
						}

						totalAmount += (qty * price);

						items.add(new Cart(cartId, customerId, productId, qty, price, name));

						logger.debug("Cart item loaded: productId={}, qty={}", productId, qty);
					}
				}
			}

			if (items.isEmpty()) {

				logger.warn("Checkout attempted with empty cart for customerId: {}", customerId);

				throw new EmptyCartException(
						"Your cart is empty. Please add items before checking out.");
			}

			String insertOrderSql =
					"INSERT INTO orders (customer_id, total_amount, status) VALUES (?, ?, 'COMPLETED')";

			int orderId = -1;

			try (PreparedStatement stmt = connection.prepareStatement(
					insertOrderSql, Statement.RETURN_GENERATED_KEYS)) {

				stmt.setInt(1, customerId);
				stmt.setDouble(2, totalAmount);

				stmt.executeUpdate();

				try (ResultSet keys = stmt.getGeneratedKeys()) {

					if (keys.next()) {

						orderId = keys.getInt(1);
						logger.info("Order created successfully with orderId={}", orderId);

					} else {

						logger.error("Order creation failed - no generated ID");
						throw new SQLException("Failed to create order, no ID obtained.");
					}
				}
			}

			String insertItemSql =
					"INSERT INTO order_items (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";

			String updateStockSql =
					"UPDATE products SET stock = stock - ? WHERE product_id = ?";

			try (
				PreparedStatement insertItemStmt =
						connection.prepareStatement(insertItemSql);

				PreparedStatement updateStockStmt =
						connection.prepareStatement(updateStockSql)
			) {

				for (Cart item : items) {

					insertItemStmt.setInt(1, orderId);
					insertItemStmt.setInt(2, item.getProductId());
					insertItemStmt.setInt(3, item.getQuantity());
					insertItemStmt.setDouble(4, item.getPrice());
					insertItemStmt.addBatch();

					updateStockStmt.setInt(1, item.getQuantity());
					updateStockStmt.setInt(2, item.getProductId());
					updateStockStmt.addBatch();

					logger.debug("Prepared order item insert for productId={}", item.getProductId());
				}

				insertItemStmt.executeBatch();
				updateStockStmt.executeBatch();

				logger.info("Order items inserted and stock updated for orderId={}", orderId);
			}

			String clearCartSql = "DELETE FROM cart WHERE customer_id = ?";

			try (PreparedStatement stmt = connection.prepareStatement(clearCartSql)) {

				stmt.setInt(1, customerId);
				stmt.executeUpdate();

				logger.info("Cart cleared for customerId={}", customerId);
			}

			connection.commit();
			logger.info("Transaction committed successfully for orderId={}", orderId);

			finalBill(items, totalAmount, orderId);

		} catch (Exception e) {

			connection.rollback();
			logger.error("Checkout failed. Transaction rolled back for customerId={}", customerId, e);

			throw e;

		} finally {

			connection.setAutoCommit(true);
			logger.debug("Auto-commit restored");
		}
	}

	private void finalBill(List<Cart> items, double totalAmount, int orderId) {

	    logger.info("Printing final bill for orderId={}", orderId);

	    System.out.println("\n===============================================================");
	    System.out.println("                       ORDER SUCCESSFUL!                       ");
	    System.out.println("===============================================================");
	    System.out.println("Order ID: #" + orderId);
	    System.out.println("---------------------------------------------------------------");

	    System.out.printf("%-30s | %-10s | %-15s%n", "Item Name", "Quantity", "Subtotal");
	    System.out.println("---------------------------------------------------------------");

	    for (Cart item : items) {

	        double subtotal = item.getPrice() * item.getQuantity();

	        System.out.printf("%-30s | %-10d | $%-14.2f%n",
	                item.getProductName(),
	                item.getQuantity(),
	                subtotal);
	    }

	    System.out.println("===============================================================");
	    System.out.printf("%43s: $%.2f%n", "TOTAL AMOUNT", totalAmount);
	    System.out.println("===============================================================");
	    System.out.println("                 Thanks for shopping with us!                  ");
	    System.out.println("===============================================================\n");
	}
}