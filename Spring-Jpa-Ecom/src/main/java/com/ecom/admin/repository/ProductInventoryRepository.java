package com.ecom.admin.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.ecom.admin.entity.ProductInventory;

public interface ProductInventoryRepository extends CrudRepository<ProductInventory, String> {
    Optional<ProductInventory> findByProductId(String productId);
}