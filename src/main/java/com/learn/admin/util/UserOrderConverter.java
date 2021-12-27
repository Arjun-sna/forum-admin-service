package com.learn.admin.util;

import com.learn.admin.payload.UserOrder;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;

public class UserOrderConverter implements Converter<String, UserOrder> {
    @Override
    public UserOrder convert(@NonNull String source) {
        try {
            return UserOrder.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException ex) {
            return UserOrder.ASC;
        }
    }
}
