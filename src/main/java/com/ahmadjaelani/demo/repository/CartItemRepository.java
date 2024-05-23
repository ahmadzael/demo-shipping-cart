package com.ahmadjaelani.demo.repository;

import com.ahmadjaelani.demo.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem,String> {
    Optional<CartItem> findByCartIdAndProductId(String cartId, String productId);

    List<CartItem> findByCartId(String cartId);

}
