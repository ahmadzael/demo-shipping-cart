package com.ahmadjaelani.demo.controller;


import com.ahmadjaelani.demo.entity.Cart;
import com.ahmadjaelani.demo.model.WebResponse;
import com.ahmadjaelani.demo.services.CartServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CartController {


    @Autowired
    private CartServices orderCartService;

    @PostMapping("/api/cart")
    public WebResponse<String> addProductToCart(@RequestParam String userId,
                                              @RequestParam String productId,
                                              @RequestParam int quantity) {
        Cart cart = orderCartService.addProductToCart(userId, productId, quantity);
        return WebResponse.<String>builder().data("Successfuly Add to cart").build();
    }

    @GetMapping("api/user/{userId}/cart")
    public Cart getCartByUserId(@PathVariable String userId) {
        return orderCartService.getCartByUserId(userId);
    }


}
