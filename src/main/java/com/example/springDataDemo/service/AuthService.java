package com.example.springDataDemo.service;

import com.example.springDataDemo.dto.CreateUserDto;
import com.example.springDataDemo.dto.LoginDto;
import com.example.springDataDemo.dto.LoginResponseDto;
import com.example.springDataDemo.dto.RegisterUserResponseDto;
import com.example.springDataDemo.entities.Role;
import com.example.springDataDemo.entities.User;
import com.example.springDataDemo.repository.UserRepository;
import com.example.springDataDemo.security.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
@AllArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public RegisterUserResponseDto registerUser(CreateUserDto createUserDto){
        User user = new User();
        user.setEmail(createUserDto.getEmail());
        user.setName(createUserDto.getName());
        user.setPassword(passwordEncoder.encode(createUserDto.getPassword()));
        user.setRole(Role.USER);
        User save = userRepository.save(user);
        return new RegisterUserResponseDto(save.getName(), save.getId());
    }


    public LoginResponseDto login(LoginDto loginDto) {
        Authentication authentication =   authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(),loginDto.getPassword())
        );
        String jwtToken = jwtService.genrateJwtToken((UserDetails) Objects.requireNonNull(authentication.getPrincipal()));

        return new LoginResponseDto(jwtToken);



    }
}
