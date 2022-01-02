package com.learn.admin.services.impl;

import com.learn.admin.dto.account.CreateAccountDto;
import com.learn.admin.dto.role.RoleDto;
import com.learn.admin.exception.ValidationException;
import com.learn.admin.model.Account;
import com.learn.admin.model.Role;
import com.learn.admin.model.User;
import com.learn.admin.repository.AccountRepository;
import com.learn.admin.service.RoleService;
import com.learn.admin.services.AccountService;
import com.learn.admin.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    @Lazy
    private UserService userService;

    @Autowired
    @Lazy
    private RoleService roleService;

    @Transactional
    public Account createAccount(CreateAccountDto createAccountData) {
        Optional<Account> existingAccount = accountRepository.findByName(createAccountData.getAccountName());
        if (existingAccount.isPresent()) {
            throw new ValidationException("Account already exists with same name");
        }

        Account account = new Account();
        account.setName(createAccountData.getAccountName());
        accountRepository.save(account);

        Role adminRole = roleService.createRole(RoleDto.createAdminRole(), account.getId());
        Role memberRole = roleService.createRole(RoleDto.createMemberRole(), account.getId());
        User accountOwner = userService.createUser(account.getId(), createAccountData, adminRole);

        account.setDefaultRole(memberRole);
        account.setOwner(accountOwner);
        accountRepository.save(account);

        return account;
    }

    public Optional<Account> getAccountById(int accountId) {
        return accountRepository.findById(accountId);
    }
}
