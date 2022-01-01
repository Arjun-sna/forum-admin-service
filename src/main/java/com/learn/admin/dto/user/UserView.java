package com.learn.admin.dto.user;

import com.learn.admin.model.Account;
import com.learn.admin.model.Role;

public interface UserView extends UserBasicView {
    Role getRole();
}
