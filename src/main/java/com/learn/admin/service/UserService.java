package com.learn.admin.service;

import com.learn.admin.exception.ValidationException;
import com.learn.admin.model.User;
import com.learn.admin.payload.CreateUserData;
import com.learn.admin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> getAllUser() {
        return userRepository.findAll();
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
}
