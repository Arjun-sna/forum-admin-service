package com.learn.admin.dto.user;

import java.time.Instant;

public interface UserBasicView {
    int getId();

    String getFirstName();

    String getLastName();

    String getUsername();

    String getEmail();

    Instant getCreatedAt();
}
