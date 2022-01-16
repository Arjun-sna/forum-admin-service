package com.learn.admin.config.security;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
// TODO: 16/01/22 use configuration properties annotation
public class JwtConfig {
    @Value("${security.jwt.prefix}")
    private String prefix;

    @Value("${security.jwt.auth_token_expiration}")
    private int authTokenExpiration;

    @Value("${security.jwt.pw_reset_token_expiration}")
    private int pwResetTokenExpiration;

    @Value("${security.jwt.account_activation_token_expiration}")
    private int accountActivationTokenExpiration;

    @Value("${security.jwt.secret}")
    private String secret;

    @Value("${security.jwt.issuer}")
    private String issuer;
}
