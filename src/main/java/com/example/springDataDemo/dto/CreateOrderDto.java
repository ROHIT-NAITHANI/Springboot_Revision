package com.example.springDataDemo.dto;

import com.example.springDataDemo.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateOrderDto {
    private String productName;
}
