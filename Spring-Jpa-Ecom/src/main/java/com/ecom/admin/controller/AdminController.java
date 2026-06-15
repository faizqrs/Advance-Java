package com.ecom.admin.controller;

import com.ecom.user.entity.Order;
import com.ecom.user.entity.OrderItem;
import com.ecom.user.repository.OrderRepository;
import com.ecom.user.repository.OrderItemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    // 🔥 View all orders
    @GetMapping("/orders")
    public String viewOrders(Model model) {

        List<Order> orders = (List<Order>) orderRepository.findAll();

        Map<String, List<OrderItem>> orderItemsMap = new HashMap<>();

        for (Order order : orders) {
            List<OrderItem> items = orderItemRepository.findByOrderId(order.getId());
            orderItemsMap.put(order.getId(), items);
        }

        model.addAttribute("orders", orders);
        model.addAttribute("orderItemsMap", orderItemsMap);

        return "admin/orders";
    }

    // 🔥 Update order status
    @PostMapping("/orders/update")
    public String updateOrderStatus(@RequestParam("orderId") String orderId,
                                    @RequestParam("status") String status) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(status);
        orderRepository.save(order);

        return "redirect:/admin/orders";
    }
}