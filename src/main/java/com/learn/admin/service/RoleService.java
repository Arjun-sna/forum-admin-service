package com.learn.admin.service;

import com.learn.admin.config.security.Permission;
import com.learn.admin.exception.ValidationException;
import com.learn.admin.model.Role;
import com.learn.admin.payload.CreateRoleData;
import com.learn.admin.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role createRole(CreateRoleData createRoleData, int accountId) {
        Optional<Role> existingRole = roleRepository.getRoleByNameAndAccountId(createRoleData.getName(), accountId);

        if(existingRole.isPresent()) {
            throw new ValidationException("Role already exists with same name");
        }

        String permissions = createRoleData.getPermissions().stream()
                .map(Permission::value).collect(Collectors.joining(","));
        Role role = new Role();
        role.setName(createRoleData.getName());
        role.setAccountId(accountId);
        role.setPermissions(permissions);
        roleRepository.save(role);

        return role;
    }

    public Optional<Role> getRole(int roleId, int accountId) {
        return roleRepository.getRoleByIdAndAccountId(roleId, accountId);
    }
}
