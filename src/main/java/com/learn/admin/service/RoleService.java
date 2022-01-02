package com.learn.admin.service;

import com.learn.admin.dto.role.RoleDto;
import com.learn.admin.dto.role.RoleView;
import com.learn.admin.model.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    RoleView createRole(RoleDto createRoleDto, int accountId);

    RoleView updateRole(int roleId, int accountId, RoleDto roleDto);

    Optional<RoleView> getRole(int roleId, int accountId);

    Role validateRoleId(int roleId, int accountId);

    List<RoleView> getAllRolesInAccount(int accountId);

    void deleteRole(int roleId, int accountId);
}
