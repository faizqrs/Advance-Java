package com.ecom.seller.repository;

import com.ecom.seller.entity.ProductInventory;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProductInventoryRepository extends CrudRepository<ProductInventory, String> {
    Optional<ProductInventory> findByProductId(String productId);
}