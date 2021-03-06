package com.learn.admin.util;

import com.learn.admin.dto.user.UserSort;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;

public class UserSortConverter implements Converter<String, UserSort> {
    @Override
    public UserSort convert(@NonNull String source) {
        try {
            return UserSort.of(source);
        } catch (IllegalArgumentException ex) {
            return UserSort.FIRSTNAME;
        }
    }
}
