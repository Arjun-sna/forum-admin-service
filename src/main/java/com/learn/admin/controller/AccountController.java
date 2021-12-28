package com.learn.admin.controller;

import com.learn.admin.model.Account;
import com.learn.admin.payload.CreateAccountData;
import com.learn.admin.service.AccountService;
import com.learn.admin.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController(Constants.API_V1)
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/account")
    public Account createAccount(@Valid @RequestBody CreateAccountData createAccountData) {
        return accountService.createAccount(createAccountData);
    }
}
