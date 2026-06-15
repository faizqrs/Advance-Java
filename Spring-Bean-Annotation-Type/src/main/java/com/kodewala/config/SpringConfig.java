package com.kodewala.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Spring Configuration Class
 * Used to configure Spring using annotations instead of XML
 */
@Configuration  // Marks this class as a Spring configuration class
@ComponentScan("com.kodewala.pojo")  
// Tells Spring to scan this package for components (@Component, @Service, etc.)
public class SpringConfig {

}