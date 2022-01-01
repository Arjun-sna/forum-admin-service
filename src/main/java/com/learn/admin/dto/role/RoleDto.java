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
public class RoleDto {
    @Size(min = 3, max = 20)
    private String name;

    @NotNull
    @Size(min = 1)
    private ArrayList<Permission> permissions;

    public static RoleDto createAdminRole() {
        ArrayList<Permission> adminPermissions = new ArrayList<>(Arrays.asList(Permission.values()));
        return RoleDto.builder()
                .name("Admin")
                .permissions(adminPermissions)
                .build();
    }

    public static RoleDto createMemberRole() {
        ArrayList<Permission> memberPermissions = new ArrayList<>();
        memberPermissions.add(Permission.HAS_POST_ACCESS);
        memberPermissions.add(Permission.HAS_TOPIC_ACCESS);
        memberPermissions.add(Permission.CAN_VIEW_USER);
        return RoleDto.builder()
                .name("Member")
                .permissions(memberPermissions)
                .build();
    }

}
