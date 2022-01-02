package com.learn.admin.dto.user;

import com.learn.admin.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class NewUserDto implements UserBasicView {
    private int id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private Instant createdAt;

    public static NewUserDto of(User user) {
        return new NewUserDto(user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getEmail(),
                user.getCreatedAt());
    }
}
