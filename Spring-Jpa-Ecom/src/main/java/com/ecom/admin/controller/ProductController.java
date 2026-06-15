package com.ecom.admin.controller;

import com.ecom.admin.request.ProductRequest;
import com.ecom.admin.response.ProductResponse;
import com.ecom.admin.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@Controller
@RequestMapping("/seller/product")
public class ProductController {

	
	
    @Autowired
    private ProductService productService;

    // List all products
    @GetMapping("/list")
    public String listProducts(Model model) {
        List<ProductResponse> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "admin/product-list";
    }

    // Show blank create form
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("productRequest", new ProductRequest());
        model.addAttribute("isEdit", false);
        return "admin/product-form";
    }

    // Submit create form
    @PostMapping("/save")
    public String saveProduct(@ModelAttribute("productRequest") ProductRequest request,
                              Model model) {

        productService.saveProduct(request);

        model.addAttribute("productRequest", request); // to show data on success page

        return "admin/success";
    }
    
    // Show pre-filled edit form
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") String id, Model model) {
        ProductResponse product = productService.getProductById(id);
        ProductRequest request = new ProductRequest();
        request.setId(product.getId());
        request.setName(product.getName());
        request.setDescription(product.getDescription());
        request.setBrand(product.getBrand());
        request.setCategory(product.getCategory());
        request.setStatus(product.getStatus());
        request.setPrice(product.getPrice());
        request.setCurrencyCode(product.getCurrencyCode());
        request.setAvailableQty(product.getAvailableQty());
        model.addAttribute("productRequest", request);
        model.addAttribute("isEdit", true);
        return "admin/product-form";
    }

    // Submit update form
    @PostMapping("/update")
    public String updateProduct(@ModelAttribute("productRequest") ProductRequest request) {
        productService.updateProduct(request);
        return "redirect:/admin/product/list";
    }

    // Delete product
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") String id) {
        productService.deleteProduct(id);
        return "redirect:/admin/product/list";
    }
}
