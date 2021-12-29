package com.learn.admin.payload;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@Builder
public class CreateRoleData {
    @Size(min = 3, max = 20)
    private String name;

    private boolean createUser;
    private boolean editUser;
    private boolean archiveUser;

    private boolean createPost;
    private boolean editPost;
    private boolean deletePost;
    private boolean hidePost;

    private boolean createTopic;
    private boolean editTopic;
    private boolean deleteTopic;
    private boolean hideTopic;

    private boolean viewHidden;

    private boolean createRole;
    private boolean editRole;
    private boolean deleteRole;

    public static CreateRoleData createAdminRole() {
        return CreateRoleData.builder()
                .name("Admin")
                .createUser(true)
                .editUser(true)
                .archiveUser(true)
                .createPost(true)
                .editPost(true)
                .deletePost(true)
                .hidePost(true)
                .createTopic(true)
                .editTopic(true)
                .deleteTopic(true)
                .hideTopic(true)
                .viewHidden(true)
                .createRole(true)
                .editRole(true)
                .deleteRole(true)
                .build();
    }
}
