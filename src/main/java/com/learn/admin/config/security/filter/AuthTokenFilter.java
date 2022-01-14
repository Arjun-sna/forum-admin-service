package com.learn.admin.config.security.filter;

import com.learn.admin.config.security.JwtUtil;
import com.learn.admin.config.security.SecurityConfig;
import com.learn.admin.config.security.token.Token;
import com.learn.admin.config.security.token.TokenOperation;
import com.learn.admin.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Stream;

@Slf4j
public class AuthTokenFilter extends JwtFilter {
    public AuthTokenFilter(JwtUtil jwtUtil, UserRepository userRepository) {
        super(jwtUtil, userRepository);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {;
        String currentUrl = request.getRequestURI();
        return Stream
                .concat(Arrays.stream(SecurityConfig.PROTECTED_URLS), Arrays.stream(SecurityConfig.PUBLIC_URLS))
                .anyMatch(currentUrl::equalsIgnoreCase);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final Token token = validateAndGetToken(request);
        if (token != null && token.getOperation() == TokenOperation.AUTH) {
            setSecurityContext(request, token);
        }

        filterChain.doFilter(request, response);
    }
}
