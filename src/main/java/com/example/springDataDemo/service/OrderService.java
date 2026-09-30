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
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @Transactional
    public OrderDto createOrder(long userId, CreateOrderDto createOrderDto) {

        User user = userRepository.findById(userId).
                orElseThrow(() -> new UserNotFoundException("User not found with id " + userId));

        Order order = new Order();
        order.setUser(user);
        order.setProductName(createOrderDto.getProductName());
        Order saved = orderRepository.save(order);
        return new OrderDto(saved.getId(), saved.getProductName(), new UserDto(saved.getUser().getId(), saved.getUser().getName(), saved.getUser().getEmail()));
    }

    public List<OrderDto> getOrderByUserId(long userId) {
        List<Order> byUserId = orderRepository.findByUserId(userId);
        List<OrderDto> orderDtos = new ArrayList<>();
        for (Order order : byUserId){
            OrderDto orderDto = new OrderDto(order.getId(), order.getProductName(),new UserDto(order.getUser().getId(), order.getUser().getName(),order.getUser().getEmail()));
            orderDtos.add(orderDto);
        }
        return orderDtos;
    }
}
