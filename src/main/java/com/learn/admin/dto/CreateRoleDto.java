package com.learn.admin.dto;

import com.learn.admin.config.security.Permission;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Arrays;

@Data
@Builder
public class CreateRoleDto {
    @Size(min = 3, max = 20)
    private String name;

    @NotNull
    @Size(min = 1)
    private ArrayList<Permission> permissions;

    public static CreateRoleDto createAdminRole() {
        ArrayList<Permission> adminPermission = new ArrayList<>(Arrays.asList(Permission.values()));
        return CreateRoleDto.builder()
                .name("Admin")
                .permissions(adminPermission)
                .build();
    }
}
