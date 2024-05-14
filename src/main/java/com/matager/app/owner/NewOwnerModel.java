/*
 * @Abdullah Sallam
 */

package com.matager.app.owner;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

@Data
@Validated
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class NewOwnerModel {
    @NotEmpty(message = "Name can not be empty.")
    private String name;
    @Email(message = "Email is Invalid.")
    private String email;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$",
            message = "The password must be 8 to 20 length and contains at least:"
                    + "\n-1 Upper Case Character ~*!@#&(){}<>:;',?/^+=\n"
                    + "\n-1 Numeric Value\n"
                    + "\n-1 Special Character.")
    private String password;

}
