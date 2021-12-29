package com.learn.admin.payload;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

@EqualsAndHashCode(callSuper = true)
@Data
public class CreateUserData extends UserData {
    @NotBlank
    private int accountId;

    @NotBlank
    private int roleId;
}
