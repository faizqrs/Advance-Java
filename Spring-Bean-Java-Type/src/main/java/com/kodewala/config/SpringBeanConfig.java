package com.kodewala.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.kodewala.pojo.AccountInfo;

/**
 * This class is used for defining Spring Beans manually using Java configuration.
 * Instead of XML configuration, we use annotations here.
 */

@Configuration  // Marks this class as a configuration class for Spring
public class SpringBeanConfig {

    /**
     * This method creates and returns an AccountInfo object.
     * Spring will manage this object as a Bean.
     * 
     * @return AccountInfo object
     */
    
    @Bean("acc1")  // Defines a bean with name "acc1" in Spring container
    public AccountInfo createAccount() {
        
        // Creating object of AccountInfo
        AccountInfo accountInfo = new AccountInfo();
        
        // Setting properties of the object
        accountInfo.setAccountName("Faisal");
        accountInfo.setAccountType("saving");
        
        // Returning object → Spring will store it as a Bean
        return accountInfo;
    }
    
}