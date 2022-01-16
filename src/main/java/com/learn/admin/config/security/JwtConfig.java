package com.learn.admin.config.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
@ConfigurationProperties(prefix = "security.jwt")
public class JwtConfig {
    private String prefix;
    private int authTokenExpiration;
    private int pwResetTokenExpiration;
    private int accountActivationTokenExpiration;
    private String secret;
    private String issuer;
}
