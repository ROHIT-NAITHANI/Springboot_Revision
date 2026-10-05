package com.example.springDataDemo.service;

import com.example.springDataDemo.dto.CreateUserDto;
import com.example.springDataDemo.dto.UserDto;
import com.example.springDataDemo.entities.User;
import com.example.springDataDemo.exception.UserNotFoundException;
import com.example.springDataDemo.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private UserDto map(User user){
        return new UserDto(user.getId(),user.getName(),user.getEmail());
    }

    private User getLoggedInUser(){
        String email = Objects.requireNonNull(SecurityContextHolder
                        .getContext()
                        .getAuthentication())
                .getName();

        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found"));
    }


    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with id" + id));

        return map(user);
    }

    public void deleteUserById(Long id) {
            userRepository.deleteById(id);
    }


    public List<UserDto> getAllUsersPaginated(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page,pageSize);
        return userRepository.findAll(pageable)
                .map(this::map)
                .getContent();

    }

    public UserDto getCurrentUser() {
        User user = getLoggedInUser();
        return map(user);
    }

    @Transactional
    public UserDto updateCurrentUser(CreateUserDto createUserDto) {
        User user = getLoggedInUser();
        user.setEmail(createUserDto.getEmail());
        user.setName(createUserDto.getName());
        return map(user);
    }
}
