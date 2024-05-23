package com.ahmadjaelani.demo.services;


import com.ahmadjaelani.demo.entity.Cart;
import com.ahmadjaelani.demo.entity.CartItem;
import com.ahmadjaelani.demo.entity.Product;
import com.ahmadjaelani.demo.entity.User;
import com.ahmadjaelani.demo.repository.CartItemRepository;
import com.ahmadjaelani.demo.repository.CartRepository;
import com.ahmadjaelani.demo.repository.ProductRepository;
import com.ahmadjaelani.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
public class CartServices {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Cart addProductToCart(String userId, String productId, int quantity) {
        Optional<Product> productOptional = productRepository.findById(productId);
        Optional<User> userOptional = userRepository.findById(userId);
        if (!productOptional.isPresent()) {
            throw new RuntimeException("Product not found");
        }
        if (!userOptional.isPresent()) {
            throw new RuntimeException("User not found");
        }

        Product product = productOptional.get();
        User user = userOptional.get();
        BigDecimal total = product.getPrice().multiply(BigDecimal.valueOf(quantity));

        String newCartID = (UUID.randomUUID().toString());

        Cart cart = cartRepository.findByUserId(userId).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setId(newCartID);
            newCart.setUser(user);
            return cartRepository.save(newCart);
        });

        CartItem cartItem = cartItemRepository.findByCartIdAndProductId(cart.getId(), productId).orElseGet(() -> {
            CartItem newCartItem = new CartItem();
            newCartItem.setCart(cart);
            newCartItem.setProduct(product);
            newCartItem.setQuantity(quantity);
            return newCartItem;
        });

        //cartItem.setQuantity(cartItem.getQuantity() + quantity);
        cartItemRepository.save(cartItem);

        return cartRepository.save(cart);
    }


//    @Transactional
//    public Cart updateCartQuantity(String cartId, int quantity) {
//        Optional<Cart> cartOptional = cartRepository.findById(cartId);
//        if (!cartOptional.isPresent()) {
//            throw new RuntimeException("Cart not found");
//        }
//
//        Cart cart = cartOptional.get();
//        BigDecimal total = cart.getProduct().getPrice().multiply(BigDecimal.valueOf(quantity));
//        cart.setQuantity(quantity);
//        cart.setTotal(total);
//
//        return cartRepository.save(cart);
//    }
//
//    @Transactional
//    public Cart increaseCartQuantity(String cartId, int quantity) {
//        Optional<Cart> cartOptional = cartRepository.findById(cartId);
//        if (!cartOptional.isPresent()) {
//            throw new RuntimeException("Cart not found");
//        }
//
//        Cart cart = cartOptional.get();
//        int newQuantity = cart.getQuantity() + quantity;
//        BigDecimal total = cart.getProduct().getPrice().multiply(BigDecimal.valueOf(newQuantity));
//        cart.setQuantity(newQuantity);
//        cart.setTotal(total);
//
//        return cartRepository.save(cart);
//    }
//
//    @Transactional
//    public Cart decreaseCartQuantity(String cartId, int quantity) {
//        Optional<Cart> cartOptional = cartRepository.findById(cartId);
//        if (!cartOptional.isPresent()) {
//            throw new RuntimeException("Cart not found");
//        }
//
//        Cart cart = cartOptional.get();
//        int newQuantity = cart.getQuantity() - quantity;
//        if (newQuantity < 0) {
//            throw new RuntimeException("Quantity cannot be less than zero");
//        }
//        BigDecimal total = cart.getProduct().getPrice().multiply(BigDecimal.valueOf(newQuantity));
//        cart.setQuantity(newQuantity);
//        cart.setTotal(total);
//
//        return cartRepository.save(cart);
//    }

    @Transactional
    public void deleteCart(String cartId) {
        if (!cartRepository.existsById(cartId)) {
            throw new RuntimeException("Cart not found");
        }
        cartRepository.deleteById(cartId);
    }
}
