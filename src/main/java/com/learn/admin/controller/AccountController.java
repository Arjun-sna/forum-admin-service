package com.learn.admin.controller;

import com.learn.admin.dto.account.AccountBasicView;
import com.learn.admin.dto.account.CreateAccountDto;
import com.learn.admin.model.Account;
import com.learn.admin.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController()
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/account")
    public AccountBasicView createAccount(@Valid @RequestBody CreateAccountDto createAccountData) {
        return accountService.createAccount(createAccountData);
    }
}
