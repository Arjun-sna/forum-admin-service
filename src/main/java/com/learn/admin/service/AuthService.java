package com.learn.admin.service;

import com.learn.admin.exception.AuthContextException;
import com.learn.admin.model.AuthUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    public AuthUser getLoggedInUser() {
        return getAuthenticationPrincipal();
    }

    private AuthUser getAuthenticationPrincipal() {
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            return (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
        throw new AuthContextException("Invalid auth context");
    }

    public Integer getLoggedInUserId() {
        return getAuthenticationPrincipal().getId();
    }

    public String getLoggedInUserEmail() {
        return getAuthenticationPrincipal().getEmail();
    }
}
