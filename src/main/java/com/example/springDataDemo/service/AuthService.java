package com.example.springDataDemo.service;

import com.example.springDataDemo.dto.*;
import com.example.springDataDemo.entities.RefreshToken;
import com.example.springDataDemo.entities.Role;
import com.example.springDataDemo.entities.User;
import com.example.springDataDemo.exception.UserNotFoundException;
import com.example.springDataDemo.repository.RefreshTokenRepository;
import com.example.springDataDemo.repository.UserRepository;
import com.example.springDataDemo.security.JwtService;
import io.jsonwebtoken.Claims;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;


@Service
@AllArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final RefreshTokenRepository refreshTokenRepository;

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
        UserDetails userDetails =
                (UserDetails) Objects.requireNonNull(authentication.getPrincipal());

        String accessToken = jwtService.generateJwtAccessToken(userDetails);
        Instant refreshExpiresAt =
                Instant.now().plus(7, ChronoUnit.DAYS);
        String refreshToken = jwtService.generateJwtRefreshToken(userDetails,refreshExpiresAt);
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        RefreshToken refreshTokenEntity = new RefreshToken();
        refreshTokenEntity.setTokenHash(jwtService.hashToken(refreshToken));
        refreshTokenEntity.setUser(user);


        refreshTokenEntity.setExpiresAt(
                refreshExpiresAt
        );
        refreshTokenEntity.setRevoked(false);
        refreshTokenRepository.save(refreshTokenEntity);
        return new LoginResponseDto(accessToken,refreshToken);

    }


    @Transactional
    public AccessTokenResponseDto refreshAccessToken(RefreshTokenDto refreshTokenDto) {
        String refreshToken = refreshTokenDto.getRefreshToken();

        Claims claims = jwtService.parseToken(refreshToken);

        String tokenType = claims.get("tokenType", String.class);

        if (!"REFRESH".equals(tokenType)) {
            throw new RuntimeException("Invalid refresh token");
        }


        RefreshToken tokenEntity =
                refreshTokenRepository.findByTokenHash(jwtService.hashToken(refreshToken))
                        .orElseThrow(() ->
                                new RuntimeException("Refresh token not found"));



        if (tokenEntity.getExpiresAt().isBefore(Instant.now())) {
            throw new RuntimeException("Refresh token expired");
        }

        if(tokenEntity.isRevoked()){
            throw new RuntimeException("Refresh token has been revoked");
        }

        Instant expiresAt = tokenEntity.getExpiresAt();


        User user = tokenEntity.getUser();

        refreshTokenRepository.delete(tokenEntity);

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());

        String newAccessToken =
                jwtService.generateJwtAccessToken(userDetails);

        String newRefreshToken =
                jwtService.generateJwtRefreshToken(userDetails,expiresAt);

        RefreshToken newTokenEntity = new RefreshToken();

        newTokenEntity.setTokenHash(jwtService.hashToken(newRefreshToken));
        newTokenEntity.setUser(user);
        newTokenEntity.setExpiresAt(
                tokenEntity.getExpiresAt()
        );
        newTokenEntity.setRevoked(false);

        refreshTokenRepository.save(newTokenEntity);
        return new AccessTokenResponseDto(newAccessToken , newRefreshToken);

    }
}
