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

    // ✅ SHOW PAYMENT PAGE
    @GetMapping("/checkout")
    public String checkout(HttpSession session, Model model) {

        String userId = (String) session.getAttribute("userId");

        // 🔒 LOGIN REQUIRED
        if (userId == null) {
            return "redirect:/login";
        }

        List<CartItem> items =
                cartService.getCartItems(userId, session.getId());

        BigDecimal total =
                paymentService.calculateTotal(userId, session.getId());

        model.addAttribute("items", items);
        model.addAttribute("total", total);

        return "user/payment";
    }

    // ✅ PROCESS PAYMENT
    @PostMapping("/pay")
    public String pay(
            @RequestParam("method") String method,

            @RequestParam(value = "upiId", required = false)
            String upiId,

            @RequestParam(value = "cardNumber", required = false)
            String cardNumber,

            @RequestParam(value = "expiry", required = false)
            String expiry,

            @RequestParam(value = "cvv", required = false)
            String cvv,

            HttpSession session,
            Model model) {

        String userId = (String) session.getAttribute("userId");

        // 🔒 LOGIN REQUIRED
        if (userId == null) {
            return "redirect:/login";
        }

        try {

            // 🔥 STORE PAYMENT METHOD
            session.setAttribute("method", method);

            // 🔥 FIXED PAYMENT CALL
            String paymentId = paymentService.createPayment(
                    userId,
                    session.getId(),
                    method
            );

            System.out.println("PAYMENT CREATED: " + paymentId);

            // 🔥 PLACE ORDER
            orderService.placeOrder(
                    userId,
                    session.getId(),
                    paymentId,
                    method,
                    upiId,
                    cardNumber,
                    expiry,
                    cvv
            );

            return "redirect:/order/success";

        } catch (RuntimeException ex) {

            // 🔥 FIXED CATCH BLOCK
            List<CartItem> items =
                    cartService.getCartItems(userId, session.getId());

            BigDecimal total =
                    paymentService.calculateTotal(userId, session.getId());

            model.addAttribute("items", items);
            model.addAttribute("total", total);
            model.addAttribute("error", ex.getMessage());

            return "user/payment";
        }
    }
}