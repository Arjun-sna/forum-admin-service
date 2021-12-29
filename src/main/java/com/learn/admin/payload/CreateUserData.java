package com.learn.admin.payload;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Positive;

@EqualsAndHashCode(callSuper = true)
@Data
public class CreateUserData extends UserData {
    @Positive(message = "Invalid role id")
    private int roleId;
}
