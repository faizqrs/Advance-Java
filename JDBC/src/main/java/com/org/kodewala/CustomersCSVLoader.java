package com.org.kodewala;

// Import class to read file
import java.io.FileReader;

// Import JDBC classes for database connectivity
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

// Apache Commons CSV classes for reading CSV file easily
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class CustomersCSVLoader {

	public static void main(String[] args) {

		// Path of the CSV file containing customer data
		String file = "C:\\Users\\RCS\\Downloads\\Customers.csv";

		try {

			// Step 1: Load MySQL JDBC driver
			// This registers MySQL driver with DriverManager
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Step 2: Create connection with MySQL database
			// URL format -> jdbc:mysql://host:port/database
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/27th_oct_Batch",
					"root",
					"Faisal@1998");

			// SQL query to insert VALID records into customer_info table
			String validSql = "INSERT INTO customer_info VALUES (?,?,?,?,?,?,?,?,?,?)";

			// SQL query to insert INVALID records into customer_info_invalid table
			// This table has one extra column "reason"
			String invalidSql = "INSERT INTO customer_info_invalid VALUES (?,?,?,?,?,?,?,?,?,?,?)";

			// PreparedStatement for valid records
			PreparedStatement validPs = con.prepareStatement(validSql);

			// PreparedStatement for invalid records
			PreparedStatement invalidPs = con.prepareStatement(invalidSql);

			// Step 3: Read CSV file using Apache Commons CSV
			// withFirstRecordAsHeader() tells library that first row is header
			CSVParser parser = new CSVParser(
					new FileReader(file),
					CSVFormat.DEFAULT.withFirstRecordAsHeader());

			// Batch size determines how many records will be inserted together
			int batchSize = 50;

			// Counter to track processed records
			int count = 0;

			// Step 4: Loop through each row in CSV file
			for (CSVRecord record : parser) {

				// Read each column from CSV using header name
				String id = record.get("Customer_ID");
				String firstName = record.get("First_Name");
				String lastName = record.get("Last_Name");
				String email = record.get("Email");
				String phone = record.get("Phone");
				String city = record.get("City");
				String state = record.get("State");
				String country = record.get("Country");
				String signUpDate = record.get("Signup_Date");
				String status = record.get("Status");

				// Assume record is valid initially
				boolean valid = true;

				// If record becomes invalid, store the reason
				String reason = "";

				// Validation 1: Customer ID must be numeric
				try {
					Integer.parseInt(id);
				} catch (Exception e) {
					valid = false;
					reason = "Invalid Customer Id";
				}

				// Validation 2: First name should not be empty
				if (firstName == null || firstName.trim().isEmpty()) {
					valid = false;
					reason = "First name missing";
				}

				// Validation 3: Email must contain '@' and '.'
				if (!email.contains("@") || !email.contains(".")) {
					valid = false;
					reason = "Invalid Email";
				}

				// Validation 4: Phone number should have at least 10 digits
				if (phone.length() < 10) {
					valid = false;
					reason = "Invalid Phone Number";
				}

				// Step 5: If record is valid → insert into customer_info
				if (valid) {

					validPs.setInt(1, Integer.parseInt(id));
					validPs.setString(2, firstName);
					validPs.setString(3, lastName);
					validPs.setString(4, email);
					validPs.setString(5, phone);
					validPs.setString(6, city);
					validPs.setString(7, state);
					validPs.setString(8, country);

					// Convert String date into SQL Date
					validPs.setDate(9, java.sql.Date.valueOf(signUpDate));

					validPs.setString(10, status);

					// Add record to batch instead of executing immediately
					validPs.addBatch();

				} 
				// Step 6: If record is invalid → insert into invalid table
				else {

					invalidPs.setString(1, id);
					invalidPs.setString(2, firstName);
					invalidPs.setString(3, lastName);
					invalidPs.setString(4, email);
					invalidPs.setString(5, phone);
					invalidPs.setString(6, city);
					invalidPs.setString(7, state);
					invalidPs.setString(8, country);

					invalidPs.setDate(9, java.sql.Date.valueOf(signUpDate));

					invalidPs.setString(10, status);

					// Store reason why record is invalid
					invalidPs.setString(11, reason);

					invalidPs.addBatch();
				}

				// Increase processed record count
				count++;

				// Step 7: Execute batch after every 50 records
				if (count % batchSize == 0) {

					validPs.executeBatch();
					invalidPs.executeBatch();

					System.out.println("Batch executed for " + count + " records");
				}
			}

			// Execute remaining records in batch
			validPs.executeBatch();
			invalidPs.executeBatch();

			System.out.println("CSV Processing Completed");

			// Close CSV parser
			parser.close();

			// Close database connection
			con.close();

		} catch (Exception e) {

			// Print stack trace if any exception occurs
			e.printStackTrace();
		}
	}
}