package com.learn.admin.controller;

import com.learn.admin.config.security.JwtUtil;
import com.learn.admin.model.AuthUser;
import com.learn.admin.payload.JwtResponse;
import com.learn.admin.payload.SignInData;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping("/sign_in")
    public JwtResponse signIn(@Valid @RequestBody SignInData signInData) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signInData.getEmail(),
                        signInData.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtil.generateAccessToken((AuthUser) authentication.getPrincipal());

        return JwtResponse.of(token);
    }
}
