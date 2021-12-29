package com.learn.admin.service;

import com.learn.admin.exception.ValidationException;
import com.learn.admin.model.Role;
import com.learn.admin.payload.CreateRoleData;
import com.learn.admin.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role createRole(CreateRoleData createRoleData, int accountId) {
        Optional<Role> existingRole = roleRepository.getRoleByNameAndAccountId(createRoleData.getName(), accountId);

        if(existingRole.isPresent()) {
            throw new ValidationException("Role already exists with same name");
        }

        Role role = new Role();
        role.setName(createRoleData.getName());
        role.setAccountId(accountId);
        role.setCreateUser(createRoleData.isCreateUser());
        role.setEditUser(createRoleData.isEditUser());
        role.setArchiveUser(createRoleData.isArchiveUser());
        role.setCreatePost(createRoleData.isCreatePost());
        role.setEditPost(createRoleData.isEditPost());
        role.setDeletePost(createRoleData.isDeletePost());
        role.setHidePost(createRoleData.isHidePost());
        role.setCreateTopic(createRoleData.isCreateTopic());
        role.setEditTopic(createRoleData.isEditTopic());
        role.setDeleteTopic(createRoleData.isDeleteTopic());
        role.setHideTopic(createRoleData.isHideTopic());
        role.setViewHidden(createRoleData.isViewHidden());
        role.setCreateRole(createRoleData.isCreateRole());
        role.setEditRole(createRoleData.isEditRole());
        role.setDeleteRole(createRoleData.isDeleteRole());

        roleRepository.save(role);

        return role;
    }
}
