package com.ecom.seller.service.impl;

import com.ecom.seller.entity.Product;
import com.ecom.seller.entity.ProductInventory;
import com.ecom.seller.entity.ProductPrice;
import com.ecom.seller.repository.ProductInventoryRepository;
import com.ecom.seller.repository.ProductPriceRepository;
import com.ecom.seller.repository.ProductRepository;
import com.ecom.seller.request.ProductRequest;
import com.ecom.seller.response.ProductResponse;
import com.ecom.seller.service.ProductService;
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
    private ProductPriceRepository productPriceRepository;

    @Autowired
    private ProductInventoryRepository productInventoryRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    //  SAVE PRODUCT 
    @Override
    public void saveProduct(ProductRequest request) {

        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setBrand(request.getBrand());
        product.setCategory(request.getCategory());
        product.setStatus(request.getStatus() != null ? request.getStatus() : "ACTIVE");
        productRepository.save(product);

        ProductPrice price = new ProductPrice();
        price.setProductId(product.getId());
        price.setPrice(request.getPrice());
        price.setCurrencyCode(request.getCurrencyCode() != null ? request.getCurrencyCode() : "INR");
        price.setStatus("ACTIVE");
        productPriceRepository.save(price);

        ProductInventory inventory = new ProductInventory();
        inventory.setProductId(product.getId());
        inventory.setAvailableQty(request.getAvailableQty());
        inventory.setStatus("ACTIVE");
        productInventoryRepository.save(inventory);
    }

    //  GET ALL PRODUCTS 
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

    //  GET AVAILABLE PRODUCTS
    // 
    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> getAvailableProducts() {

        List<Product> products = (List<Product>) productRepository.findAll();
        List<ProductResponse> responses = new ArrayList<>();

        for (Product product : products) {

            Optional<ProductInventory> optionalInventory =
                    productInventoryRepository.findByProductId(product.getId());

            if (optionalInventory.isPresent() &&
                optionalInventory.get().getAvailableQty() > 0) {

                responses.add(buildResponse(product));
            }
        }

        return responses;
    }

    //  GET PRODUCT BY ID 
    @Override
    @Transactional(readOnly = true)
    public ProductResponse getProductById(String id) {

        return productRepository.findById(id)
                .map(this::buildResponse)
                .orElse(null);
    }

    //  UPDATE PRODUCT
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

        productRepository.save(product);

        // deactivate old price
        productPriceRepository.findByProductIdAndStatus(product.getId(), "ACTIVE")
                .ifPresent(old -> {
                    old.setStatus("INACTIVE");
                    productPriceRepository.save(old);
                });

        // new price
        ProductPrice newPrice = new ProductPrice();
        newPrice.setProductId(product.getId());
        newPrice.setPrice(request.getPrice());
        newPrice.setCurrencyCode(
                request.getCurrencyCode() != null ? request.getCurrencyCode() : "INR"
        );
        newPrice.setStatus("ACTIVE");
        productPriceRepository.save(newPrice);

        // update inventory
        productInventoryRepository.findByProductId(product.getId())
                .ifPresent(inv -> {
                    inv.setAvailableQty(request.getAvailableQty());
                    productInventoryRepository.save(inv);
                });
    }

    // Delete Product
    @Override
    public void deleteProduct(String id) {

        cartItemRepository.deleteCartItemsByProductId(id);
        orderItemRepository.deleteOrderItemsByProductId(id);

        productInventoryRepository.findByProductId(id)
                .ifPresent(productInventoryRepository::delete);

        productPriceRepository.deleteAll(
                productPriceRepository.findByProductId(id)
        );

        productRepository.deleteById(id);
    }

    
    private ProductResponse buildResponse(Product product) {

        ProductResponse response = new ProductResponse();

        response.setId(product.getId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setBrand(product.getBrand());
        response.setCategory(product.getCategory());
        response.setStatus(product.getStatus());

        productPriceRepository.findByProductIdAndStatus(product.getId(), "ACTIVE")
                .ifPresent(p -> {
                    response.setPrice(p.getPrice());
                    response.setCurrencyCode(p.getCurrencyCode());
                });

        productInventoryRepository.findByProductId(product.getId())
                .ifPresent(inv -> response.setAvailableQty(inv.getAvailableQty()));

        return response;
    }
}