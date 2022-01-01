package com.learn.admin.dto.user;

import com.learn.admin.dto.account.AccountBasicView;
import com.learn.admin.dto.role.RoleView;

public interface UserCompleteView extends UserBasicView {
    RoleView getRole();

    AccountBasicView getAccount();
}
