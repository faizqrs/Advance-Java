package com.kodewala.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/*
 🔹 @Component
 - Marks this class as Spring Bean
 - Spring will automatically create object of Employee
*/
@Component
public class Employee {

    private String name;

    // 🔹 Used for constructor injection
    private Address addressConstructor;

    // 🔹 Used for setter injection
    private Address addressSetter;

    /*
     🔹 Field Injection
     - Spring directly injects Address here
     - No constructor/setter required
    */
    @Autowired
    private Address addressField;

    /*
     🔹 Constructor Injection
     - Spring injects Address bean here
     - BEST practice (recommended)
    */
    @Autowired
    public Employee(Address address) {
        this.addressConstructor = address;
        this.name = "Constructor Employee";
    }

    /*
     🔹 Setter Injection
     - Spring injects Address using setter method
     - Must have parameter (very important)
    */
    @Autowired
    public void setAddressSetter(Address addressSetter) {
        this.addressSetter = addressSetter;
    }

    /*
     🔹 Method to display all injected values
    */
    public void showEmployeeDetails() {

        System.out.println("=== Constructor Injection ===");
        System.out.println("Name: " + name);
        System.out.println("Address: " + addressConstructor.getCity() + ", "
                           + addressConstructor.getLine());

        System.out.println("=== Setter Injection ===");
        System.out.println("Name: Setter Employee");
        System.out.println("Address: " + addressSetter.getCity() + ", "
                           + addressSetter.getLine());

        System.out.println("=== Field Injection ===");
        System.out.println("Name: Field Employee");
        System.out.println("Address: " + addressField.getCity() + ", "
                           + addressField.getLine());
    }
}