package com.learn.admin.config.security.token;

public class AuthToken extends Token<AuthToken> {
    String email;
    TokenOperation operation;

    @Override
    public String generateAccessToken(AuthToken payload) {
        return null;
    }

    @Override
    public AuthToken getPayload(String token) {
        return null;
    }

    @Override
    public boolean validate(String token) {
        return false;
    }
}
