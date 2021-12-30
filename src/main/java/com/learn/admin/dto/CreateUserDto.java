package com.learn.admin.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Positive;

@EqualsAndHashCode(callSuper = true)
@Data
public class CreateUserDto extends UserDto {
    @Positive(message = "Invalid role id")
    private int roleId;
}
