package com.ecom.user.controller;

import com.ecom.user.service.OrderService;
import com.ecom.user.service.PaymentService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PaymentService paymentService;

    // ✅ PLACE ORDER
    @PostMapping("/place")
    public String placeOrder(
            @RequestParam("method") String method,

            @RequestParam(value = "upiId", required = false)
            String upiId,

            @RequestParam(value = "cardNumber", required = false)
            String cardNumber,

            @RequestParam(value = "expiry", required = false)
            String expiry,

            @RequestParam(value = "cvv", required = false)
            String cvv,

            HttpSession session) {

        // 🔥 GET LOGGED-IN USER
        String userId = (String) session.getAttribute("userId");

        // 🔥 CREATE PAYMENT
        String paymentId = paymentService.createPayment(
                session.getId(),
                method
        );

        // 🔥 PLACE ORDER
        orderService.placeOrder(
                userId,                // ✅ IMPORTANT FIX
                session.getId(),
                paymentId,
                method,
                upiId,
                cardNumber,
                expiry,
                cvv
        );

        session.setAttribute("method", method);

        return "redirect:/order/success";
    }

    // ✅ SUCCESS PAGE
    @GetMapping("/success")
    public String orderSuccess(HttpSession session, Model model) {

        Object method = session.getAttribute("method");

        model.addAttribute("paymentMethod", method);

        return "user/order-success";
    }
}