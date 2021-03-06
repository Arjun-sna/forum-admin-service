package com.learn.admin.config.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learn.admin.config.security.token.Token;
import com.learn.admin.config.security.token.TokenOperation;
import com.learn.admin.exception.InternalServerException;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtUtil {
    private static final String CUSTOM_CLAIM_KEY = "payload";
    private final JwtConfig jwtConfig;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String generateAccessToken(Token token) {
        try {
            String payloadClaim = objectMapper.writeValueAsString(token);

            return Jwts.builder()
                    .setSubject(token.getEmail())
                    .claim(CUSTOM_CLAIM_KEY, payloadClaim)
                    .setIssuer(jwtConfig.getIssuer())
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + getExpirationTime(token.getOperation())))
                    .signWith(SignatureAlgorithm.HS512, jwtConfig.getSecret())
                    .compact();
        } catch (JsonProcessingException e) {
            throw new InternalServerException(e.getMessage());
        }
    }

    private int getExpirationTime(TokenOperation operation) {
        switch (operation) {
            case AUTH:
                return jwtConfig.getAuthTokenExpiration();
            case PASSWORD_RESET:
                return jwtConfig.getPwResetTokenExpiration();
            case ACCOUNT_ACTIVATION:
                return jwtConfig.getAccountActivationTokenExpiration();
            default:
                return -1;
        }
    }

    public Token getTokenClaim(String token) {
        String claim = (String) Jwts.parser()
                .setSigningKey(jwtConfig.getSecret())
                .parseClaimsJws(token)
                .getBody()
                .get(CUSTOM_CLAIM_KEY);
        try {
            return objectMapper.readValue(claim, Token.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean validate(String token) {
        try {
            Jwts.parser().setSigningKey(jwtConfig.getSecret()).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature - {}", ex.getMessage());
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token - {}", ex.getMessage());
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token - {}", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token - {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty - {}", ex.getMessage());
        }
        return false;
    }

}
