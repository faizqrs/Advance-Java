package com.zepto.product.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.zepto.product.entity.ProductEntity;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {

    // METHOD 1 — uploadProduct()
    // Handled by JpaRepository.save(entity) — call directly from service

    // METHOD 2 — getProductAndCheckStatus()
    // Handled by JpaRepository.findById(id) — call directly from service

    // METHOD 3 — getAllProducts()
    // Handled by JpaRepository.findAll() — call directly from service

    // METHOD 4 — updateProductStatus()
    @Modifying
    @Query("UPDATE ProductEntity p SET p.status = :status, p.approvalNote = :note WHERE p.id = :id")
    void updateProductStatus(@Param("id") int id,
                             @Param("status") String status,
                             @Param("note") String note);
}