package com.ecom.user.repository;

import com.ecom.user.entity.OrderItem;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface OrderItemRepository extends CrudRepository<OrderItem, String> {
	@Transactional
	@Modifying
	@Query("DELETE FROM OrderItem o WHERE o.productId = :productId")
	void deleteOrderItemsByProductId(@Param("productId") String productId);
	
	List<OrderItem> findByOrderId(String orderId);
}