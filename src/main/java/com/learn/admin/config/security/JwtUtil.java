package com.learn.admin.config.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learn.admin.config.security.token.Token;
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

    public String generateAccessToken(Token token) throws JsonProcessingException {
        String payloadClaim = objectMapper.writeValueAsString(token);
        return Jwts.builder()
                .setSubject(token.getEmail())
                .claim(CUSTOM_CLAIM_KEY, payloadClaim)
                .setIssuer(jwtConfig.getIssuer())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getExpiration()))
                .signWith(SignatureAlgorithm.HS512, jwtConfig.getSecret())
                .compact();
    }

    public Token getTokenClaim(String token) throws JsonProcessingException {
        String claim = (String) Jwts.parser()
                .setSigningKey(jwtConfig.getSecret())
                .parseClaimsJws(token)
                .getBody()
                .get(CUSTOM_CLAIM_KEY);
        return objectMapper.readValue(claim, Token.class);
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
