package com.learn.admin.model;

import com.learn.admin.config.security.Authority;
import com.learn.admin.config.security.Permission;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class AuthUser implements UserDetails {
    private final User user;

    public int getId() {
        return this.user.getId();
    }

    public int getAccountId() {
        return this.user.getAccountId();
    }

    public Account getAccount() {
        return this.user.getAccount();
    }

    public String getEmail() {
        return this.user.getEmail();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String userPermissions = this.user.getRole().getPermissions();
        return Pattern.compile(",")
                .splitAsStream(userPermissions)
                .map(Permission::of)
                .map(Authority::of)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
