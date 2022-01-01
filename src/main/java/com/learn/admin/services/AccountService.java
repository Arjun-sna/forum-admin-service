package com.learn.admin.services;

import com.learn.admin.dto.account.CreateAccountDto;
import com.learn.admin.model.Account;

import java.util.Optional;

public interface AccountService {
    Account createAccount(CreateAccountDto createAccountData);
    Optional<Account> getAccountById(int accountId);
}
