package com.org.kodewala.ECommerce_jdbc.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.org.kodewala.ECommerce_jdbc.entities.Customer;
import com.org.kodewala.ECommerce_jdbc.exception.CustomerNotFoundException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * CustomerService
 * 
 * Handles all operations related to customers:
 * 1. Fetch customers from database
 * 2. Display customers
 * 3. Find customer by ID
 */
public class CustomerService {

	private static final Logger logger = LogManager.getLogger(CustomerService.class);

	/**
	 * Fetch all customers from the database
	 */
	public List<Customer> getCustomersFromDatabase(Connection connection) {

		logger.info("Fetching customers from database");

		List<Customer> customers = new ArrayList<>();

		try {

			String query = "SELECT * FROM customers";

			PreparedStatement stmt = connection.prepareStatement(query);

			ResultSet resultSet = stmt.executeQuery();

			while (resultSet.next()) {

				int id = resultSet.getInt(1);
				String firstName = resultSet.getString(2);
				String lastName = resultSet.getString(3);
				String address = resultSet.getString(4);
				String phoneNumber = resultSet.getString(5);

				Customer customer =
						new Customer(id, firstName, lastName, address, phoneNumber);

				customers.add(customer);
			}

			logger.info("Total customers fetched: {}", customers.size());

		} catch (SQLException e) {

			logger.error("Database error while fetching customers", e);
		}

		return customers;
	}

	/**
	 * Display all customers in formatted table
	 */
	public List<Customer> showAllCustomers(Connection connection) {

	    List<Customer> customers = getCustomersFromDatabase(connection);

	    logger.info("Displaying {} customers", customers.size());

	    System.out.println("\n=============================================");
	    System.out.println("                CUSTOMER LIST                ");
	    System.out.println("=============================================");

	    System.out.printf("%-15s | %-25s%n", "Customer ID", "Customer Name");
	    System.out.println("---------------------------------------------");

	    for (Customer customer : customers) {

	        String fullName =
	                customer.getFirstName() + " " + customer.getLastName();

	        System.out.printf("%-15d | %-25s%n",
	                customer.getCustomerId(),
	                fullName);
	    }

	    System.out.println("=============================================\n");

	    return customers;
	}

	/**
	 * Find a customer from list by ID
	 */
	public Customer getCustomer(List<Customer> customers, int customerId)
			throws CustomerNotFoundException {

		logger.info("Searching for customer with ID: {}", customerId);

		Customer customer = customers.stream()
				.filter(cust -> cust.getCustomerId() == customerId)
				.findFirst()
				.orElse(null);

		if (customer == null) {

			logger.warn("Customer not found for ID: {}", customerId);

			throw new CustomerNotFoundException(
					"Customer not found!! Please try again");
		}

		logger.info("Customer found: {}", customerId);

		return customer;
	}
}