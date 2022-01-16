package com.learn.admin.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class AccountActivationNotification {
    private String token;
    private int userId;
    private String username;
    private String email;
}
