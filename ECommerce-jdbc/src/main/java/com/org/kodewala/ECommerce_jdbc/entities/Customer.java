package com.org.kodewala.ECommerce_jdbc.entities;

/**
 * Customer Entity class
 * This class represents the Customer table in the database.
 * It stores customer personal and contact information.
 */
public class Customer {

	// Unique id of the customer
	private int customerId;

	// Customer's first name
	private String firstName;

	// Customer's last name
	private String lastName;

	// Customer's address
	private String address;

	// Customer's phone number
	private String phoneNumber;

	/**
	 * Parameterized constructor to initialize customer details
	 */
	public Customer(int customerId, String firstName, String lastName, String address, String phoneNumber) {
		super();
		this.customerId = customerId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.phoneNumber = phoneNumber;
	}

	// Getter method to get customer id
	public int getCustomerId() {
		return customerId;
	}

	// Setter method to update customer id
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	// Getter method to get customer's first name
	public String getFirstName() {
		return firstName;
	}

	// Setter method to update customer's first name
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	// Getter method to get customer's last name
	public String getLastName() {
		return lastName;
	}

	// Setter method to update customer's last name
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	// Getter method to get customer address
	public String getAddress() {
		return address;
	}

	// Setter method to update customer address
	public void setAddress(String address) {
		this.address = address;
	}

	// Getter method to get customer phone number
	public String getPhoneNumber() {
		return phoneNumber;
	}

	// Setter method to update customer phone number
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}