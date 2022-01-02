package com.learn.admin.repository;

import com.learn.admin.dto.role.RoleView;
import com.learn.admin.model.Role;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<RoleView> getRoleByNameAndAccountId(String name, int accountId);

    Optional<RoleView> getRoleByIdAndAccountId(int id, int accountId);

    List<RoleView> findAllRoleByAccountId(int accountId, Sort sort);

    Optional<Role> getRoleInternalByIdAndAccountId(int id, int accountId);
}
