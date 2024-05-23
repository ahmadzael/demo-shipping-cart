package com.ahmadjaelani.demo.repository;

import com.ahmadjaelani.demo.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem,String> {
}
