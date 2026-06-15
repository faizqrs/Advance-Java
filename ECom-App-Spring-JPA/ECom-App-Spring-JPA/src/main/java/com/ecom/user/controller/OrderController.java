package com.ecom.user.controller;

import com.ecom.user.service.OrderService;
import com.ecom.user.service.PaymentService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/place")
    public String placeOrder(@RequestParam("method") String method,
                             HttpSession session) {

        String paymentId = paymentService.createPayment(session.getId(), method);

        orderService.placeOrder(session.getId(), paymentId);

        return "user/order-success";
    }
}