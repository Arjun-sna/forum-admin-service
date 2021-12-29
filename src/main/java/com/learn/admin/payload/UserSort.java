package com.learn.admin.payload;


import java.util.stream.Stream;

public enum UserSort {
    FIRSTNAME("firstName"),
    LASTNAME("lastName"),
    EMAIL("email");

    private static final UserSort[] VALUES;

    static {
        VALUES = values();
    }

    private final String fieldName;

    UserSort(String fieldName) {
        this.fieldName = fieldName;
    }

    public static UserSort of(String fieldName) {
        return Stream.of(VALUES)
                .filter(value -> value.fieldName.equalsIgnoreCase(fieldName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No matching constant for [" + fieldName + "]"));
    }

    public String value() {
        return this.fieldName;
    }
}
