package com.learn.admin.service;

import com.learn.admin.exception.ValidationException;
import com.learn.admin.model.User;
import com.learn.admin.payload.CreateUserData;
import com.learn.admin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    public User createUser(CreateUserData createUserData) {
        User existingUser = userRepository.findByEmail(createUserData.getEmail());

        if (existingUser != null) {
            throw new ValidationException("User already exists with same email");
        }

        User user = new User();
        user.setFirstName(createUserData.getFirstName());
        user.setLastName(createUserData.getLastName());
        user.setEmail(createUserData.getEmail());
        user.setPassword(createUserData.getPassword());
        return userRepository.save(user);
    }
}
