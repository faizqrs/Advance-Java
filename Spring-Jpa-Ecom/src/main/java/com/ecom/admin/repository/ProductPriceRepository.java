package com.ecom.admin.repository;

import com.ecom.admin.entity.ProductPrice;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ProductPriceRepository extends CrudRepository<ProductPrice, String> {
    Optional<ProductPrice> findByProductIdAndStatus(String productId, String status);
    List<ProductPrice> findByProductId(String productId);
}
