package com.learn.admin.dto.auth;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class ResetPwDto {
    @NotBlank
    @Size(min = 8, max = 16)
    private String password;
}
