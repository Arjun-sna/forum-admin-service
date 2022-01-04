package com.learn.admin.config.security.token;

import com.learn.admin.model.AuthUser;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Token<T> {
    public String generateAccessToken(T payload) {
        return "";
    };

    T getPayload(String token) {
        return new ;
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
