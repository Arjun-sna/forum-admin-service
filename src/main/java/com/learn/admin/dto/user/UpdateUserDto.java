package com.learn.admin.dto.user;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class UpdateUserDto {
    @Size(min = 3, max = 100)
    private String firstName;

    @Size(min = 2, max = 100)
    private String lastName;
}
