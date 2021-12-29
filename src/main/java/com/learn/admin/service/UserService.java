package com.learn.admin.service;

import com.learn.admin.model.Account;
import com.learn.admin.model.Role;
import com.learn.admin.payload.UserData;
import com.learn.admin.payload.UserOrder;
import com.learn.admin.payload.UserSort;
import com.learn.admin.exception.ValidationException;
import com.learn.admin.model.User;
import com.learn.admin.payload.CreateUserData;
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

    @Autowired @Lazy
    private AccountService accountService;

    @Autowired @Lazy
    private RoleService roleService;

    public Page<User> getAllUser(int page, int limit, UserSort userSort, UserOrder userOrder) {
        Sort sortConfig = Sort.by(userOrder == UserOrder.ASC
                ? Sort.Order.asc(userSort.value()) : Sort.Order.desc(userSort.value()));
        Pageable pageRequest = PageRequest.of(page, limit, sortConfig);
        return userRepository.findAll(pageRequest);
    }

    public User createUser(CreateUserData createUserData) {
        Account account = accountService
                .getAccountById(createUserData.getAccountId())
                .orElseThrow(() -> new ValidationException("Couldn't find the account"));
        Role role = roleService.
                getRole(createUserData.getRoleId(), createUserData.getAccountId())
                .orElseThrow(() -> new ValidationException("Couldn't find the role"));

        return createUser(createUserData, account.getId(), role);
    }

    public User createUser(UserData createUserData, int accountId, Role role) {
        accountService.getAccountById(accountId);
        Optional<User> existingUser = userRepository.findByEmailOrUsername(
                createUserData.getEmail(), createUserData.getUsername());

        if (existingUser.isPresent()) {
            throw new ValidationException("User already exists with same email/username");
        }

        User user = new User();
        user.setAccountId(accountId);
        user.setRole(role);
        user.setUsername(createUserData.getUsername());
        user.setFirstName(createUserData.getFirstName());
        user.setLastName(createUserData.getLastName());
        user.setEmail(createUserData.getEmail());
        user.setPassword(passwordEncoder.encode(createUserData.getPassword()));
        return userRepository.save(user);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> getUserById(int id) {
        return userRepository.findById(id);
    }
}
