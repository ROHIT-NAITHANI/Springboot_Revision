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
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderService orderService;

//    User
    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody CreateOrderDto createOrderDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(createOrderDto));

    }

    @GetMapping("/my")
    public ResponseEntity<List<OrderDto>> getMyOrders(){
          return  ResponseEntity.status(HttpStatus.OK).body(orderService.getMyOrders());
    }

    @GetMapping("/my/{id}")
    public ResponseEntity<OrderDto> getMyOrder(@PathVariable Long id){
        return  ResponseEntity.ok(orderService.getMyOrder(id));
    }


//    Admin
    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrders(){
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<OrderDto>> getOrderByUser(@PathVariable Long id){
        return ResponseEntity.ok(orderService.getOrderbyUser(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMyOrder(@PathVariable Long id){
        orderService.deleteMyOrder(id);
        return ResponseEntity.noContent().build();
    }







}
