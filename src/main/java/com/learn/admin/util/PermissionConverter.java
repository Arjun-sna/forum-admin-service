package com.learn.admin.util;

import com.learn.admin.config.security.Permission;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;

public class PermissionConverter implements Converter<String, Permission> {
    @Override
    public Permission convert(@NonNull String source) {
        // TODO: 01/01/22 fix this
//        try {
        return Permission.of(source);
//        } catch (IllegalArgumentException ex) {
//            throw new Va
//        }
    }
}
