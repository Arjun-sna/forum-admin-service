package com.learn.admin.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Email;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Data
@EqualsAndHashCode(callSuper = true)
public class SignUpDto extends UserDto {
    @Positive(message = "Invalid account id")
    private int accountId;
}
