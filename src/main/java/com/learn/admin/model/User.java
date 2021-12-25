package com.learn.admin.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Data
@Builder
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String firstName;
    private String lastName;

    @Column(unique = true)
    private String email;
    private String password;

    @Column(unique = true)
    private String setPasswordToken;
    private Date setPasswordTokenExpiry;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_connections",
            joinColumns = @JoinColumn(name = "user_id_f"),
            inverseJoinColumns = @JoinColumn(name = "user_id_t")
    )
    private Set<User> friends;
}
