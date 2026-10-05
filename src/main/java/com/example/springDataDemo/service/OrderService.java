package com.example.springDataDemo.service;

import com.example.springDataDemo.dto.CreateOrderDto;
import com.example.springDataDemo.dto.OrderDto;
import com.example.springDataDemo.dto.UserDto;
import com.example.springDataDemo.entities.Order;
import com.example.springDataDemo.entities.User;
import com.example.springDataDemo.exception.UserNotFoundException;
import com.example.springDataDemo.repository.OrderRepository;
import com.example.springDataDemo.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    private OrderDto map(Order order){
        User user = order.getUser();
        return new OrderDto(order.getId(),
                order.getProductName(),
                new UserDto(user.getId(), user.getName(), user.getEmail()));
    }

    private User getLoggedInUser(){
        String email = Objects.requireNonNull(SecurityContextHolder
                        .getContext()
                        .getAuthentication())
                .getName();

        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Transactional
    public OrderDto createOrder(CreateOrderDto createOrderDto) {
        User user = getLoggedInUser();
        Order order = new Order();
        order.setUser(user);
        order.setProductName(createOrderDto.getProductName());
        Order saved = orderRepository.save(order);
        return map(saved);
    }


    public List<OrderDto> getMyOrders() {
        User user =getLoggedInUser();
        return orderRepository.findByUserId(user.getId())
                .stream()
                .map(this::map)
                .toList();
    }

    public OrderDto getMyOrder(Long id) {
        User user = getLoggedInUser();
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (!order.getUser().getId().equals(user.getId())){
            throw new RuntimeException("Access denied");
        }

        return map(order);
    }

    public List<OrderDto> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(this::map)
                .toList();

    }

    public List<OrderDto> getOrderbyUser(Long id) {
        return orderRepository.findByUserId(id)
                .stream()
                .map(this::map)
                .toList();
    }

    public void deleteMyOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
