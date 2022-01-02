package com.learn.admin.dto.account;

import com.learn.admin.model.Account;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NewAccountDto implements AccountBasicView {
    private int id;
    private String name;

    public static NewAccountDto of(Account account) {
        return new NewAccountDto(account.getId(), account.getName());
    }
}
