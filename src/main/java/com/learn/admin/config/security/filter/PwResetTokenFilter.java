package com.learn.admin.config.security.filter;

import com.learn.admin.config.security.token.Token;
import com.learn.admin.config.security.token.TokenOperation;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PwResetTokenFilter extends JwtFilter {

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !request.getRequestURI().equalsIgnoreCase("/reset-password");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final Token token = validateAndGetToken(request);
        if (token != null && token.getOperation() == TokenOperation.PASSWORD_RESET) {
            setSecurityContext(request, token);
        }

        filterChain.doFilter(request, response);
    }
}
