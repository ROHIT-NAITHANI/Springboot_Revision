package com.example.springDataDemo.controller;
import com.example.springDataDemo.dto.CreateUserDto;
import com.example.springDataDemo.dto.UserDto;
import com.example.springDataDemo.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class UserController {
    private final UserService userService;
    @PostMapping("/users")
    public ResponseEntity<UserDto> createUser(@RequestBody CreateUserDto createUserDto){

      return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(createUserDto));

    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getUsers(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsers());
    }

    @GetMapping("/page")
    public ResponseEntity<List<UserDto>> getUsersPaginated(@RequestParam int page , @RequestParam int pageSize, @RequestParam(defaultValue = "asc") String direction,
                                                           @RequestParam(defaultValue = "name")String sortBy){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsersPaginated(page,pageSize,direction,sortBy));
    }

    @GetMapping("/users/{id}")
    public  ResponseEntity<UserDto> getUserById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.getUserById(id));
    }

    @DeleteMapping("/users/delete/{id}")
    public  ResponseEntity<UserDto> deleteUser(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(userService.deleteUserById(id));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id ,@RequestBody CreateUserDto createUserDto){
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateById(createUserDto , id));
    }

    @PatchMapping("/users/{id}")
    public ResponseEntity<UserDto> patchUser(@PathVariable Long id ,@RequestBody CreateUserDto createUserDto){
        return ResponseEntity.status(HttpStatus.OK).body(userService.patchById(createUserDto , id));
    }


}
