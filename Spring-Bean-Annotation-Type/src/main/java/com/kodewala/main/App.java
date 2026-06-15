package com.kodewala.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.kodewala.config.SpringConfig;
import com.kodewala.pojo.PaymentInfo;

/**
 * Main class to run the Spring application
 */
public class App {

    public static void main(String[] args) {

        /**
         * Creating Spring Container (IoC Container)
         * It will read configuration from SpringConfig class
         * and scan for components (@Component)
         */
        ApplicationContext context =
                new AnnotationConfigApplicationContext(SpringConfig.class);

        /**
         * Fetching the PaymentInfo bean from Spring container
         * Since we are using @Component, we can fetch it by class type
         */
        PaymentInfo paymentInfo = context.getBean(PaymentInfo.class);

        // Accessing and printing values from the bean
        System.out.println("Payment Amount: " + paymentInfo.getAmount());
        System.out.println("Payment Method: " + paymentInfo.getPaymentMethod());
        System.out.println("Payment Status: " + paymentInfo.getStatus());

    }

}