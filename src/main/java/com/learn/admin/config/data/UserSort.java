package com.learn.admin.config.data;


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

    public String value() {
        return this.fieldName;
    }

    public static UserSort of(String fieldName) {
        for (UserSort value : VALUES) {
            if (value.fieldName.equalsIgnoreCase(fieldName)) {
                return value;
            }
        }
        throw new IllegalArgumentException("No matching constant for [" + fieldName + "]");
    }
}
