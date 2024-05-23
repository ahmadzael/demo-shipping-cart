package com.ahmadjaelani.demo.services;

import com.ahmadjaelani.demo.entity.Cart;
import com.ahmadjaelani.demo.entity.CartItem;
import com.ahmadjaelani.demo.entity.Order;
import com.ahmadjaelani.demo.entity.OrderItem;
import com.ahmadjaelani.demo.repository.CartItemRepository;
import com.ahmadjaelani.demo.repository.CartRepository;
import com.ahmadjaelani.demo.repository.OrderItemRepository;
import com.ahmadjaelani.demo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    public Order createOrder(String userId,List<String> cartItemIds) {
        // Retrieve the customer's cart
        Optional<Cart> cartOpt = cartRepository.findByUserId(userId);
        if (!cartOpt.isPresent()) {
            throw new RuntimeException("Cart not found");
        }

        Cart cart = cartOpt.get();
        List<CartItem> cartItems = cartItemRepository.findAllById(cartItemIds);

        if (cartItems.isEmpty()) {
            throw new RuntimeException("No items selected from cart");
        }

        // Create a new order
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setStatus("Processing");

        BigDecimal totalAmount = BigDecimal.ZERO;

        for (CartItem cartItem : cartItems) {
            if (cartItem.getCart().getId().equals(cart.getId())) {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrder(order);
                orderItem.setProduct(cartItem.getProduct());
                orderItem.setQuantity(cartItem.getQuantity());
                orderItem.setPrice(cartItem.getProduct().getPrice());

                totalAmount = totalAmount.add(orderItem.getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity())));
                order.getOrderItems().add(orderItem);
            }
        }

        order.setTotalAmount(totalAmount);
        orderRepository.save(order);
        orderItemRepository.saveAll(order.getOrderItems());

        // Remove selected cart items from the cart
        cartItemRepository.deleteAll(cartItems);

        return order;
    }
}
