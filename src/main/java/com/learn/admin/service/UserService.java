package com.learn.admin.service;

import com.learn.admin.dto.auth.SignUpDto;
import com.learn.admin.dto.user.*;
import com.learn.admin.model.Role;
import com.learn.admin.model.User;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface UserService {
    Page<UserView> getAllUser(int page, int limit, UserSort userSort, UserOrder userOrder);
    User createUser(SignUpDto signUpData);
    User createUser(CreateUserDto createUserData);
    User createUser(int accountId, UserDto createUserDto, Role role);
    Optional<User> getUserByEmail(String email);
    Optional<UserView> getUserById(int id);
    Optional<UserCompleteView> getCompleteUserById(int id);
    Page<UserBasicView> getUsersByRole(int roleId, int accountId, int page, int limit);
}
