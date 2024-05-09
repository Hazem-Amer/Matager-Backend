package com.matager.app.auth;

import com.matager.app.common.helper.res_model.ResponseModel;
import com.matager.app.token.TokenService;
import com.matager.app.user.User;
import com.matager.app.user.central.CentralUserService;
import com.matager.app.user.model.NewUserModel;
import com.matager.app.user.model.SigninModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/v1/central/auth")
@RequiredArgsConstructor
public class CentralAuthController {

    private final CentralUserService centralUserService;
    private final TokenService tokenService;

    // Called From Central Server, can't be called from anywhere else.
    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody @Valid SigninModel form) {
        ResponseModel.ResponseModelBuilder<?, ?> response = ResponseModel.builder().timeStamp(LocalDateTime.now().toString());

        try {
            User user = centralUserService.signinUser(form);
            String token = tokenService.generateToken(user, form.getShardNum());
            log.info("Signed in: " + user);
            return ResponseEntity.ok(
                    response
                            .statusCode(HttpStatus.OK.value())
                            .status(HttpStatus.OK)
                            .message("User signed in successfully")
                            .data(Map.of("token", token))
                            .build());
        } catch (Exception e) {
            log.error("Error Signing User With Credentials: " + form + "reason: " + e.getMessage());
            return ResponseEntity.badRequest().body(
                    response
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .status(HttpStatus.BAD_REQUEST)
                            .message("Error signing user with entered creds.")
                            .reason(e.getMessage())
                            .build()
            );
        }
    }

    // Called From Central Server, can't be called from anywhere else.
//    @PreAuthorize("hasRole('SERVER_ADMIN')") // to be added later
    @PostMapping("/create_user")
    public ResponseEntity<?> createUser(@Valid @RequestBody NewUserModel newUserModel) {
        ResponseModel.ResponseModelBuilder<?, ?> response = ResponseModel.builder()
                .timeStamp(LocalDateTime.now().toString());

        User user = centralUserService.signNewUser(newUserModel);
        String token = tokenService.generateToken(user, newUserModel.getShardNum());
        log.info("new user created: " + user.getEmail());

        return ResponseEntity.ok(
                response
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .message("User created successfully.")
                        .data(Map.of("token", token))
                        .build());

    }

}
