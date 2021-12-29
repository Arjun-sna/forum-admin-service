package com.learn.admin.repository;

import com.learn.admin.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findByName(String name);

    Optional<Account> findById(String name);
}
