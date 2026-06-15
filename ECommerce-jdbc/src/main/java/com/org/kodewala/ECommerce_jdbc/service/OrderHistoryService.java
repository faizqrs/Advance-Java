package com.org.kodewala.ECommerce_jdbc.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * OrderHistoryService
 * 
 * This service is responsible for fetching and displaying
 * the order history of a specific customer.
 * 
 * It retrieves:
 * 1. Order details from the orders table
 * 2. Order items using a JOIN between order_items and products
 */
public class OrderHistoryService {

	/**
	 * View the order history of a customer
	 * 
	 * @param connection  Database connection
	 * @param customerId  Customer whose order history needs to be displayed
	 */
	public void viewOrderHistory(Connection connection, int customerId) {

	    // Query to fetch orders placed by the customer
	    String orderQuery = "SELECT order_id, order_date, total_amount, status " +
	                        "FROM orders WHERE customer_id = ? ORDER BY order_date DESC";

	    // Query to fetch items for a specific order
	    String itemQuery = "SELECT p.product_name, oi.quantity, oi.price " +
	                       "FROM order_items oi JOIN products p ON oi.product_id = p.product_id " +
	                       "WHERE oi.order_id = ?";

	    try (PreparedStatement orderStmt = connection.prepareStatement(orderQuery)) {

	        // Setting customerId in prepared statement
	        orderStmt.setInt(1, customerId);

	        try (ResultSet orderRs = orderStmt.executeQuery()) {

	            boolean hasOrders = false;

	            System.out.println("\n===============================================================");
	            System.out.println("                      YOUR ORDER HISTORY                       ");
	            System.out.println("===============================================================");

	            // Iterating through all customer orders
	            while (orderRs.next()) {

	                hasOrders = true;

	                int orderId = orderRs.getInt(1);
	                String orderDate = orderRs.getString(2);
	                double totalAmount = orderRs.getDouble(3);
	                String status = orderRs.getString(4);

	                // Printing order header
	                System.out.printf("ORDER ID : #%-15d DATE  : %-20s%n", orderId, orderDate);
	                System.out.printf("STATUS   : %-15s TOTAL : $%.2f%n", status, totalAmount);

	                System.out.println("---------------------------------------------------------------");

	                // Items table header
	                System.out.printf("%-30s | %-10s | %-15s%n", "Item Name", "Quantity", "Price Each");
	                System.out.println("---------------------------------------------------------------");

	                // Fetch items belonging to this order
	                try (PreparedStatement itemStmt = connection.prepareStatement(itemQuery)) {

	                    itemStmt.setInt(1, orderId);

	                    try (ResultSet itemRs = itemStmt.executeQuery()) {

	                        while (itemRs.next()) {

	                            String productName = itemRs.getString(1);
	                            int qty = itemRs.getInt(2);
	                            double price = itemRs.getDouble(3);

	                            // Printing each order item
	                            System.out.printf("%-30s | %-10d | $%-14.2f%n",
	                                    productName, qty, price);
	                        }
	                    }
	                }

	                // Separator between orders
	                System.out.println("===============================================================");
	            }

	            // If customer has no orders
	            if (!hasOrders) {

	                System.out.println("             You have not placed any orders yet.               ");
	                System.out.println("===============================================================");
	            }

	            System.out.println(); // spacing
	        }

	    } catch (SQLException e) {

	        System.out.println("Database error occurred while fetching your order history.");
	        e.printStackTrace();
	    }
	}
}