package com.ecom.user.controller;

import com.ecom.admin.response.ProductResponse;
import com.ecom.admin.service.ProductService;
import com.ecom.user.entity.CartItem;
import com.ecom.user.service.CartService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    // ✅ ADD TO CART
    @PostMapping("/add")
    public String addToCart(
            @RequestParam("productId") String productId,

            @RequestParam("qty") int qty,

            HttpSession session) {

        // 🔥 GET USER
        String userId =
                (String) session.getAttribute("userId");

        // 🔥 FIXED METHOD
        cartService.addToCart(
                userId,
                session.getId(),
                productId,
                qty
        );

        return "redirect:/cart/view";
    }

    // ✅ VIEW CART
    @GetMapping("/view")
    public String viewCart(HttpSession session,
                           Model model) {

        // 🔥 GET USER
        String userId =
                (String) session.getAttribute("userId");

        // 🔥 FIXED METHOD
        List<CartItem> items =
                cartService.getCartItems(
                        userId,
                        session.getId()
                );

        // product list
        List<ProductResponse> products = items.stream()
                .map(item ->
                        productService.getProductById(
                                item.getProduct().getId()
                        )
                )
                .toList();

        model.addAttribute("items", items);
        model.addAttribute("products", products);

        return "user/cart";
    }

    // ✅ HANDLE ERRORS
    @ExceptionHandler(RuntimeException.class)
    public String handleException(RuntimeException ex,
                                  Model model) {

        model.addAttribute("error",
                ex.getMessage());

        return "user/error";
    }
}