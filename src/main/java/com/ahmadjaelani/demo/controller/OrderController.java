package com.ahmadjaelani.demo.controller;

import com.ahmadjaelani.demo.entity.Cart;
import com.ahmadjaelani.demo.entity.Order;
import com.ahmadjaelani.demo.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;


    @PostMapping("/api/user/{userId}/order")
    public Order createOrderFromSelectedCartItems(@PathVariable String userId, @RequestBody List<String> cartItemIds) {
        return orderService.createOrder(userId, cartItemIds);
    }

    @GetMapping("api/user/{userId}/order")
    public Order getOrderByUserId(@PathVariable String userId) {
        return orderService.getOrderByUserId(userId);
    }

}
