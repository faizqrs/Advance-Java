package com.ecom.admin.service.impl;

import com.ecom.admin.entity.Product;
import com.ecom.admin.entity.ProductInventory;
import com.ecom.admin.entity.ProductPrice;
import com.ecom.admin.repository.ProductInventoryRepository;
import com.ecom.admin.repository.ProductRepository;
import com.ecom.admin.request.ProductRequest;
import com.ecom.admin.response.ProductResponse;
import com.ecom.admin.service.ProductService;
import com.ecom.user.repository.CartItemRepository;
import com.ecom.user.repository.OrderItemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductInventoryRepository productInventoryRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    // 🔥 SAVE PRODUCT
    @Override
    public void saveProduct(ProductRequest request) {

        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setBrand(request.getBrand());
        product.setCategory(request.getCategory());
        product.setStatus(request.getStatus() != null ? request.getStatus() : "ACTIVE");

        // INVENTORY
        ProductInventory inventory = new ProductInventory();
        inventory.setProduct(product);
        inventory.setAvailableQty(request.getAvailableQty());
        inventory.setStatus("ACTIVE");

        // PRICE
        ProductPrice price = new ProductPrice();
        price.setProduct(product);
        price.setPrice(request.getPrice());
        price.setCurrencyCode(request.getCurrencyCode() != null ? request.getCurrencyCode() : "INR");
        price.setStatus("ACTIVE");

        List<ProductPrice> prices = new ArrayList<>();
        prices.add(price);

        product.setInventory(inventory);
        product.setPrices(prices);

        productRepository.save(product);
    }

    // 🔥 GET ALL PRODUCTS
    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> getAllProducts() {

        List<Product> products = (List<Product>) productRepository.findAll();
        List<ProductResponse> responses = new ArrayList<>();

        for (Product product : products) {
            responses.add(buildResponse(product));
        }

        return responses;
    }

    // 🔥 GET AVAILABLE PRODUCTS
    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> getAvailableProducts() {

        List<Product> products = (List<Product>) productRepository.findAll();
        List<ProductResponse> responses = new ArrayList<>();

        for (Product product : products) {

            ProductInventory inventory = product.getInventory();

            if (inventory != null && inventory.getAvailableQty() > 0) {
                responses.add(buildResponse(product));
            }
        }

        return responses;
    }

    // 🔥 GET PRODUCT BY ID
    @Override
    @Transactional(readOnly = true)
    public ProductResponse getProductById(String id) {

        return productRepository.findById(id)
                .map(this::buildResponse)
                .orElse(null);
    }

    // 🔥 UPDATE PRODUCT
    @Override
    public void updateProduct(ProductRequest request) {

        Optional<Product> optional = productRepository.findById(request.getId());
        if (optional.isEmpty()) return;

        Product product = optional.get();

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setBrand(request.getBrand());
        product.setCategory(request.getCategory());
        product.setStatus(request.getStatus());

        // 🔥 FIX: GET ACTIVE PRICE FROM LIST
        ProductPrice price = product.getPrices()
                .stream()
                .filter(p -> "ACTIVE".equals(p.getStatus()))
                .findFirst()
                .orElse(null);

        if (price != null) {
            price.setPrice(request.getPrice());
            price.setCurrencyCode(request.getCurrencyCode());
        }

        // INVENTORY
        ProductInventory inventory = product.getInventory();
        if (inventory != null) {
            inventory.setAvailableQty(request.getAvailableQty());
        }

        productRepository.save(product);
    }

    // 🔥 DELETE PRODUCT
    @Override
    public void deleteProduct(String id) {

        cartItemRepository.deleteCartItemsByProductId(id);
        orderItemRepository.deleteOrderItemsByProductId(id);

        productRepository.deleteById(id);
    }

    // 🔥 BUILD RESPONSE
    private ProductResponse buildResponse(Product product) {

        ProductResponse response = new ProductResponse();

        response.setId(product.getId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setBrand(product.getBrand());
        response.setCategory(product.getCategory());
        response.setStatus(product.getStatus());

        // 🔥 FIX: ACTIVE PRICE
        ProductPrice price = product.getPrices()
                .stream()
                .filter(p -> "ACTIVE".equals(p.getStatus()))
                .findFirst()
                .orElse(null);

        if (price != null) {
            response.setPrice(price.getPrice());
            response.setCurrencyCode(price.getCurrencyCode());
        }

        // INVENTORY
        ProductInventory inventory = product.getInventory();
        if (inventory != null) {
            response.setAvailableQty(inventory.getAvailableQty());
        }

        return response;
    }
}