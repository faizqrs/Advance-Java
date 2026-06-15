package com.ecom.seller.service;


import com.ecom.seller.request.ProductRequest;
import com.ecom.seller.response.ProductResponse;
import java.util.List;

public interface ProductService {
    void saveProduct(ProductRequest request);

    List<ProductResponse> getAllProducts();

    List<ProductResponse> getAvailableProducts(); 

    ProductResponse getProductById(String id);

    void updateProduct(ProductRequest request);

    void deleteProduct(String id);
}