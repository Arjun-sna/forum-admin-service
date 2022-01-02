package com.learn.admin.dto.role;

import com.learn.admin.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NewRoleDto implements RoleView {
    int id;
    String name;
    String permissions;

    public static NewRoleDto of(Role role) {
        return new NewRoleDto(role.getId(), role.getName(), role.getPermissions());
    }
}
