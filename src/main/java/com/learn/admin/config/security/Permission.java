package com.learn.admin.config.security;

import java.util.stream.Stream;

public enum Permission {
    CAN_CREATE_USER("can_create_user"),
    CAN_EDIT_USER("can_edit_user");

    private final String permission;
    private static final Permission[] VALUES;
    static {
        VALUES = values();
    }


    Permission(String permission) {
        this.permission = permission;
    }

    public String value() {
        return this.permission;
    }

    public static Permission of(String permission) {
        return Stream.of(VALUES)
                .filter(value -> value.permission.equalsIgnoreCase(permission))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No matching constant for [" + permission + "]"));
    }
}
