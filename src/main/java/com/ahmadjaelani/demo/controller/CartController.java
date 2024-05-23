package com.ahmadjaelani.demo.controller;


import com.ahmadjaelani.demo.entity.Cart;
import com.ahmadjaelani.demo.model.WebResponse;
import com.ahmadjaelani.demo.services.CartServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart")
public class CartController {


    @Autowired
    private CartServices orderCartService;

    @PostMapping("/add")
    public WebResponse<Cart> addProductToCart(@RequestParam String userId,
                                              @RequestParam String productId,
                                              @RequestParam int quantity) {
        Cart cart = orderCartService.addProductToCart(userId, productId, quantity);
        return WebResponse.<Cart>builder().data(cart).build();

    }
}
