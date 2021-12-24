package com.learn.admin.controller;

import com.learn.admin.model.User;
import com.learn.admin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/hello")
    public String hello() {
        return "Hello world";
    }

    @GetMapping("/user")
    public List<User> getUser() {
        return userService.getAllUser();
    }
}
