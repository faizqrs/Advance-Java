package com.org.kodewala.ECommerce_jdbc.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.org.kodewala.ECommerce_jdbc.entities.Product;
import com.org.kodewala.ECommerce_jdbc.exception.ProductNotFoundException;

/**
 * ProductService class
 * 
 * This class contains all business logic related to products.
 * It interacts with the database and performs operations like:
 * - Fetching products
 * - Searching products
 * - Sorting products
 * - Finding product by id
 */
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ProductService {

    private static final Logger logger = LogManager.getLogger(ProductService.class);
	/**
	 * Fetch all products from the database
	 * 
	 * @param connection Database connection
	 * @return List of Product objects
	 */
	public List<Product> getProductsFromDatabase(Connection connection) {

		List<Product> products = new ArrayList<>();

		try {
			String query = "Select * from products";
			PreparedStatement stmt = connection.prepareStatement(query);

			ResultSet resultSet = stmt.executeQuery();

			// Iterating over database result and converting it into Product objects
			while (resultSet.next()) {

				int id = resultSet.getInt(1);
				String productName = resultSet.getString(2);
				double price = resultSet.getDouble(3);
				int stock = resultSet.getInt(4);

				Product product = new Product(id, productName, price, stock);

				products.add(product);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return products;
	}

	/**
	 * Find product by its ID from the product list
	 * 
	 * @param products List of products
	 * @param productId Id of product to search
	 * @return Product object
	 * @throws ProductNotFoundException if product does not exist
	 */
	public Product getProductById(List<Product> products, int productId) throws ProductNotFoundException {

		Product product = products.stream()
				.filter(pro -> pro.getProductId() == productId)
				.findFirst()
				.orElse(null);

		if (product == null) {
			throw new ProductNotFoundException("Product not found, please try again");
		}

		return product;
	}

	/**
	 * Search products by keyword (partial match)
	 * 
	 * @param products List of products
	 * @param keyWord keyword to search in product name
	 */
	public void findProductByKeyWord(List<Product> products, String keyWord) {

		products = products.stream()
				.filter(n -> n.getProductName()
				.toLowerCase()
				.contains(keyWord.toLowerCase()))
				.toList();

		if (products.isEmpty()) {
			System.out.println("No products found!!");
		} else {
			printProductList(products);
		}
	}

	/**
	 * Sort products by price
	 * 
	 * @param products list of products
	 * @param asc true = ascending order, false = descending
	 */
	public void sortProducts(List<Product> products, boolean asc) {

		if (asc) {
			products = products.stream()
					.sorted(Comparator.comparing(Product::getPrice))
					.collect(Collectors.toList());
		} else {
			products = products.stream()
					.sorted(Comparator.comparing(Product::getPrice).reversed())
					.collect(Collectors.toList());
		}

		printProductList(products);
	}

	/**
	 * Utility method to print products in table format
	 * 
	 * @param products list of products
	 */
	public void printProductList(List<Product> products) {

	    System.out.println("\n===============================================================");
	    System.out.println("                        PRODUCT CATALOG                        ");
	    System.out.println("===============================================================");

	    System.out.printf("%-10s | %-25s | %-10s | %-10s%n",
	            "Product ID", "Product Name", "In Stock", "Price");

	    System.out.println("---------------------------------------------------------------");

	    for (Product product : products) {

	        System.out.printf("%-10d | %-25s | %-10d | $%-9.2f%n",
	                product.getProductId(),
	                product.getProductName(),
	                product.getStock(),
	                product.getPrice());
	    }

	    System.out.println("===============================================================\n");
	}
}