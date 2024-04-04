/*
 * @Abdullah Sallam
 */

package com.matager.app.owner;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@Validated
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class NewOwnerModel {

    private int shardNum;

    private Long id;
    private String ownerUuid;
    @Email(message = "Email is Invalid.")
    private String email;
    @NotEmpty(message = "Name can not be empty.")
    private String name;

}
