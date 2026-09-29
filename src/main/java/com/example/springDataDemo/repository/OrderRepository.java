package com.example.springDataDemo.repository;

import com.example.springDataDemo.entities.Order;
import com.example.springDataDemo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order , Long> {
    List<Order> findByUserId(Long userId);

}
