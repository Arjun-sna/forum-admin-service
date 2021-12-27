package com.learn.admin.controller;

import com.learn.admin.config.security.JwtUtil;
import com.learn.admin.model.AuthUser;
import com.learn.admin.model.User;
import com.learn.admin.payload.CreateUserData;
import com.learn.admin.payload.JwtResponse;
import com.learn.admin.payload.SignInData;
import com.learn.admin.service.AuthService;
import com.learn.admin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @GetMapping("/hello")
    public String hello() {
        return "Hello world";
    }

    @GetMapping("/me")
    public Optional<User> getUserProfile() {
        return userService.getUserById(authService.getLoggedInUserId());
    }

    @GetMapping("/user")
    public List<User> getUser() {
        return userService.getAllUser();
    }

    @PostMapping("/user")
    public User createUser(@Valid @RequestBody CreateUserData createUserData) {
        return userService.createUser(createUserData);
    }

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
