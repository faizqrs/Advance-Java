package com.kodewala.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import com.kodewala.bean.Account;
import com.kodewala.bean.Payment;

//Scope of the Bean:
//1. Singleton(Default): Only ONE instance of the bean is created in the Spring container. Shared across the entire application
//2. Prototype: New object is created every time the bean is requested
//3. Request: One bean per HTTP request
//4. Session: Same object for one user, different for other users
//5. Application: One bean per ServletContext (whole app)


/**
 * SpringConfig class is a Java-based configuration class.
 * It replaces XML configuration in Spring applications.
 */
@Configuration // Marks this class as a configuration class
@ComponentScan(basePackages = "com.kodewala.bean") 
// Enables component scanning for @Component classes in this package
public class SpringConfig {

    /**
     * Creates Account bean with id "acc1".
     * 
     * @Lazy → Bean will NOT be created at container startup.
     * It will be created only when it is first requested.
     */
    @Bean("acc1")
    @Lazy
    public Account createAccount1() {
        return new Account("Faisal", "saving");
    }

    /**
     * Creates Account bean with id "acc2".
     * 
     * This is an eager bean → created at container startup.
     */
    @Bean("acc2")
    public Account createAccount2() {
        return new Account("kodewala", "current");
    }

    /**
     * Creates Payment bean with id "pay".
     * 
     * NOTE:
     * If Payment class already has @Component,
     * then this bean definition is redundant (duplicate bean).
     */
    @Bean("pay")
    public Payment pay() {
        return new Payment();
    }
}