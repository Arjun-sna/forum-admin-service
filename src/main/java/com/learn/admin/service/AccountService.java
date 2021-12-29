package com.learn.admin.service;

import com.learn.admin.exception.ValidationException;
import com.learn.admin.model.Account;
import com.learn.admin.model.Role;
import com.learn.admin.model.User;
import com.learn.admin.payload.CreateAccountData;
import com.learn.admin.payload.CreateRoleData;
import com.learn.admin.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    @Lazy
    private UserService userService;

    @Autowired
    @Lazy
    private RoleService roleService;

    @Transactional
    public Account createAccount(CreateAccountData createAccountData) {
        Optional<Account> existingAccount = accountRepository.findByName(createAccountData.getAccountName());
        if (existingAccount.isPresent()) {
            throw new ValidationException("Account already exists with same name");
        }

        Account account = new Account();
        account.setName(createAccountData.getAccountName());
        accountRepository.save(account);

        CreateRoleData adminRoleData = CreateRoleData.createAdminRole();
        Role role = roleService.createRole(adminRoleData, account.getId());

        User accountOwner = userService.createUser(createAccountData, account.getId(), role);
        account.setOwner(accountOwner);
        accountRepository.save(account);

        return account;
    }

    public Optional<Account> getAccountById(int accountId) {
        return accountRepository.findById(accountId);
    }
}
