package com.kodewala.bean;



import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

/**
 * Account class demonstrates the complete Spring Bean Lifecycle.
 * 
 * It includes:
 * - Constructor
 * - Setter Injection
 * - @PostConstruct
 * - InitializingBean
 * - Business Method
 * - @PreDestroy
 * - DisposableBean
 */
public class Account {

    private String name;
    private String type;

    /**
     * 1. Constructor
     * Called when the bean object is created by Spring container.
     */
    public Account(String _name, String _type) {
        super();
        System.out.println("1. Constructor Called...");
        this.name = _name;
        this.type = _type;
    }

    /**
     * 2. Setter Injection (optional)
     * Called only if Spring uses setter-based dependency injection.
     */
    public void setName(String name) {
        this.name = name;
        System.out.println("2. Setter Called...");
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * 3. @PostConstruct
     * Called after dependency injection is completed.
     * Used for initialization logic.
     */
    @PostConstruct
    public void init() {
        System.out.println("3. Postconstruct call...");
    }



    /**
     * Business Method
     * This is your actual application logic.
     */
    public void print() {
        System.out.println("Bean Ready " + name + " | " + type);
    }

    /**
     * 6. @PreDestroy
     * Called before bean is destroyed.
     * Used for cleanup logic.
     */
    @PreDestroy
    public void preDestroy() {
        System.out.println("6. PreDestroy Called...");
    }

}