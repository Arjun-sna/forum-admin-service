package com.learn.admin.controller;

import com.learn.admin.config.security.authorization.CanCreateUser;
import com.learn.admin.config.security.authorization.CanViewUser;
import com.learn.admin.dto.user.*;
import com.learn.admin.exception.ValidationException;
import com.learn.admin.service.AuthService;
import com.learn.admin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final AuthService authService;

    @GetMapping("/me")
    public Optional<UserCompleteView> getUserProfile() {
        return userService.getCompleteUserById(authService.getLoggedInUser().getId());
    }

    @PatchMapping("/me")
    public void updateUser(@Valid @RequestBody UpdateUserDto updateUserData) {
        userService.updateUser(
                authService.getLoggedInUser().getId(), authService.getLoggedInUser().getAccountId(), updateUserData);
    }

    @GetMapping("/users")
    @CanViewUser
    public Page<UserView> getUser(
            @RequestParam(defaultValue = "0", required = false) Integer page,
            @RequestParam(defaultValue = "10", required = false) Integer limit,
            @RequestParam(defaultValue = "firstName", required = false) UserSort sort,
            @RequestParam(defaultValue = "asc", required = false) UserOrder order
    ) {
        return userService.getAllUser(authService.getLoggedInUser().getAccountId(), page, limit, sort, order);
    }

    @GetMapping("/users/{id}")
    @CanViewUser
    public UserView getUserById(
            @PathVariable Integer id
    ) {
        return userService.getUserById(id)
                .orElseThrow(() -> new ValidationException("User not found"));
    }

    @PostMapping("/users")
    @CanCreateUser
    public UserBasicView createUser(@Valid @RequestBody CreateUserDto createUserData) {
        return userService.createUser(createUserData);
    }
}
