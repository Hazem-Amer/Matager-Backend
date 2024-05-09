/*
 * @Abdullah Sallam
 */

package com.matager.app.user;

import com.matager.app.auth.AuthenticationFacade;
import com.matager.app.common.helper.res_model.ResponseModel;
import com.matager.app.user.model.ChangeRoleModel;
import com.matager.app.user.model.NewUserModel;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.Map;

@RequestMapping("/v1/user")
@RequiredArgsConstructor
@RestController
@Slf4j
public class UserController {

    private final AuthenticationFacade authenticationFacade;
    private final UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<?> getUsers() {
        Long ownerId = authenticationFacade.getAuthenticatedUser().getOwner().getId();

        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("Users Fetched Successfully.")
                        .data(Map.of("users", userService.getUsersByOwner(ownerId)))
                        .build());
    }

    @PreAuthorize("hasAnyRole('SERVER_ADMIN','ADMIN')")
    @PostMapping
    public ResponseEntity<?> addNewUser(@Valid @RequestBody NewUserModel newUserModel) {
        ResponseModel.ResponseModelBuilder<?, ?> response = ResponseModel.builder()
                .timeStamp(LocalDateTime.now().toString());

        userService.addNewUser(newUserModel);

        return ResponseEntity.ok(
                response
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .message("User: " + newUserModel.getName() + " created successfully")
                        .build());

    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> deleteUser(@PathVariable String uuid) {

        userService.deleteUser(uuid);

        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
//                        .message("User " + userService.deleteUser(uuid).getName() + " Deleted Successfully.")
                        .message("Not implemented yet.")
                        .build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/role")
    public ResponseEntity<?> changeUserRole(@RequestBody @Valid ChangeRoleModel changeRoleModel) {
        userService.changeRole(changeRoleModel.getUserEmail(), changeRoleModel.getRole());

        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("User Role Changed Successfully.")
                        .build());
    }
}
