/*
 * @Abdullah Sallam
 */

package com.matager.app.user.model;

import com.matager.app.user.UserRole;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class ChangeRoleModel {
    @Email
    private String userEmail;
    private UserRole role;
}
