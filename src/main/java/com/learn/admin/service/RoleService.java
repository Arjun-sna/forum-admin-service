package com.learn.admin.service;

import com.learn.admin.config.security.Permission;
import com.learn.admin.exception.ValidationException;
import com.learn.admin.model.Role;
import com.learn.admin.dto.role.RoleDto;
import com.learn.admin.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;
    private final AuthService authService;

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
        Role existingRole = getRole(roleId, accountId)
                .orElseThrow(() -> new ValidationException("Cannot find the role"));

        existingRole.setName(roleDto.getName());
        existingRole.setPermissions(constructPermissionsStringFromList(roleDto.getPermissions()));

        roleRepository.save(existingRole);
        return existingRole;
    }

    public Optional<Role> getRole(int roleId, int accountId) {
        return roleRepository.getRoleByIdAndAccountId(roleId, accountId);
    }

    public List<Role> getAllRolesInAccount(int accountId) {
        return roleRepository.findAllRoleByAccountId(accountId, Sort.by("name"));
    }
}
