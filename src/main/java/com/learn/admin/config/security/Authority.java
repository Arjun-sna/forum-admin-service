package com.learn.admin.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@RequiredArgsConstructor(staticName = "of")
public class Authority implements GrantedAuthority {
    private final Permission permission;

    @Override
    public String getAuthority() {
        return permission.value();
    }
}
