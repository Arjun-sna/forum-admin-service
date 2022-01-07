package com.learn.admin.config.security.token;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class Token {
    private String email;
    private String username;
    private TokenOperation operation;
}
