package com.rapido.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SignupController {

    // Handles request to open signup page
    // URL: /showPage
    @RequestMapping("showPage")
    public ModelAndView showSignupDetils() {

        // Creating ModelAndView object
        ModelAndView mv = new ModelAndView();

        // Setting view name (JSP page)
        mv.setViewName("sign-up"); // → sign-up.jsp

        return mv;
    }
    
    // Handles form submission
    // URL: /signup
    @RequestMapping("signup")
    public String signUp(
            
            // Extracts form field "mobile"
            @RequestParam("mobile") String mobile, 
            
            // Extracts form field "email"
            @RequestParam("email") String email,
            
            // Extracts form field "location"
            @RequestParam("location") String location,
            
            // Extracts form field "otp"
            @RequestParam("otp") String otp) {
        
        // Printing user data in console
        System.out.println("User Details: Mobile " + mobile +
                           " Email " + email +
                           " Location " + location +
                           " OTP " + otp);

        // Returning success page
        return "signup-success"; // → signup-success.jsp
    }
}