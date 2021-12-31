package com.learn.admin.controller;

import com.learn.admin.model.Role;
import com.learn.admin.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;


    @GetMapping("/roles")
    public List<Role> getRoles() {
        return roleService.getAllRolesInUserAccount();
    }
}
