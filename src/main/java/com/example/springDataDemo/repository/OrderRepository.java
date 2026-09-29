package com.example.springDataDemo.repository;

import com.example.springDataDemo.entities.Order;
import com.example.springDataDemo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order , Long> {

}
