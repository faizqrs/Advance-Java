package com.zepto.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.zepto.product.entity.ProductEntity;
import com.zepto.product.request.ProductRequest;
import com.zepto.product.request.ProductResponse;
import com.zepto.product.service.IProduct;

@Controller
public class ProductController {

    @Autowired
    private IProduct iProduct;

    @PostMapping("uploadProduct")
    public String uploadProduct(@ModelAttribute ProductRequest productRequest, Model model) {

        // Save the product and set confirmation response
        ProductResponse productResponse = iProduct.createProduct(productRequest);
        model.addAttribute("response", productResponse);

        //  Fetch all products and pass to admin table
        List<ProductEntity> products = iProduct.getAllProducts();
        model.addAttribute("products", products);

        return "product-confirm";
    }
}