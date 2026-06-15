package com.org.kodewala;

// Static import for assertion method used to compare expected and actual values
import static org.junit.jupiter.api.Assertions.assertEquals;

// JUnit lifecycle and test annotations
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class CalcMainTest {

	// Static object of CalcMain class which will be used in test cases
	static CalcMain calcMain;
	
	// This method runs ONLY ONCE before all test cases start
	@BeforeAll
	public static void beforeAll() {
		System.out.println("before");
		
		// Creating object of CalcMain class
		calcMain = new CalcMain();
	}
	
	// This method runs BEFORE EVERY test case
	@BeforeEach
	public void setUpBeforeEachTestCase() {
		System.out.println("Setup");
	}
	
	
	// Test case for normal positive numbers
	@Test
	public void testAddNumber() { 
		
		int expected = 12; // expected result
		int actual = calcMain.addNumbers(5, 7); // calling method
		
		// Assertion: verifies expected and actual values
		assertEquals(expected, actual);
	}
	
	
	// Test case when first number is zero
	@Test
	public void testAddNumberFirstZero() { 
		
		int expected = 7; // expected result
		int actual = calcMain.addNumbers(0, 7);
		
		assertEquals(expected, actual);
	}
	
	
	// Test case when second number is zero
	@Test
	public void testAddNumberSecZero() { 
		
		int expected = 7;
		int actual = calcMain.addNumbers(7, 0);
		
		assertEquals(expected, actual);
	}
	
	
	// Disabled test case (will not execute during test run)
	@Disabled
	public void testAddNumberBothZero() { 
		
		int expected = 0;
		int actual = calcMain.addNumbers(0, 0);
		
		assertEquals(expected, actual);
	}
	
	
	// Test case for negative number scenario
	@Test
	public void testAddNumberNegative() { 
		
		int expected = 2;
		int actual = calcMain.addNumbers(-10, 12);
		
		assertEquals(expected, actual);
	}
}