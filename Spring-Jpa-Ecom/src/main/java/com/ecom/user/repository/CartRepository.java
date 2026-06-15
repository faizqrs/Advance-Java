package com.ecom.user.repository;

import com.ecom.user.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface CartRepository extends CrudRepository<Cart, String> {

    Cart findBySessionIdAndStatus(String sessionId, String status);
}