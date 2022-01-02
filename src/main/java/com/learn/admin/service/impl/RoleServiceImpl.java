package com.learn.admin.service.impl;

import com.learn.admin.config.security.Permission;
import com.learn.admin.dto.role.RoleDto;
import com.learn.admin.dto.user.UserBasicView;
import com.learn.admin.exception.ValidationException;
import com.learn.admin.model.Role;
import com.learn.admin.repository.RoleRepository;
import com.learn.admin.service.RoleService;
import com.learn.admin.service.UserService;
import com.learn.admin.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final UserService userService;

    public Role createRole(RoleDto createRoleDto, int accountId) {
        Optional<Role> existingRole = roleRepository.getRoleByNameAndAccountId(createRoleDto.getName(), accountId);

        if (existingRole.isPresent()) {
            throw new ValidationException("Role already exists with same name");
        }

        Role role = new Role();
        role.setName(createRoleDto.getName());
        role.setAccountId(accountId);
        role.setPermissions(constructPermissionsStringFromList(createRoleDto.getPermissions()));
        roleRepository.save(role);

        return role;
    }

    private String constructPermissionsStringFromList(ArrayList<Permission> permissions) {
        return permissions
                .stream()
                .map(Permission::value)
                .collect(Collectors.joining(","));
    }

    public Role updateRole(int roleId, int accountId, RoleDto roleDto) {
        Role existingRole = validateAndGetRoleForModification(roleId, accountId);

        existingRole.setName(roleDto.getName());
        existingRole.setPermissions(constructPermissionsStringFromList(roleDto.getPermissions()));

        roleRepository.save(existingRole);
        return existingRole;
    }

    private Role validateAndGetRoleForModification(int roleId, int accountId) {
        Role existingRole = getRole(roleId, accountId)
                .orElseThrow(() -> new ValidationException("Cannot find the role"));

        if (existingRole.getName().equalsIgnoreCase(Constants.SYSTEM_GENERATED_ROLE)) {
            throw new ValidationException("Cannot delete a system generate role");
        }
        return existingRole;
    }

    public Optional<Role> getRole(int roleId, int accountId) {
        return roleRepository.getRoleByIdAndAccountId(roleId, accountId);
    }

    public List<Role> getAllRolesInAccount(int accountId) {
        return roleRepository.findAllRoleByAccountId(accountId, Sort.by("name"));
    }

    public void deleteRole(int roleId, int accountId) {
        Role existingRole = validateAndGetRoleForModification(roleId, accountId);

        Page<UserBasicView> usersByRole = userService.getUsersByRole(roleId, accountId, 1, 1);
        if (usersByRole.hasContent()) {
            throw new ValidationException("Cannot delete a role with members assigned");
        }

        roleRepository.delete(existingRole);
    }
}
