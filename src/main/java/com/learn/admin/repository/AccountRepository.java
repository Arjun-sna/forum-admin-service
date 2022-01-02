package com.learn.admin.repository;

import com.learn.admin.dto.account.AccountBasicView;
import com.learn.admin.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    <T> Optional<T> findByName(String name, Class<T> type);

    <T> Optional<T> findById(String name, Class<T> type);
}
