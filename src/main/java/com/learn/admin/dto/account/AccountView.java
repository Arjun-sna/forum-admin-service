package com.learn.admin.dto.account;

import com.learn.admin.dto.role.RoleBasicView;
import com.learn.admin.dto.user.UserBasicView;

public interface AccountView extends AccountBasicView {
    UserBasicView getOwner();
    RoleBasicView getDefaultRole();
}
