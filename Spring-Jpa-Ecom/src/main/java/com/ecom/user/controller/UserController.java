package com.ecom.user.controller;

import com.ecom.admin.response.ProductResponse;
import com.ecom.admin.service.ProductService;
import com.ecom.user.entity.Order;
import com.ecom.user.entity.OrderItem;
import com.ecom.user.repository.OrderRepository;
import com.ecom.user.repository.OrderItemRepository;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    // 1. Show all products
    @GetMapping("/search")
    public String showAllProducts(Model model) {

        List<ProductResponse> products = productService.getAvailableProducts();
        model.addAttribute("products", products);

        return "user/product-search";
    }

    // 2. Search products
    @GetMapping("/search/filter")
    public String searchProducts(@RequestParam("keyword") String keyword,
                                Model model) {

        List<ProductResponse> products = productService.getAvailableProducts()
                .stream()
                .filter(p -> p.getName().toLowerCase().contains(keyword.toLowerCase()))
                .toList();

        model.addAttribute("products", products);
        model.addAttribute("keyword", keyword);

        return "user/product-search";
    }

    // 3. View product detail
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

    // 4. Redirect to cart
    @GetMapping("/cart")
    public String redirectToCart() {
        return "redirect:/cart/view";
    }

    // ✅ Order history WITH items
    @GetMapping("/orders")
    public String orderHistory(HttpSession session, Model model) {

        String userId = (String) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/login";
        }

        List<Order> orders = orderRepository.findByUserId(userId);

        Map<String, List<OrderItem>> orderItemsMap = new HashMap<>();
        Map<String, String> productNameMap = new HashMap<>();

        for (Order order : orders) {

            List<OrderItem> items = orderItemRepository.findByOrderId(order.getId());
            orderItemsMap.put(order.getId(), items);

            for (OrderItem item : items) {
                // fetch product name
                String productName = productService
                        .getProductById(item.getProductId())
                        .getName();

                productNameMap.put(item.getProductId(), productName);
            }
        }

        model.addAttribute("orders", orders);
        model.addAttribute("orderItemsMap", orderItemsMap);
        model.addAttribute("productNameMap", productNameMap);

        return "user/order-history";
    }
}