package com.learn.admin.dto.auth;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
public class SignInDto {
    @Email
    private String email;

    @Size(min = 8, max = 16)
    private String password;
}
