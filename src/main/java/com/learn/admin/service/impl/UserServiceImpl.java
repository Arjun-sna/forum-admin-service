package com.learn.admin.service.impl;

import com.learn.admin.config.security.JwtUtil;
import com.learn.admin.config.security.token.Token;
import com.learn.admin.config.security.token.TokenOperation;
import com.learn.admin.dto.account.AccountView;
import com.learn.admin.dto.auth.SignUpDto;
import com.learn.admin.dto.user.*;
import com.learn.admin.exception.ValidationException;
import com.learn.admin.kafka.KafkaProducerService;
import com.learn.admin.kafka.PwResetNotification;
import com.learn.admin.model.Account;
import com.learn.admin.model.Role;
import com.learn.admin.model.User;
import com.learn.admin.repository.UserRepository;
import com.learn.admin.service.AccountService;
import com.learn.admin.service.AuthService;
import com.learn.admin.service.RoleService;
import com.learn.admin.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;
    private final JwtUtil jwtUtil;

    @Autowired
    @Lazy
    private AccountService accountService;

    @Autowired
    @Lazy
    private RoleService roleService;

    @Autowired
    @Lazy
    private KafkaProducerService<PwResetNotification> pwResetNotificationService;

    public Page<UserView> getAllUser(int accountId, int page, int limit, UserSort userSort, UserOrder userOrder) {
        Sort sortConfig = Sort.by(userOrder == UserOrder.ASC
                ? Sort.Order.asc(userSort.value()) : Sort.Order.desc(userSort.value()));
        Pageable pageRequest = PageRequest.of(page, limit, sortConfig);
        return userRepository.findAllByAccountId(accountId, pageRequest);
    }

    public UserBasicView createUser(SignUpDto signUpData) {
        // TODO: 04/01/22 move this to accountservice
        AccountView accountView = accountService.
                getAccountById(signUpData.getAccountId())
                .orElseThrow(() -> new ValidationException("Couldn't find the account"));

        return createUser(
                signUpData,
                Account.instantOf(accountView.getId()),
                Role.instantOf(accountView.getDefaultRole().getId())
        );
    }

    public UserBasicView createUser(CreateUserDto createUserData) {
        Role role = roleService.validateRoleId(createUserData.getRoleId(), authService.getLoggedInUser().getAccountId());
        return createUser(createUserData, authService.getLoggedInUser().getAccount(), role);
    }

    public UserBasicView createUser(UserDto createUserDto, Account account, Role role) {
        // TODO: 14/01/22 replace with exists
        Optional<User> existingUser = userRepository.findByEmailOrUsername(
                createUserDto.getEmail(), createUserDto.getUsername());

        if (existingUser.isPresent()) {
            throw new ValidationException("User already exists with same email/username");
        }

        User user = new User();
        user.setAccount(account);
        user.setRole(role);
        user.setUsername(createUserDto.getUsername());
        user.setFirstName(createUserDto.getFirstName());
        user.setLastName(createUserDto.getLastName());
        user.setEmail(createUserDto.getEmail());
        user.setPassword(passwordEncoder.encode(createUserDto.getPassword()));
        userRepository.save(user);

        return NewUserDto.of(user);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<UserView> getUserById(int id) {
        return userRepository.findByIdAndAccountId(id, authService.getLoggedInUser().getAccountId(), UserView.class);
    }

    public Optional<UserCompleteView> getCompleteUserById(int id) {
        return userRepository.findByIdAndAccountId(id, authService.getLoggedInUser().getAccountId(), UserCompleteView.class);
    }

    public Page<UserBasicView> getUsersByRole(int roleId, int accountId, int page, int limit) {
        return userRepository.findAllByAccountIdAndRoleId(
                accountId, roleId, PageRequest.of(page, limit), UserBasicView.class);
    }

    private User validateAndGetUser(int userId, int accountId) {
        Optional<User> existingUser = userRepository.findByIdAndAccountId(userId, accountId, User.class);

        return existingUser.orElseThrow(() -> new ValidationException("User not found"));
    }

    @Override
    public void changeRole(int userId, Account account, Role role) {
        User user = validateAndGetUser(userId, account.getId());
        user.setRole(role);
        userRepository.save(user);
    }

    @Override
    public void initiatePwReset(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ValidationException("User not found"));
        String token = jwtUtil.generateAccessToken(
                Token.of(user.getEmail(), user.getUsername(), TokenOperation.PASSWORD_RESET));
        pwResetNotificationService
                .send(PwResetNotification.of(token, user.getId(), user.getUsername(), user.getEmail()));
    }

    @Override
    public void resetPassword(int userId, int accountId, @NonNull String password) {
        User user = this.validateAndGetUser(userId, accountId);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }
}
