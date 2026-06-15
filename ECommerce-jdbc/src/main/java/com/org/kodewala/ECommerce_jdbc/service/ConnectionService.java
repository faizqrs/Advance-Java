package com.org.kodewala.ECommerce_jdbc.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConnectionService {

	private static final Logger logger = LogManager.getLogger(ConnectionService.class);

	private static final String DB_URL = "jdbc:mysql://localhost:3306/ecommerce?rewriteBatchedStatements=true";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "Faisal@1998";
	
	public static Connection getConnection() {

		Connection connection = null;

		try {

			logger.info("Attempting to establish database connection");

			connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

			logger.info("Database connection established successfully");

		} catch (SQLException e) {

			logger.error("Failed to establish database connection", e);
		}

		return connection;
	}
}