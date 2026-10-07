package com.example.springDataDemo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AccessTokenResponseDto {
    private String accessToken;
    private String refreshToken;
}
