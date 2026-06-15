package com.kodewala.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kodewala.product.service.IProduct;
import com.kodewala.product.entity.ProductEntity;

public class ProductStatusController {

	 @Autowired
	    private IProduct iProduct;
	 
	 @GetMapping("checkProductStatus")
	    @ResponseBody
	    public String checkStatus(@RequestParam("productId") String productId) {

	        // Convert productId from String to int for the service layer
	        int productIdint = Integer.parseInt(productId);

	        // Delegate to service layer to fetch the product's current status from DB
	        String status = iProduct.checkProductStatus(productIdint);

	        // Return plain text response directly to the browser (not a JSP view)
	        return "Status of your product is : " + status;
	    }
	 
	 @GetMapping("getAllProducts")
	    public String getAllProducts(Model model) {

	        // Fetch all ProductEntity records from the database
	        List<ProductEntity> allProducts = iProduct.getAllProducts();

	        // Add the list to the model under the key "products"
	        // Accessible in JSP via: ${products} and <c:forEach var="product" items="${products}">
	        model.addAttribute("products", allProducts);

	        // Render the product-confirm.jsp page with the populated products list
	        return "product-confirm";
	    }
	 
	 @PostMapping("updateStatus")
	    public String updateStatus(
	            @RequestParam("id") int id,
	            @RequestParam("status") String status,
	            @RequestParam("note") String note) {

	        // Delegate status update to the service layer
	        // Service → Repository → Hibernate UPDATE query → MySQL
	        iProduct.updateProductStatus(id, status, note);

	        // POST-Redirect-GET pattern:
	        // Redirect to GET /getAllProducts so the admin sees the refreshed table.
	        // This also prevents duplicate form submission if the admin refreshes the page.
	        return "redirect:/getAllProducts";
	    }
}
