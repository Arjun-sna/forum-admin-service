package com.learn.admin.service;

import com.learn.admin.payload.UserSort;
import com.learn.admin.exception.ValidationException;
import com.learn.admin.model.User;
import com.learn.admin.payload.CreateUserData;
import com.learn.admin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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

    public Page<User> getAllUser(int page, int limit, UserSort userSort) {
        Pageable pageRequest = PageRequest.of(page, limit, Sort.by(userSort.value()));
        return userRepository.findAll(pageRequest);
    }

    public User createUser(CreateUserData createUserData) {
        Optional<User> existingUser = userRepository.findByEmail(createUserData.getEmail());

        if (existingUser.isPresent()) {
            throw new ValidationException("User already exists with same email");
        }

        User user = new User();
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
