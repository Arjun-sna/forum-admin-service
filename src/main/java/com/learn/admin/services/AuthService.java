package com.learn.admin.services;

import com.learn.admin.model.AuthUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthService extends UserDetailsService {
    AuthUser getLoggedInUser();
    Integer getLoggedInUserId();
    Integer getLoggedInUserAccountId();
    String getLoggedInUserEmail();
}
