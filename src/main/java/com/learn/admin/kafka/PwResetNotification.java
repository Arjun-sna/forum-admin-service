package com.learn.admin.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class PwResetNotification {
    private final String channel = "EMAIL";
    private final String type = "PW_RESET";
    private String token;
    private int userId;
    private String username;
    private String email;
}
