package com.learn.admin.config.security.token;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class TokenClaim {
    private String email;
    private String username;
    private TokenOperation operation;
}
