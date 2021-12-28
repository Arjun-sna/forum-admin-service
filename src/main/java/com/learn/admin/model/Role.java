package com.learn.admin.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private int accountId;

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
}
