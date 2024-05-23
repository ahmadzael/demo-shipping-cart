package com.ahmadjaelani.demo.controller;

import com.ahmadjaelani.demo.entity.Order;
import com.ahmadjaelani.demo.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;


    @PostMapping("/create")
    public Order createOrderFromSelectedCartItems(@RequestParam String customerId, @RequestBody List<String> cartItemIds) {
        return orderService.createOrder(customerId, cartItemIds);
    }
}
