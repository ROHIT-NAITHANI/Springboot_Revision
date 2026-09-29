package com.example.springDataDemo.controller;

import com.example.springDataDemo.dto.CreateOrderDto;
import com.example.springDataDemo.dto.OrderDto;
import com.example.springDataDemo.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/users/{userId}/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@PathVariable long userId , @RequestBody CreateOrderDto createOrderDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(userId , createOrderDto));

    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> getOrderByUserId(@PathVariable long userId){
          return  ResponseEntity.status(HttpStatus.OK).body(orderService.getOrderByUserId(userId));
    }






}
