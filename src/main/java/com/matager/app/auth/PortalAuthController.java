package com.matager.app.auth;

import com.matager.app.common.helper.res_model.ResponseModel;
import com.matager.app.owner.NewOwnerModel;
import com.matager.app.owner.Owner;
import com.matager.app.owner.OwnerService;
import com.matager.app.store.StoreService;
import com.matager.app.token.TokenService;
import com.matager.app.user.User;
import com.matager.app.user.UserService;
import com.matager.app.user.model.NewUserModel;
import com.matager.app.user.model.SigninModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class PortalAuthController {

    private final UserService userService;
    private final OwnerService ownerService;
    private final AuthenticationFacade authenticationFacade;
    private final StoreService storeService;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<?> signin(@RequestBody @Valid SigninModel form) {
        ResponseModel.ResponseModelBuilder<?, ?> response = ResponseModel.builder().timeStamp(LocalDateTime.now().toString());

        try {
            User user = userService.signinUser(form);
            String token = tokenService.generateToken(user);
            log.info("Logged In: " + user);
            return ResponseEntity.ok(
                    response
                            .statusCode(HttpStatus.OK.value())
                            .status(HttpStatus.OK)
                            .message("User signed in successfully")
                            .data(Map.of("token", token))
                            .build());
        } catch (Exception e) {
            log.error("Error login User With Credentials: " + form + "reason: " + e.getMessage());
            return ResponseEntity.badRequest().body(
                    response
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .status(HttpStatus.BAD_REQUEST)
                            .message("Error login user with entered creds.")
                            .reason(e.getMessage())
                            .build()
            );
        }
    }

    @PostMapping("/sign_up")
    public ResponseEntity<?> createUser(@Valid @RequestBody NewOwnerModel newOwnerModel) {
        ResponseModel.ResponseModelBuilder<?, ?> response = ResponseModel.builder()
                .timeStamp(LocalDateTime.now().toString());


        User user = ownerService.addNewOwner(newOwnerModel);
        String token = tokenService.generateToken(user);
        log.info("new user created: " + user.getEmail());

        return ResponseEntity.ok(
                response
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .message("User created successfully.")
                        .data(Map.of("token", token))
                        .build());

    }


    @GetMapping("/@me")
    public ResponseEntity<?> getUserData() {
        User user = authenticationFacade.getAuthenticatedUser();
        Owner owner = user.getOwner();
        Jwt jwt = authenticationFacade.getJwt();

        return ResponseEntity.ok(
                ResponseModel.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .message("User Data")
                        .data(
                                Map.of("userName", user.getName(),
                                        "userEmail", user.getEmail(),
                                        "userRole", user.getRole(),
                                        "userDefaultStoreId", user.getDefaultStore() != null ? user.getDefaultStore().getId() : "No Default Store.",
                                        "userStores", storeService.getStores(owner.getId()).stream().map(s -> Map.of("id", s.getId(), "name", s.getName(), "iconUrl", "")).toArray(),
                                        "currencyCode", "EGP", // TODO: change later
                                        "currencySymbol", "$", // TODO: change later
                                        "token", tokenService.generateToken(user, jwt.getExpiresAt()))
                        )
                        .build()
        );
    }



}
