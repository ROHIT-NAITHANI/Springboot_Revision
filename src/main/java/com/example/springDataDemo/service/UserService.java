package com.example.springDataDemo.service;

import com.example.springDataDemo.dto.CreateUserDto;
import com.example.springDataDemo.dto.UserDto;
import com.example.springDataDemo.entities.User;
import com.example.springDataDemo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserDto saveUser(CreateUserDto createUserDto){
        User user = new User();
        user.setEmail(createUserDto.getEmail());
        user.setName(createUserDto.getName());
        User savedUser = userRepository.save(user);
        return new UserDto(savedUser.getId(),savedUser.getName(),savedUser.getEmail());
    }

    public List<UserDto> getAllUsers(){
        List<User> list = userRepository.findAll();
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : list){
            UserDto userDto = new UserDto(user.getId() , user.getEmail(), user.getName());
            userDtos.add(userDto);
        }
        return userDtos;
    }

    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow();
        return new UserDto(user.getId(), user.getName(), user.getEmail());
    }

    public UserDto deleteUserById(Long id) {
            User user = userRepository.findById(id).orElseThrow();
            userRepository.delete(user);
            return new UserDto(user.getId(), user.getName(), user.getEmail());
    }
}
