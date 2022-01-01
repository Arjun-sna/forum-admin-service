package com.learn.admin.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class CreateAccountDto extends UserDto {
    @Size(min = 3, max = 20)
    private String accountName;
}
