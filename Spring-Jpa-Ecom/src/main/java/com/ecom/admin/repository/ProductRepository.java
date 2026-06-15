package com.ecom.admin.repository;

import com.ecom.admin.entity.Product;

import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, String> {
}
