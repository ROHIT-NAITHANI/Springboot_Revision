package com.example.springDataDemo.service;

import com.example.springDataDemo.dto.CreateUserDto;
import com.example.springDataDemo.dto.UserDto;
import com.example.springDataDemo.entities.User;
import com.example.springDataDemo.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @Transactional
    public UserDto updateById(CreateUserDto createUserDto , Long id) {

        User user = userRepository.findById(id).orElseThrow();
        user.setEmail(createUserDto.getEmail());
        user.setName(createUserDto.getName());
        User saveduser = userRepository.save(user);
        return new UserDto(saveduser.getId(), saveduser.getName(), saveduser.getEmail());



    }

    @Transactional
    public UserDto patchById(CreateUserDto createUserDto, Long id) {
        User user = userRepository.findById(id).orElseThrow();
        if (createUserDto.getName() != null){
            user.setName(createUserDto.getName());
        }
        if (createUserDto.getEmail() != null){
            user.setEmail(createUserDto.getEmail());
        }
        User saveduser = userRepository.save(user);
        return new UserDto(saveduser.getId(), saveduser.getName(), saveduser.getEmail());

    }

    public List<UserDto> getAllUsersPaginated(int page, int pageSize, String direction, String sortBy) {
        Sort sort;
        sort = direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page,pageSize,sort);
        Page<User> usersPage = userRepository.findAll(pageable);
        List<UserDto> userDtos = new ArrayList<>();
        usersPage.forEach(user -> userDtos.add(new UserDto(user.getId() , user.getEmail(), user.getName())));
        return userDtos;
    }
}
