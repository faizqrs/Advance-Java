package com.ecom.seller.repository;

import com.ecom.seller.entity.Product;

import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, String> {
}