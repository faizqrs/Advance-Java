package com.kodewala.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.kodewala.config.SpringBeanConfig;
import com.kodewala.pojo.AccountInfo;

/**
 * Main class to run the Spring application
 */
public class App {

    public static void main(String[] args) {
        
        // Creating Spring container using Java-based configuration class
        ApplicationContext context =
                new AnnotationConfigApplicationContext(SpringBeanConfig.class);

        /**
         * Fetching the bean from Spring container
         * "acc1" is the bean name defined in @Bean annotation
         */
        AccountInfo accountInfo = (AccountInfo) context.getBean("acc1");

        // Printing values from the bean
        System.out.println("Account Name : " + accountInfo.getAccountName());
        System.out.println("Account Type : " + accountInfo.getAccountType());

    }

}