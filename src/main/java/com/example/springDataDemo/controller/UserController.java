package com.example.springDataDemo.controller;
import com.example.springDataDemo.dto.CreateUserDto;
import com.example.springDataDemo.dto.UserDto;
import com.example.springDataDemo.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    // USER
    @GetMapping("/me")
    public ResponseEntity<UserDto> getMe(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getCurrentUser());
    }

    @PutMapping("/me")
    public ResponseEntity<UserDto> updateUser(@RequestBody CreateUserDto createUserDto){
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateCurrentUser(createUserDto));
    }

    // ADMIN
    @GetMapping()
    public ResponseEntity<List<UserDto>> getUsersPaginated(@RequestParam int page , @RequestParam int pageSize){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsersPaginated(page,pageSize));
    }

    @GetMapping("/{id}")
    public  ResponseEntity<UserDto> getUserById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.getUserById(id));
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<Void> deleteUser(@PathVariable Long id){
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

}
