package com.org.kodewala.ECommerce_jdbc;

import java.sql.Connection;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.org.kodewala.ECommerce_jdbc.entities.Customer;
import com.org.kodewala.ECommerce_jdbc.entities.Product;
import com.org.kodewala.ECommerce_jdbc.exception.CustomerNotFoundException;
import com.org.kodewala.ECommerce_jdbc.service.CSVToDatabaseService;
import com.org.kodewala.ECommerce_jdbc.service.CartService;
import com.org.kodewala.ECommerce_jdbc.service.CheckoutService;
import com.org.kodewala.ECommerce_jdbc.service.ConnectionService;
import com.org.kodewala.ECommerce_jdbc.service.CustomerService;
import com.org.kodewala.ECommerce_jdbc.service.OrderHistoryService;
import com.org.kodewala.ECommerce_jdbc.service.ProductService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Main Application class
 * 
 * This is the entry point of the e-commerce console application.
 * It handles user interaction and calls the required services.
 */
public class App {

	private static final Logger logger = LogManager.getLogger(App.class);

	public static void main(String[] args) {

		logger.info("Starting E-Commerce Application");

		// Establish database connection
		Connection connection = ConnectionService.getConnection();
		logger.info("Database connection established");

		// Load CSV data into database
		logger.info("Loading CSV data into database");
		CSVToDatabaseService.processProductsCSV(connection);
		CSVToDatabaseService.processCustomersCSV(connection);

		CustomerService customerService = new CustomerService();
		Scanner sc = new Scanner(System.in);

		System.out.println("Welcome to our e-commerce website");
		logger.info("Application started successfully");

		// Display all customers
		System.out.println("List of available customers are as follows: ");
		List<Customer> customers = customerService.showAllCustomers(connection);

		System.out.println("Please enter the customer Id before proceeding");

		Customer selectedCustomer = null;

		// Loop until a valid customer is selected
		while (true) {
			try {

				int selectedCustomerId = sc.nextInt();

				selectedCustomer = customerService.getCustomer(customers, selectedCustomerId);
				logger.info("Customer selected with ID: {}", selectedCustomerId);

				break;

			} catch (InputMismatchException e) {

				System.out.println("Wrong input!! please try again");
				logger.warn("Invalid input provided for customer ID");
				sc.nextLine();

			} catch (CustomerNotFoundException e) {

				System.out.println(e.getMessage());
				logger.warn("Customer not found: {}", e.getMessage());
			}
		}

		// Initialize services
		ProductService productService = new ProductService();
		CartService cartService = new CartService();
		CheckoutService checkoutService = new CheckoutService();
		OrderHistoryService orderHistoryService = new OrderHistoryService();

		// Fetch products from database
		List<Product> products = productService.getProductsFromDatabase(connection);
		logger.info("Products loaded from database");

		// Menu options
		System.out.println("Press 1 to see available products");
		System.out.println("Press 2 to search availble products");
		System.out.println("Press 3 to sort products by price low-high");
		System.out.println("Press 4 to sort products by price high-low");
		System.out.println("Press 5 to add product to cart");
		System.out.println("Press 6 to remove product from cart");
		System.out.println("Press 7 to update quantity of product from cart");
		System.out.println("Press 8 to check cart");
		System.out.println("Press 9 to check-out");
		System.out.println("Press 10 to show order history");
		System.out.println("Press 11 to exit");

		// Application main loop
		while (true) {

			System.out.print("Please select an option : ");

			try {

				int selectedOption = sc.nextInt();
				logger.info("User selected option: {}", selectedOption);

				switch (selectedOption) {

				case 1:
					productService.printProductList(products);
					break;

				case 2:
					String keyWord = sc.next();
					logger.info("Searching products with keyword: {}", keyWord);
					productService.findProductByKeyWord(products, keyWord);
					break;

				case 3:
					logger.info("Sorting products by price ascending");
					productService.sortProducts(products, true);
					break;

				case 4:
					logger.info("Sorting products by price descending");
					productService.sortProducts(products, false);
					break;

				case 5:
					logger.info("Adding product to cart");
					cartService.addProductToCart(products, sc, connection,
							selectedCustomer.getCustomerId());
					break;

				case 6:
					logger.info("Removing product from cart");
					cartService.removeProductFromCart(sc, connection,
							selectedCustomer.getCustomerId());
					break;

				case 7:
					logger.info("Updating product quantity in cart");
					cartService.updateProductQuantityInCart(products, sc, connection,
							selectedCustomer.getCustomerId());
					break;

				case 8:
					logger.info("Viewing cart");
					cartService.seeCart(connection, selectedCustomer.getCustomerId());
					break;

				case 9:
					logger.info("Starting checkout process");
					checkoutService.doCheckout(connection, selectedCustomer.getCustomerId());

					// Reload products to update stock after checkout
					products = productService.getProductsFromDatabase(connection);
					logger.info("Products refreshed after checkout");
					break;

				case 10:
					logger.info("Viewing order history");
					orderHistoryService.viewOrderHistory(connection,
							selectedCustomer.getCustomerId());
					break;

				case 11:
					System.out.println("Thanks for using the website!!");
					logger.info("Application terminated by user");
					connection.close();
					System.exit(0);
					break;

				default:
					System.out.println("Wrong option, please try again!");
					logger.warn("User selected invalid menu option");
					break;
				}

			} catch (InputMismatchException e) {

				System.out.println("Invalid input! Please try again.");
				logger.warn("Invalid input type entered");
				sc.nextLine();

			} catch (Exception e) {

				System.out.println(e.getMessage());
				logger.error("Unexpected error occurred", e);
			}
		}
	}
}