package com.learn.admin.controller;

import com.learn.admin.config.security.Permission;
import com.learn.admin.config.security.filter.CanCreateUser;
import com.learn.admin.config.security.filter.CanEditUser;
import com.learn.admin.exception.ValidationException;
import com.learn.admin.model.Role;
import com.learn.admin.model.User;
import com.learn.admin.payload.CreateUserData;
import com.learn.admin.payload.UserOrder;
import com.learn.admin.payload.UserSort;
import com.learn.admin.service.AuthService;
import com.learn.admin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final AuthService authService;

    @GetMapping("/me")
    public Optional<User> getUserProfile() {
        return userService.getUserById(authService.getLoggedInUserId());
    }

    @GetMapping("/users")
//    @PreAuthorize("hasAuthority(T(com.learn.admin.config.security.Permission).CAN_CREATE_USER.value())")
    @CanEditUser
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
        // TODO: 28/12/21 remove hardcode value
        return userService.createUser(createUserData, 1, new Role());
    }
}
