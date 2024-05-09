/*
 * @Abdullah Sallam
 */

package com.matager.app.user.model;

import lombok.Data;

@Data
public class ChangeRoleModel {
    @Email
    private String userEmail;
    private UserRole role;
}
