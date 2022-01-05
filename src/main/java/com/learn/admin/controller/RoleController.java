package com.learn.admin.controller;

import com.learn.admin.config.security.filter.CanEditRole;
import com.learn.admin.dto.role.AssignMembersRequest;
import com.learn.admin.dto.role.RoleDto;
import com.learn.admin.dto.role.RoleView;
import com.learn.admin.exception.ValidationException;
import com.learn.admin.model.Role;
import com.learn.admin.service.AuthService;
import com.learn.admin.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;
    private final AuthService authService;


    @GetMapping
    public List<RoleView> getRoles() {
        return roleService.getAllRolesInAccount(authService.getLoggedInUserAccountId());
    }

    @PostMapping
    public RoleView addRole(@Valid @RequestBody RoleDto createRoleDto) {
        int accountId = authService.getLoggedInUserAccountId();
        return roleService.createRole(createRoleDto, accountId);
    }

    @PatchMapping("/{roleId}")
    public RoleView updateRole(@PathVariable Integer roleId, @Valid @RequestBody RoleDto updateRoleDto) {
        int accountId = authService.getLoggedInUserAccountId();
        return roleService.updateRole(roleId, accountId, updateRoleDto);
    }

    @GetMapping("/{roleId}")
    public RoleView getRole(@PathVariable Integer roleId) {
        int accountId = authService.getLoggedInUserAccountId();
        return roleService.getRole(roleId, accountId)
                .orElseThrow(() -> new ValidationException("Role not found"));
    }

    @PatchMapping("/{roleId}/members")
    @CanEditRole
    public void assignMembers(@PathVariable int roleId, @Valid @RequestBody AssignMembersRequest assignMembersRequest) {
        roleService.assignMembers(roleId, authService.getLoggedInUserAccount(), assignMembersRequest.getMemberIds());
    }

    @DeleteMapping("/{roleId}")
    public void deleteRole(@PathVariable Integer roleId) {
        roleService.deleteRole(roleId, authService.getLoggedInUserAccountId());
    }
}
