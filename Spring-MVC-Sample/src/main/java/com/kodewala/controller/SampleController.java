package com.kodewala.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SampleController {

    // Handles request for URL: /login
    @RequestMapping("/login")
    public ModelAndView viewPage() {
        
        // Just for debugging (check console)
        System.out.println("SampleController.viewPage()");
        
        // Creating ModelAndView object
        ModelAndView mv = new ModelAndView();
        
        // Setting view name (JSP page name)
        mv.setViewName("result");  // → result.jsp
        
        return mv;
    }
}