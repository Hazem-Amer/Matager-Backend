package com.matager.app.user.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SignInPosModel {

    private String username;
    @NotBlank
    private String password;
}
