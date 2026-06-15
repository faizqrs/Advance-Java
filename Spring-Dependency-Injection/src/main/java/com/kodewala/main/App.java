package com.kodewala.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.kodewala.bean.Employee;
import com.kodewala.config.SpringConfig;

public class App {

	public static void main(String[] args) {
		// Start Spring container
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);

        // Get Employee bean
        Employee emp = context.getBean(Employee.class);

        // Call custom print method
        emp.showEmployeeDetails();

	}

}
