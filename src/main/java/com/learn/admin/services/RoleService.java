package com.learn.admin.services;

import com.learn.admin.config.security.Permission;
import com.learn.admin.dto.role.RoleDto;
import com.learn.admin.model.Role;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface RoleService {
    Role createRole(RoleDto createRoleDto, int accountId);
    Role updateRole(int roleId, int accountId, RoleDto roleDto);
    Optional<Role> getRole(int roleId, int accountId);
    List<Role> getAllRolesInAccount(int accountId);
    void deleteRole(int roleId, int accountId);
}
