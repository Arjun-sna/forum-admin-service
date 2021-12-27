package com.learn.admin.payload;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor(staticName = "of")
public class JwtResponse {
    private final String jwtToken;
}
