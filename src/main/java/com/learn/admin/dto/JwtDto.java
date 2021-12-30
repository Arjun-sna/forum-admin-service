package com.learn.admin.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor(staticName = "of")
public class JwtDto {
    private final String jwtToken;
}
