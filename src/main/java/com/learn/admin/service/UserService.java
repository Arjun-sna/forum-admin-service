package com.learn.admin.service;

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
        User user = User.builder()
                .firstName(createUserData.getFirstName())
                .lastName(createUserData.getLastName())
                .email(createUserData.getEmail())
                .password(createUserData.getPassword())
                .build();
        return userRepository.save(user);
    }
}
