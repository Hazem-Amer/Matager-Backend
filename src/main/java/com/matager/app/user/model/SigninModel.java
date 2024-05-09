/*
 * @Abdullah Sallam
 */

package com.matager.app.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Data
public class SigninModel {

    @JsonIgnore
    private int shardNum;

    @Email
    private String email;
    @NotBlank
    private String password;
}
