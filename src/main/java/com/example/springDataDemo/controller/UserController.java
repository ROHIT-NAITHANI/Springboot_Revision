package com.example.springDataDemo.controller;

import com.example.springDataDemo.dto.CreateUserDto;
import com.example.springDataDemo.dto.UserDto;
import com.example.springDataDemo.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody CreateUserDto createUserDto){

      return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(createUserDto));

    }

}
