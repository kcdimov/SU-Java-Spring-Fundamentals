package com.coffee_shop.models.binding;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UserLoginBM {

    private String username;
    private String password;

    public UserLoginBM() {
    }

    @NotBlank(message = "Username cannot be empty")
    @Length(min = 5, max = 20, message = "Username must be minimum 5 and maximum 20 characters")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NotNull
    @Length(min = 3, message = "Password must be minimum 3 characters")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
