package com.ecom.user.repository;

import com.ecom.user.entity.Order;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, String> {
	
	List<Order> findByUserId(String userId);
	
	List<Order> findAll();
}