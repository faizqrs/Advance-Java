package com.org.kodewala;

// Import JDBC classes
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class Application {

	public static void main(String[] args) {
		
		try {
			
			// Step 1: Load MySQL JDBC driver
			// This registers the driver with DriverManager
			Class.forName("com.mysql.cj.jdbc.Driver"); 
			
			
			// Step 2: Create connection with database
			// URL format → jdbc:mysql://host:port/database
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/27th_oct_batch",
					"root",
					"Faisal@1998");
			
			
			// Step 3: SQL query with placeholders (?)
			// PreparedStatement is used to execute parameterized SQL queries
			String sql = "insert into user_info(user_name,user_id,status) values(?,?,?)";
			
			
			// Step 4: Create PreparedStatement object
			PreparedStatement ps = con.prepareStatement(sql);
			
			
			// Batch size defines how many records will be executed together
			int batchSize = 100;
			
			
			// Step 5: Loop to insert 1000 records
			for (int i = 0; i < 1000; i++) {
				
				// Set values for placeholders
				ps.setString(1, "faisal1" + i);  // user_name
				ps.setString(2, "faiz@i" + i);   // user_id
				ps.setString(3, "CREATED");      // status
				
				
				// Add query to batch instead of executing immediately
				ps.addBatch();
				
				System.out.println("adding to batch");
				
				
				// Step 6: Execute batch after every 100 records
				if (i % batchSize == 0) {
					System.out.println("Executing the batch of " + i);
					
					// Execute all queries stored in batch
					ps.executeBatch();
				}
				
				
				// Alternative way (not used here)
				// executeUpdate() inserts one record at a time
//				int result = ps.executeUpdate();
//				System.out.println(" Data Inserted " + result);
				
			}
			
		} catch (Exception e) {
			
			// Print error if any exception occurs
			e.printStackTrace();
		}

	}

}