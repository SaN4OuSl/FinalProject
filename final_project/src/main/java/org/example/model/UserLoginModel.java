package org.example.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserLoginModel {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
