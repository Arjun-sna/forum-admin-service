package com.learn.admin.controller;

import com.learn.admin.dto.role.RoleDto;
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
    public List<Role> getRoles() {
        return roleService.getAllRolesInAccount(authService.getLoggedInUserAccountId());
    }

    @PostMapping
    public Role addRole(@Valid @RequestBody RoleDto createRoleDto) {
        int accountId = authService.getLoggedInUserAccountId();
        return roleService.createRole(createRoleDto, accountId);
    }

    @PatchMapping("/{roleId}")
    public Role updateRole(@PathVariable Integer roleId, @Valid @RequestBody RoleDto updateRoleDto) {
        int accountId = authService.getLoggedInUserAccountId();
        return roleService.updateRole(roleId, accountId, updateRoleDto);
    }

    @GetMapping("/{roleId}")
    public Role getRole(@PathVariable Integer roleId) {
        int accountId = authService.getLoggedInUserAccountId();
        return roleService.getRole(roleId, accountId)
                .orElseThrow(() -> new ValidationException("Role not found"));
    }
}
