package com.zepto.product.service;

import java.util.List;
import com.zepto.product.request.ProductRequest;
import com.zepto.product.request.ProductResponse;
import com.zepto.product.entity.ProductEntity;

public interface IProduct {
    ProductResponse createProduct(ProductRequest productRequest);
    String checkProductStatus(int productId);
    List<ProductEntity> getAllProducts();
    String updateProductStatus(int id, String status, String note);
}