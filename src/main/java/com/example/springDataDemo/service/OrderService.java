package com.example.springDataDemo.service;

import com.example.springDataDemo.dto.CreateOrderDto;
import com.example.springDataDemo.dto.OrderDto;
import com.example.springDataDemo.entities.Order;
import com.example.springDataDemo.entities.User;
import com.example.springDataDemo.repository.OrderRepository;
import com.example.springDataDemo.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @Transactional
    public OrderDto createOrder(long userId, CreateOrderDto createOrderDto) {
        Order order = new Order();
        User user= userRepository.findById(userId).orElseThrow();
        order.setUser(user);
        order.setProductName(createOrderDto.getProductName());
        Order saved = orderRepository.save(order);
        return new OrderDto(saved.getId(), saved.getProductName(),saved.getUser());
    }
}
