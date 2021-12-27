package com.learn.admin.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@Table(name = "users")
public class User {
    public User(User user) {
        this.id = user.id;
        this.password = user.password;
        this.email = user.email;
        this.firstName = user.firstName;
        this.lastName = user.lastName;
        this.setPasswordToken = user.setPasswordToken;
        this.setPasswordTokenExpiry = user.setPasswordTokenExpiry;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String firstName;
    private String lastName;

    @Column(unique = true)
    private String email;

    @JsonIgnore
    private String password;

    @Column(unique = true)
    @JsonIgnore
    private String setPasswordToken;
    @JsonIgnore
    private Date setPasswordTokenExpiry;
}
