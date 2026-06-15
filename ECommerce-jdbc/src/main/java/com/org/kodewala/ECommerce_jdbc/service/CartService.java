package com.org.kodewala.ECommerce_jdbc.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.org.kodewala.ECommerce_jdbc.entities.Product;
import com.org.kodewala.ECommerce_jdbc.exception.EmptyCartException;
import com.org.kodewala.ECommerce_jdbc.exception.OutOfStockException;
import com.org.kodewala.ECommerce_jdbc.exception.ProductNotFoundException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * CartService
 * 
 * This service handles all cart related operations:
 * 1. View cart
 * 2. Add product to cart
 * 3. Remove product from cart
 * 4. Update product quantity in cart
 */
public class CartService {

	private static final Logger logger = LogManager.getLogger(CartService.class);

	/**
	 * Displays all items present in the customer's cart
	 */
	public void seeCart(Connection connection, int customerId) throws EmptyCartException {

	    logger.info("Fetching cart for customerId: {}", customerId);

	    String query = "SELECT p.product_id, p.product_name, c.quantity, p.price " + 
	                   "FROM cart c " +
	                   "JOIN products p ON c.product_id = p.product_id " + 
	                   "WHERE c.customer_id = ?";

	    try (PreparedStatement stmt = connection.prepareStatement(query)) {

	        stmt.setInt(1, customerId);

	        try (ResultSet rs = stmt.executeQuery()) {

	            boolean isCartEmpty = true;
	            double grandTotal = 0.0;

	            while (rs.next()) {

	                if (isCartEmpty) {

	                    System.out.println("\n===============================================================");
	                    System.out.println("                          YOUR CART                            ");
	                    System.out.println("===============================================================");

	                    System.out.printf("%-10s | %-25s | %-8s | %-12s%n",
	                            "Product ID", "Product Name", "Quantity", "Total Price");

	                    System.out.println("---------------------------------------------------------------");

	                    isCartEmpty = false;
	                }

	                int productId = rs.getInt(1);
	                String name = rs.getString(2);
	                int qty = rs.getInt(3);
	                double price = rs.getDouble(4);

	                double itemTotal = price * qty;
	                grandTotal += itemTotal;

	                logger.debug("Cart item: productId={}, name={}, qty={}", productId, name, qty);

	                System.out.printf("%-10d | %-25s | %-8d | $%-11.2f%n",
	                        productId, name, qty, itemTotal);
	            }

	            if (isCartEmpty) {
	                logger.warn("Cart is empty for customerId: {}", customerId);
	                throw new EmptyCartException("Your cart is empty. Time to do some shopping!");
	            } else {

	                System.out.println("===============================================================");
	                System.out.printf("%48s: $%.2f%n", "GRAND TOTAL", grandTotal);
	                System.out.println("===============================================================");

	                logger.info("Cart displayed successfully for customerId: {}", customerId);
	            }
	        }

	    } catch (SQLException e) {
	        System.out.println("Database error occurred while fetching your cart.");
	        logger.error("Database error while fetching cart", e);
	    }
	}

	/**
	 * Adds a product to the cart
	 */
	public void addProductToCart(List<Product> products, Scanner sc,
	                             Connection connection, int customerId)
	        throws ProductNotFoundException, OutOfStockException, SQLException {

	    System.out.print("Type the product id you want to add to cart: ");
	    int productId = sc.nextInt();

	    logger.info("Adding product {} to cart for customer {}", productId, customerId);

	    ProductService productService = new ProductService();
	    Product product = productService.getProductById(products, productId);

	    System.out.print("Enter quantity you want to add to cart: ");
	    int quantity = sc.nextInt();

	    int currentCartQty = 0;

	    String checkCartSql = "SELECT quantity FROM cart WHERE customer_id = ? AND product_id = ?";

	    try (PreparedStatement checkStmt = connection.prepareStatement(checkCartSql)) {

	        checkStmt.setInt(1, customerId);
	        checkStmt.setInt(2, product.getProductId());

	        try (ResultSet rs = checkStmt.executeQuery()) {

	            if (rs.next()) {
	                currentCartQty = rs.getInt("quantity");
	            }
	        }
	    }

	    if ((currentCartQty + quantity) > product.getStock()) {
	        logger.warn("Out of stock for product {}. Requested={}, Available={}",
	                product.getProductId(), quantity, product.getStock());

	        throw new OutOfStockException("Cannot add to cart. We only have "
	                + product.getStock() + " in stock.");
	    }

	    String sql = "INSERT INTO cart (customer_id, product_id, quantity) VALUES (?, ?, ?) "
	            + "ON DUPLICATE KEY UPDATE quantity = quantity + ?";

	    try (PreparedStatement stmt = connection.prepareStatement(sql)) {

	        stmt.setInt(1, customerId);
	        stmt.setInt(2, product.getProductId());
	        stmt.setInt(3, quantity);
	        stmt.setInt(4, quantity);

	        int result = stmt.executeUpdate();

	        if (result > 0) {
	            System.out.println("Cart updated successfully!!");
	            logger.info("Cart updated successfully for customer {}", customerId);
	        }
	    }
	}

	/**
	 * Removes a product from the cart
	 */
	public void removeProductFromCart(Scanner sc, Connection connection, int customerId)
			throws ProductNotFoundException {

		System.out.print("Type the product id you want to remove from cart: ");
		int productId = sc.nextInt();

		logger.info("Removing product {} from cart for customer {}", productId, customerId);

		String deleteQuery = "DELETE FROM cart WHERE customer_id = ? AND product_id = ?";

		try (PreparedStatement pstmt = connection.prepareStatement(deleteQuery)) {

			pstmt.setInt(1, customerId);
			pstmt.setInt(2, productId);

			int rowsAffected = pstmt.executeUpdate();

			if (rowsAffected == 0) {

				logger.warn("Product {} not found in cart for customer {}", productId, customerId);
				throw new ProductNotFoundException("Product not found in cart, please try again");

			} else {

				System.out.println("Product successfully removed from your cart.");
				logger.info("Product {} removed from cart", productId);
			}

		} catch (SQLException e) {

			System.out.println("Database error occurred while trying to remove the product.");
			logger.error("Database error while removing product from cart", e);
		}
	}

	/**
	 * Updates the quantity of a product in the cart
	 */
	public void updateProductQuantityInCart(List<Product> products, Scanner sc,
	                                        Connection connection, int customerId)
	        throws ProductNotFoundException, OutOfStockException {

	    System.out.print("Type the product id you want to update quantity in cart: ");
	    int productId = sc.nextInt();

	    logger.info("Updating cart quantity for product {} for customer {}", productId, customerId);

	    ProductService productService = new ProductService();
	    Product product = productService.getProductById(products, productId);

	    System.out.print("Type the new quantity: ");
	    int qty = sc.nextInt();

	    if (qty > product.getStock()) {

	        logger.warn("Requested quantity {} exceeds stock {} for product {}",
	                qty, product.getStock(), productId);

	        throw new OutOfStockException("Cannot update cart. We only have "
	                + product.getStock() + " available in stock.");
	    }

	    String updateQuery = "UPDATE cart SET quantity = ? WHERE customer_id = ? AND product_id = ?";

	    try (PreparedStatement pstmt = connection.prepareStatement(updateQuery)) {

	        pstmt.setInt(1, qty);
	        pstmt.setInt(2, customerId);
	        pstmt.setInt(3, productId);

	        int rowsAffected = pstmt.executeUpdate();

	        if (rowsAffected == 0) {

	            logger.warn("Product {} not found in cart during update", productId);
	            throw new ProductNotFoundException("Product not found in cart, please try again.");

	        } else {

	            System.out.println("Cart updated successfully with the new quantity.");
	            logger.info("Cart quantity updated successfully for product {}", productId);
	        }

	    } catch (SQLException e) {

	        System.out.println("Database error occurred while trying to update the product quantity.");
	        logger.error("Database error while updating cart quantity", e);
	    }
	}
}