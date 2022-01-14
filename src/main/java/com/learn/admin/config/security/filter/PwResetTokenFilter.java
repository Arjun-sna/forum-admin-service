package com.learn.admin.config.security.filter;

import com.learn.admin.config.security.JwtUtil;
import com.learn.admin.config.security.token.Token;
import com.learn.admin.config.security.token.TokenOperation;
import com.learn.admin.repository.UserRepository;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@Component
public class PwResetTokenFilter extends JwtFilter {
    public PwResetTokenFilter(JwtUtil jwtUtil, UserRepository userRepository) {
        super(jwtUtil, userRepository);
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
