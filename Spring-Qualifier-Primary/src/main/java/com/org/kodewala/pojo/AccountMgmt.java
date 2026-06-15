package com.org.kodewala.pojo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * AccountMgmt class is a Spring-managed component.
 * It is responsible for handling Account-related operations.
 */
@Component // Marks this class as a Spring Bean
public class AccountMgmt {
    
    /**
     * Injecting Account bean using field injection.
     * 
     * @Autowired → Tells Spring to inject the dependency
     * @Qualifier("acc2") → Specifies which bean to inject
     * (because multiple Account beans exist: acc1 and acc2)
     */
    @Qualifier("acc2")
    @Autowired
    private Account account;
    
    /**
     * This method prints account details.
     */
    public void Print() {
        System.out.println("Account: " + account.getName() + " Type: " + account.getType());
    }
}