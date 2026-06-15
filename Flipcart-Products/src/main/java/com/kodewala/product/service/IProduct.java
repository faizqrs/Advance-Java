package com.kodewala.product.service;

import java.util.List;

import com.kodewala.product.entity.ProductEntity;
import com.kodewala.product.request.ProductRequest;
import com.kodewala.product.request.ProductResponse;

public interface IProduct {

	public ProductResponse createProduct(ProductRequest productRequest);
	public String checkProductStatus(int id);
	public List<ProductEntity> getAllProducts();
	public String updateProductStatus(int id, String status, String note);
		
	
}
