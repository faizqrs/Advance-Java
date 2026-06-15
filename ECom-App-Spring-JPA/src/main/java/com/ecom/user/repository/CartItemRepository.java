package com.ecom.user.repository;

import com.ecom.user.entity.CartItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, String> {

    List<CartItem> findByCartId(String cartId);

    CartItem findByCartIdAndProductId(String cartId, String productId);

    void deleteByCartId(String cartId); 

    @Transactional
    @Modifying
    @Query("DELETE FROM CartItem c WHERE c.productId = :productId")
    void deleteCartItemsByProductId(@Param("productId") String productId);
}