package com.learn.admin.dto.auth;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class ForgotPwDto {
    @Email
    @NotNull
    private String email;
}
