package com.org.kodewala.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.org.kodewala.pojo.Account;





/**
 * SpringConfig class is used for Java-based configuration.
 * It replaces the traditional XML configuration in Spring.
 */
@Configuration // Marks this class as a Spring configuration class
@ComponentScan(basePackages = "com.org.kodewala.pojo") 
// Tells Spring to scan the specified package for @Component classes
public class SpringConfig {

    /**
     * Creates an Account bean with name "acc1".
     * @Primary indicates that this bean will be given priority
     * when multiple beans of the same type are present.
     */
    @Primary // default bean
    @Bean("acc1")
    public Account createAccount() {
        return new Account("Faisal", "Saving");
    }

    /**
     * Creates another Account bean with name "acc2".
     * This will NOT be the default bean because @Primary is not used here.
     */
    @Bean("acc2")
    public Account createAccount2() {
        return new Account("Kodewala", "Current");
    }
}

