package com.learn.admin.dto.user;

import com.learn.admin.dto.role.RoleBasicView;

public interface UserView extends UserBasicView {
    RoleBasicView getRole();

    int getAccountId();
}
