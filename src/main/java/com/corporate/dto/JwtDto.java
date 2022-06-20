package com.corporate.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class JwtDto {
    private String accessToken;
    private String refreshToken;
}
