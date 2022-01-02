package com.learn.admin.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "users")
public class User extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String firstName;
    private String lastName;

    @Column(unique = true)
    private String email;

    @JsonIgnore
    private String password;

    @Column(name = "account_id", insertable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    private int accountId;

    @OneToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    @Column(name = "role_id", insertable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    private int roleId;

    @OneToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;

    public static User instantOf(int id) {
        User user = new User();
        user.setId(id);
        return user;
    }
}
