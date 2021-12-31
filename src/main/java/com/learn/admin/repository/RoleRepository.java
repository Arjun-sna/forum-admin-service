package com.learn.admin.repository;

import com.learn.admin.model.Role;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> getRoleByNameAndAccountId(String name, int accountId);

    Optional<Role> getRoleByIdAndAccountId(int id, int accountId);

    List<Role> findAllRoleByAccountId(int accountId, Sort sort);
}
