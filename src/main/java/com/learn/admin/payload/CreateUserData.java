package com.learn.admin.payload;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@EqualsAndHashCode(callSuper = true)
@Data
public class CreateUserData extends UserData{
    @NotBlank
    private int accountId;

    @NotBlank
    private int roleId;
}
