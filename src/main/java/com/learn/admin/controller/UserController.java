package com.learn.admin.controller;

import com.learn.admin.exception.ValidationException;
import com.learn.admin.payload.*;
import com.learn.admin.config.security.JwtUtil;
import com.learn.admin.model.AuthUser;
import com.learn.admin.model.User;
import com.learn.admin.service.AuthService;
import com.learn.admin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @GetMapping("/users")
    public Page<User> getUser(
            @RequestParam(defaultValue = "0",required = false) Integer page,
            @RequestParam(defaultValue = "10",required = false) Integer limit,
            @RequestParam(defaultValue = "firstName", required = false) UserSort sort,
            @RequestParam(defaultValue = "asc", required = false) UserOrder order
    ) {
        return userService.getAllUser(page, limit, sort, order);
    }

    @GetMapping("/users/{id}")
    public User getUserById(
            @PathVariable Integer id
    ) {
        return userService.getUserById(id)
                .orElseThrow(() -> new ValidationException("User not found"));
    }

    @PostMapping("/users")
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
