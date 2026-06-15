package com.kodewala.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.kodewala.product.entity.ProductEntity;
import com.kodewala.product.request.ProductRequest;
import com.kodewala.product.request.ProductResponse;
import com.kodewala.product.service.IProduct;

@Controller
public class ProductController {

	@Autowired
	private IProduct iProduct;
	
	@PostMapping("uploadProduct")
	public String uploadProduct(@ModelAttribute ProductRequest productRequest, Model model) {
		
		ProductResponse productResponse = iProduct.createProduct(productRequest);
		model.addAttribute("response", productResponse);
		
		List<ProductEntity> products = iProduct.getAllProducts();
		model.addAttribute("products", products);
		return "product-confirm";
	}
	
}
