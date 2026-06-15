package com.ecom.user.controller;

import com.ecom.user.service.PaymentService;
import com.ecom.user.entity.CartItem;
import com.ecom.user.service.CartService;
import com.ecom.user.service.OrderService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private CartService cartService;
    
    //  Show payment page
    @GetMapping("/checkout")
    public String checkout(HttpSession session, Model model) {

        List<CartItem> items = cartService.getCartItems(session.getId());

        BigDecimal total = paymentService.calculateTotal(session.getId());

        model.addAttribute("items", items);
        model.addAttribute("total", total);

        return "user/payment";
    }

    //  Process payment
    @PostMapping("/pay")
    public String processPayment(@RequestParam("method") String method,
                                 HttpSession session,
                                 Model model) {

        //  get total before payment
        BigDecimal total = paymentService.calculateTotal(session.getId());

        String paymentId = paymentService.createPayment(session.getId(), method);

        orderService.placeOrder(session.getId(), paymentId);

        //  send data to JSP
        model.addAttribute("method", method);
        model.addAttribute("amount", total);

        return "user/order-success";
    }
}