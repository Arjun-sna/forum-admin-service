package com.learn.admin.repository;

import com.learn.admin.dto.user.UserBasicView;
import com.learn.admin.dto.user.UserView;
import com.learn.admin.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    Optional<User> findByEmailOrUsername(String email, String username);

    Optional<UserView> findByIdAndAccountId(int userId, int accountId);

    Page<UserBasicView> findAllByAccountId(int accountId, Pageable pageable);
}
