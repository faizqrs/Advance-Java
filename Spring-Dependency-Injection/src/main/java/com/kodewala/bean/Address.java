package com.kodewala.bean;

/*
 🔹 Address class (POJO - Plain Old Java Object)
 - This class represents address details
 - It will be used as a dependency in Employee class
*/
public class Address {

    // 🔹 Instance variables (properties of Address)
    private String line;   // e.g. "Ecity", "Whitefield"
    private String city;   // e.g. "Bangalore"

    /*
     🔹 Constructor
     - Used to initialize object with values
     - Spring will call this when creating bean using @Bean
    */
    public Address(String _line, String _city) {
        this.line = _line;
        this.city = _city;
    }

    /*
     🔹 Getter for line
     - Used to access address line
    */
    public String getLine() {
        return line;
    }

    /*
     🔹 Setter for line
     - Used to update address line if needed
    */
    public void setLine(String line) {
        this.line = line;
    }

    /*
     🔹 Getter for city
     - Used to access city value
    */
    public String getCity() {
        return city;
    }

    /*
     🔹 Setter for city
     - Used to update city value
    */
    public void setCity(String city) {
        this.city = city;
    }
}