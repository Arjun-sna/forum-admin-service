package com.learn.admin.dto.auth;

import com.learn.admin.dto.user.UserDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Positive;

@Data
@EqualsAndHashCode(callSuper = true)
public class SignUpDto extends UserDto {
    @Positive(message = "Invalid account id")
    private int accountId;
}
