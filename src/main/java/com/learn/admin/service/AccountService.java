package com.learn.admin.service;

import com.learn.admin.dto.account.AccountBasicView;
import com.learn.admin.dto.account.AccountView;
import com.learn.admin.dto.account.CreateAccountDto;
import com.learn.admin.model.Account;

import java.util.Optional;

public interface AccountService {
    AccountBasicView createAccount(CreateAccountDto createAccountData);

    Optional<AccountView> getAccountById(int accountId);
}
