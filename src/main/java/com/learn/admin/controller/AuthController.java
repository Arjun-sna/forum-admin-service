package com.learn.admin.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.learn.admin.config.security.JwtUtil;
import com.learn.admin.config.security.token.Token;
import com.learn.admin.config.security.token.TokenOperation;
import com.learn.admin.dto.auth.JwtDto;
import com.learn.admin.dto.auth.SignInDto;
import com.learn.admin.dto.auth.SignUpDto;
import com.learn.admin.dto.user.UserBasicView;
import com.learn.admin.model.AuthUser;
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
    public JwtDto signIn(@Valid @RequestBody SignInDto signInDto) throws JsonProcessingException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signInDto.getEmail(),
                        signInDto.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        AuthUser authUser = (AuthUser) authentication.getPrincipal();
        String token = jwtUtil.generateAccessToken(
                Token.of(authUser.getEmail(), authUser.getUsername(), TokenOperation.AUTH));

        return JwtDto.of(token);
    }

    @PostMapping("sign_up")
    public UserBasicView signUp(@Valid @RequestBody SignUpDto signUpDto) {
        return userService.createUser(signUpDto);
    }

    @PostMapping("reset-password")
    public void resetPassword() {

    }
}
