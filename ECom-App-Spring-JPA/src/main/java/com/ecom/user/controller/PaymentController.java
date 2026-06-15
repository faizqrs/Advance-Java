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
    public String pay(@RequestParam("method") String method,
                      @RequestParam(value = "upiId", required = false) String upiId,
                      @RequestParam(value = "cardNumber", required = false) String cardNumber,
                      @RequestParam(value = "expiry", required = false) String expiry,
                      @RequestParam(value = "cvv", required = false) String cvv,
                      HttpSession session,
                      Model model) {

        try {
            //  FIX: store payment method in session
            session.setAttribute("method", method);

            //  STEP 1: CREATE PAYMENT
            String paymentId = paymentService.createPayment(session.getId(), method);

            System.out.println("PAYMENT CREATED: " + paymentId);

            //  STEP 2: PLACE ORDER
            orderService.placeOrder(
                    session.getId(),
                    paymentId,
                    method,
                    upiId,
                    cardNumber,
                    expiry,
                    cvv
            );

            //  Redirect to success page
            return "redirect:/order/success";

        } catch (RuntimeException ex) {

            // ❗ Reload data for UI
            List<CartItem> items = cartService.getCartItems(session.getId());
            BigDecimal total = paymentService.calculateTotal(session.getId());

            model.addAttribute("items", items);
            model.addAttribute("total", total);
            model.addAttribute("error", ex.getMessage());

            return "user/payment";
        }
    }
}