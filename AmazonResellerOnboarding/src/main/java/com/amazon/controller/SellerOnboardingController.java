package com.amazon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.amazon.request.OnboardingRequest;

@Controller
public class SellerOnboardingController {

    // This mapping handles requests like: /onboard/101
    // {id} is a dynamic value from the URL
    @RequestMapping("onboard/{id}")
    @ResponseBody
    public String doOnboarding(
            
            // Extracts 'id' from URL (e.g., /onboard/101 → id = 101)
            @PathVariable("id") int id,
            
            // Binds form data to OnboardingRequest object
            @ModelAttribute OnboardingRequest request) {

        // Printing path variable
        System.out.println("id: " + id);

        // Printing form data
        System.out.println("Seller Name: " + request.getSellerName());
        System.out.println("Seller Email: " + request.getSellerEmail());
        System.out.println("Seller Mobile: " + request.getSellerMobile());
        System.out.println("Seller Type: " + request.getSellerType());

        // Sending response back to browser
        return "Received Id: " + id + " Seller Name: " + request.getSellerName();
    }
}