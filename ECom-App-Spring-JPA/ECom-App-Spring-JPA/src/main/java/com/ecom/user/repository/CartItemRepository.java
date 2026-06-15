package com.ecom.user.repository;

import com.ecom.user.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CartItemRepository extends CrudRepository<CartItem, String> {

    List<CartItem> findByCartId(String cartId);

    CartItem findByCartIdAndProductId(String cartId, String productId);

    void deleteByCartId(String cartId);
}