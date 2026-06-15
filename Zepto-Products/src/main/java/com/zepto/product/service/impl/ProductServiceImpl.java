package com.zepto.product.service.impl;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zepto.product.entity.PriceEntity;
import com.zepto.product.entity.ProductEntity;
import com.zepto.product.repository.ProductRepository;
import com.zepto.product.request.ProductRequest;
import com.zepto.product.request.ProductResponse;
import com.zepto.product.service.IProduct;

@Service
public class ProductServiceImpl implements IProduct {

    @Autowired
    ProductRepository productRepository;

    @Transactional
    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {

        String productName        = productRequest.getProductName();
        String productQty         = productRequest.getProductQty();
        String productDescription = productRequest.getProductDescription();
        String productPrice       = productRequest.getProductPrice();
        String productSoldBy      = productRequest.getSoldBy();
        String priceType          = productRequest.getCurrencyType();

        System.out.println("Received From Seller: " + productName + " " + productQty
                + " " + productDescription + " " + productPrice + " " + productSoldBy);

        String productId = java.util.UUID.nameUUIDFromBytes(productName.getBytes())
                .toString()
                .replace("-", "")
                .substring(0, 8)
                .toUpperCase();

        // create price entity object
        PriceEntity priceEntity = new PriceEntity();
        priceEntity.setCurrencyType(priceType);
        priceEntity.setPrice(productPrice);
        
        ProductEntity product = new ProductEntity();
        product.setName(productName);
        product.setStatus("CREATED");
        product.setProductId(productId);
        
        priceEntity.setProductEntity(product);
        // set price entity
        product.setPrice(priceEntity);
        ProductEntity saved = productRepository.save(product);

        ProductResponse productResponse = new ProductResponse();

        if (saved != null) {
            productResponse.setProductId(String.valueOf(saved.getId()));
            productResponse.setConfirmationMsg(
                    "Your Product has Uploaded Successfully. It will be live soon.");
        } else {
            productResponse.setProductId(null);
            productResponse.setConfirmationMsg("Unable to upload product!");
        }

        return productResponse;
    }

    @Transactional
    @Override
    public String checkProductStatus(int productId) {
        return productRepository.findById(productId)
                .map(ProductEntity::getStatus)
                .orElse("Product not found");
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductEntity> getAllProducts() {
        return productRepository.findAll();
    }

    @Transactional
    @Override
    public String updateProductStatus(int id, String status, String note) {
        productRepository.updateProductStatus(id, status, note);
        return "Status Updated";
    }
}