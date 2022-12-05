package com.spring.server.dto.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class CreateUserDTO {
    @NotNull(message = "Введите логин")
    private String username;
    @NotNull(message = "Введите пароль")
    private String password;
    @NotNull(message = "Введите почту")
    @Email(message = "Введите корректную почту")
    private String email;

    public CreateUserDTO(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    //region Getter and Setter

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    //endregion
}
