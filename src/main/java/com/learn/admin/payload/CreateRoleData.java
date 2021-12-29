package com.learn.admin.payload;

import com.learn.admin.config.security.Permission;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Arrays;

@Data
@Builder
public class CreateRoleData {
    @Size(min = 3, max = 20)
    private String name;

    @NotNull
    @Size(min = 1)
    private ArrayList<Permission> permissions;

    public static CreateRoleData createAdminRole() {
        ArrayList<Permission> adminPermission = new ArrayList<>(Arrays.asList(Permission.values()));
        return CreateRoleData.builder()
                .name("Admin")
                .permissions(adminPermission)
                .build();
    }
}
