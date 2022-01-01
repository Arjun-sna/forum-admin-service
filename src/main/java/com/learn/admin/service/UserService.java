package com.learn.admin.service;

import com.learn.admin.dto.auth.SignUpDto;
import com.learn.admin.dto.user.*;
import com.learn.admin.exception.ValidationException;
import com.learn.admin.model.Account;
import com.learn.admin.model.Role;
import com.learn.admin.model.User;
import com.learn.admin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;

    @Autowired
    @Lazy
    private AccountService accountService;

    @Autowired
    @Lazy
    private RoleService roleService;

    // TODO: 01/01/22 move account id to param
    public Page<UserView> getAllUser(int page, int limit, UserSort userSort, UserOrder userOrder) {
        Sort sortConfig = Sort.by(userOrder == UserOrder.ASC
                ? Sort.Order.asc(userSort.value()) : Sort.Order.desc(userSort.value()));
        Pageable pageRequest = PageRequest.of(page, limit, sortConfig);
        return userRepository.findAllByAccountId(authService.getLoggedInUserAccountId(), pageRequest);
    }

    public User createUser(SignUpDto signUpData) {
        Account account = accountService.
                getAccountById(signUpData.getAccountId())
                .orElseThrow(() -> new ValidationException("Couldn't find the account"));

        return createUser(account.getId(), signUpData, account.getDefaultRole());
    }

    public User createUser(CreateUserDto createUserData) {
        Role role = roleService.
                getRole(createUserData.getRoleId(), authService.getLoggedInUserAccountId())
                .orElseThrow(() -> new ValidationException("Couldn't find the role"));

        return createUser(authService.getLoggedInUserAccountId(), createUserData, role);
    }

    public User createUser(int accountId, UserDto createUserDto, Role role) {
        Optional<User> existingUser = userRepository.findByEmailOrUsername(
                createUserDto.getEmail(), createUserDto.getUsername());

        if (existingUser.isPresent()) {
            throw new ValidationException("User already exists with same email/username");
        }

        User user = new User();
        user.setAccountId(accountId);
        user.setRole(role);
        user.setUsername(createUserDto.getUsername());
        user.setFirstName(createUserDto.getFirstName());
        user.setLastName(createUserDto.getLastName());
        user.setEmail(createUserDto.getEmail());
        user.setPassword(passwordEncoder.encode(createUserDto.getPassword()));
        return userRepository.save(user);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<UserView> getUserById(int id) {
        return userRepository.findByIdAndAccountId(id, authService.getLoggedInUserAccountId(), UserView.class);
    }

    public Optional<UserCompleteView> getCompleteUserById(int id) {
        return userRepository.findByIdAndAccountId(id, authService.getLoggedInUserAccountId(), UserCompleteView.class);
    }

    public Page<UserBasicView> getUsersByRole(int roleId, int accountId, int page, int limit) {
        return userRepository.findAllByAccountIdAndRoleId(
                accountId, roleId, PageRequest.of(page, limit), UserBasicView.class);
    }
}
