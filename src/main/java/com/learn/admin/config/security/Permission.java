package com.learn.admin.config.security;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.stream.Stream;

public enum Permission {
    CAN_VIEW_USER("view_user"),
    CAN_CREATE_USER("create_user"),
    CAN_ARCHIVE_USER("archive_user"),
    CAN_EDIT_USER("edit_user"),
    HAS_POST_ADMIN_ACCESS("post_admin_access"),
    HAS_POST_ACCESS("post_access"),
    HAS_TOPIC_ADMIN_ACCESS("topic_admin_access"),
    HAS_TOPIC_ACCESS("topic_access"),
    CAN_VIEW_HIDDEN("view_hidden"),
    CAN_CREATE_ROLE("create_role"),
    CAN_DELETE_ROLE("delete_role"),
    CAN_EDIT_ROLE("edit_role");

    private static final Permission[] VALUES;

    static {
        VALUES = values();
    }

    private final String permission;


    Permission(String permission) {
        this.permission = permission;
    }

    @JsonCreator
    public static Permission of(String permission) {
        return Stream.of(VALUES)
                .filter(value -> value.permission.equalsIgnoreCase(permission))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No matching constant for [" + permission + "]"));
    }

    public String value() {
        return this.permission;
    }
}
