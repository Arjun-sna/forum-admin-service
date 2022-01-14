package com.learn.admin.dto.auth;

import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class PwResetDto {
    @Email
    private String email;
}
