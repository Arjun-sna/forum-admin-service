package com.learn.admin.util;

import com.learn.admin.config.data.UserSort;
import org.springframework.core.convert.converter.Converter;

public class UserSortConverter implements Converter<String, UserSort> {
    @Override
    public UserSort convert(String source) {
        return UserSort.of(source);
    }
}
