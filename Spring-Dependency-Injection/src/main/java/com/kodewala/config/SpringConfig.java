package com.kodewala.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.kodewala.bean.Address;
import com.kodewala.bean.Employee;

/*
 🔹 @Configuration
 - Marks this class as Spring configuration class
 - Spring will read this class to create beans
*/
@Configuration

/*
 🔹 @ComponentScan
 - Tells Spring where to look for @Component classes
 - It will scan Employee class if it has @Component
*/
@ComponentScan("com.kodewala.bean")
public class SpringConfig {

    /*
     🔹 @Bean for Address
     - Creates Address object manually
     - This object is managed by Spring container
    */
    @Bean
    public Address address() {
        return new Address("Ecity", "Bangalore");
    }
    
    /*
     🔹 @Bean for Employee
     - Manually creating Employee bean
     - Passing Address object using constructor (constructor injection)
    */
    @Bean
    public Employee employee() {
        return new Employee(address()); // injecting Address bean
    }
}