package com.learn.admin.service.impl;

import com.learn.admin.exception.AuthContextException;
import com.learn.admin.model.Account;
import com.learn.admin.model.AuthUser;
import com.learn.admin.repository.UserRepository;
import com.learn.admin.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;

    public AuthUser getLoggedInUser() {
        return getAuthenticationPrincipal();
    }

    private AuthUser getAuthenticationPrincipal() {
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            return (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
        throw new AuthContextException("Invalid auth context");
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .map(AuthUser::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
