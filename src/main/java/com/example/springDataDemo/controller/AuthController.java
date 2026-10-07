package com.example.springDataDemo.controller;

import com.example.springDataDemo.dto.*;
import com.example.springDataDemo.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @PostMapping("/register")
    public ResponseEntity<RegisterUserResponseDto> signUp(@RequestBody CreateUserDto createUserDto){
       return ResponseEntity.status(HttpStatus.CREATED).body(authService.registerUser(createUserDto));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginDto loginDto){
        return ResponseEntity.status(HttpStatus.OK).body(authService.login(loginDto));
    }

    @PostMapping("/RefreshToken")
    public ResponseEntity<AccessTokenResponseDto> refresh(@RequestBody RefreshTokenDto refreshTokenDto){
        return ResponseEntity.ok(authService.refreshAccessToken(refreshTokenDto));

    }


}
