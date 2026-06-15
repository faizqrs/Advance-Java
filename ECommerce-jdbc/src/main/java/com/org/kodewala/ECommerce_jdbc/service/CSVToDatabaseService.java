package com.org.kodewala.ECommerce_jdbc.service;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * CSVToDatabaseService
 *
 * This service reads CSV files and loads the data into the database.
 * It is mainly used for initial data population.
 *
 * Features:
 * - Reads CSV files using OpenCSV
 * - Skips header rows
 * - Uses batch insert for better performance
 */
public class CSVToDatabaseService {

	private static final Logger logger = LogManager.getLogger(CSVToDatabaseService.class);

	private static final String PRODUCTS_CSV_FILE_PATH =
			"C:\\Users\\RCS\\Downloads\\products.csv";

	private static final String CUSTOMERS_CSV_FILE_PATH =
			"C:\\Users\\RCS\\Downloads\\customers.csv";

	/**
	 * Reads entire CSV file and returns all rows
	 */
	public static List<String[]> readAllDataAtOnce(String file) {

		List<String[]> allData = null;

		logger.info("Reading CSV file: {}", file);

		try (FileReader filereader = new FileReader(file);
			 CSVReader csvReader =
					 new CSVReaderBuilder(filereader)
							 .withSkipLines(1)
							 .build()) {

			allData = csvReader.readAll();
			logger.info("CSV file loaded successfully: {}", file);

		} catch (IOException | CsvException e) {

			logger.error("Error reading CSV file: {}", file, e);
		}

		return allData;
	}

	/**
	 * Reads product CSV file and inserts data into products table
	 */
	public static void processProductsCSV(Connection connection) {

		logger.info("Starting product CSV import");

		List<String[]> csvData = readAllDataAtOnce(PRODUCTS_CSV_FILE_PATH);

		if (csvData == null || csvData.isEmpty()) {
			logger.warn("No product data found in CSV.");
			return;
		}

		String sql =
				"INSERT IGNORE INTO products (product_id, product_name, price, stock) VALUES (?, ?, ?, ?)";

		try (PreparedStatement smt = connection.prepareStatement(sql)) {

			int batchSize = 50;
			int count = 0;

			for (String[] row : csvData) {

				smt.setInt(1, Integer.parseInt(row[0]));
				smt.setString(2, row[1]);
				smt.setDouble(3, Double.parseDouble(row[2]));
				smt.setInt(4, Integer.parseInt(row[3]));

				smt.addBatch();
				count++;

				if (count % batchSize == 0) {

					logger.debug("Executing batch insert for {} product records", batchSize);

					smt.executeBatch();
					smt.clearBatch();
				}
			}

			smt.executeBatch();

			logger.info("Product batch executed successfully");

		} catch (SQLException e) {

			logger.error("Database error while importing products", e);
		}
	}

	/**
	 * Reads customer CSV file and inserts data into customers table
	 */
	public static void processCustomersCSV(Connection connection) {

		logger.info("Starting customer CSV import");

		List<String[]> csvData = readAllDataAtOnce(CUSTOMERS_CSV_FILE_PATH);

		if (csvData == null || csvData.isEmpty()) {
			logger.warn("No customer data found in CSV.");
			return;
		}

		String sql =
				"INSERT IGNORE INTO customers (customer_id, first_name, last_name, address, phone_number) VALUES (?, ?, ?, ?, ?)";

		try (PreparedStatement smt = connection.prepareStatement(sql)) {

			int batchSize = 50;
			int count = 0;

			for (String[] row : csvData) {

				smt.setInt(1, Integer.parseInt(row[0]));
				smt.setString(2, row[1]);
				smt.setString(3, row[2]);
				smt.setString(4, row[3]);
				smt.setString(5, row[4]);

				smt.addBatch();
				count++;

				if (count % batchSize == 0) {

					logger.debug("Executing batch insert for {} customer records", batchSize);

					smt.executeBatch();
					smt.clearBatch();
				}
			}

			smt.executeBatch();

			logger.info("Customer batch executed successfully");

		} catch (SQLException e) {

			logger.error("Database error while importing customers", e);
		}
	}
}