package com.learn.admin.controller;

import com.learn.admin.config.security.JwtUtil;
import com.learn.admin.dto.auth.SignUpDto;
import com.learn.admin.model.AuthUser;
import com.learn.admin.dto.auth.JwtDto;
import com.learn.admin.dto.auth.SignInDto;
import com.learn.admin.model.User;
import com.learn.admin.service.UserService;
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
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/sign_in")
    public JwtDto signIn(@Valid @RequestBody SignInDto signInDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signInDto.getEmail(),
                        signInDto.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtil.generateAccessToken((AuthUser) authentication.getPrincipal());

        return JwtDto.of(token);
    }

    @PostMapping("sign_up")
    public User signUp(@Valid @RequestBody SignUpDto signUpDto) {
        return userService.createUser(signUpDto);
    }
}
