package com.example.springDataDemo.service;

import com.example.springDataDemo.dto.CreateUserDto;
import com.example.springDataDemo.dto.UserDto;
import com.example.springDataDemo.entities.User;
import com.example.springDataDemo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserDto saveUser(CreateUserDto createUserDto){
        User user = new User();
        user.setEmail(createUserDto.getEmail());
        user.setName(createUserDto.getName());
        User savedUser =userRepository.save(user);
        return new UserDto(savedUser.getId(),savedUser.getName(),savedUser.getEmail());
    }
}
