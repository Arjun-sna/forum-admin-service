package com.learn.admin.service.impl;

import com.learn.admin.dto.account.AccountBasicView;
import com.learn.admin.dto.account.CreateAccountDto;
import com.learn.admin.dto.account.NewAccountDto;
import com.learn.admin.dto.role.RoleDto;
import com.learn.admin.dto.role.RoleView;
import com.learn.admin.dto.user.UserBasicView;
import com.learn.admin.exception.ValidationException;
import com.learn.admin.model.Account;
import com.learn.admin.model.Role;
import com.learn.admin.model.User;
import com.learn.admin.repository.AccountRepository;
import com.learn.admin.service.AccountService;
import com.learn.admin.service.RoleService;
import com.learn.admin.service.UserService;
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
    public AccountBasicView createAccount(CreateAccountDto createAccountData) {
        Optional<AccountBasicView> existingAccount = accountRepository.findByName(
                createAccountData.getAccountName(), AccountBasicView.class);
        if (existingAccount.isPresent()) {
            throw new ValidationException("Account already exists with same name");
        }

        Account account = new Account();
        account.setName(createAccountData.getAccountName());
        accountRepository.save(account);

        RoleView adminRole = roleService.createRole(RoleDto.createAdminRole(), account.getId());
        RoleView memberRole = roleService.createRole(RoleDto.createMemberRole(), account.getId());
        UserBasicView newUser = userService.createUser(createAccountData, account, Role.instantOf(adminRole.getId()));

        account.setDefaultRole(Role.instantOf(memberRole.getId()));
        account.setOwner(User.instantOf(newUser.getId()));
        accountRepository.save(account);

        return NewAccountDto.of(account);
    }

    public Optional<Account> getAccountById(int accountId) {
        return accountRepository.findById(accountId);
    }
}
