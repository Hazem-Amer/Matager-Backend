/*
 * @Abdullah Sallam
 */

package com.matager.app.user.model;

import at.orderking.bossApp.user.UserRole;
import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class ChangeRoleModel {
    @Email
    private String userEmail;
    private UserRole role;
}
