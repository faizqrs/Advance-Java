package com.ecom.admin.service;

import com.ecom.admin.request.ProductRequest;
import com.ecom.admin.response.ProductResponse;
import java.util.List;

public interface ProductService {
    void saveProduct(ProductRequest request);

    List<ProductResponse> getAllProducts();

    List<ProductResponse> getAvailableProducts(); 

    ProductResponse getProductById(String id);

    void updateProduct(ProductRequest request);

    void deleteProduct(String id);
}
