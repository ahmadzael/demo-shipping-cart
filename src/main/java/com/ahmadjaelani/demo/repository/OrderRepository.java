package com.ahmadjaelani.demo.repository;

import com.ahmadjaelani.demo.entity.Cart;
import com.ahmadjaelani.demo.entity.Order;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order,String> {
    Optional<Order> findByUserId(String UserId);

}
