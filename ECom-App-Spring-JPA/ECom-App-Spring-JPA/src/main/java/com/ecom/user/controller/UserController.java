package com.ecom.user.controller;

import com.ecom.seller.response.ProductResponse;
import com.ecom.seller.service.ProductService;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private ProductService productService;

    //  1. Show all products (User Home)
    @GetMapping("/search")
    public String showAllProducts(Model model) {

        List<ProductResponse> products = productService.getAllProducts();

        model.addAttribute("products", products);

        return "user/product-search";
    }

    //  2. Search products (simple filter)
    @GetMapping("/search/filter")
    public String searchProducts(@RequestParam("keyword") String keyword,
                                 Model model) {

        List<ProductResponse> products = productService.getAllProducts()
                .stream()
                .filter(p -> p.getName().toLowerCase().contains(keyword.toLowerCase()))
                .toList();

        model.addAttribute("products", products);
        model.addAttribute("keyword", keyword);

        return "user/product-search";
    }

    //  3. View product detail
    @GetMapping("/product/{id}")
    public String viewProduct(@PathVariable("id") String id,
                             Model model) {

        ProductResponse product = productService.getProductById(id);

        if (product == null) {
            return "redirect:/user/search";
        }

        model.addAttribute("product", product);

        return "user/product-detail";
    }

    //  4. Redirect to cart (optional helper)
    @GetMapping("/cart")
    public String redirectToCart() {
        return "redirect:/cart/view";
    }
}