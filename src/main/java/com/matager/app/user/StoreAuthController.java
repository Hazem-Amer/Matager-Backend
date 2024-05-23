package com.matager.app.user;

import com.matager.app.auth.AuthenticationFacade;
import com.matager.app.common.helper.res_model.ResponseModel;
import com.matager.app.owner.Owner;
import com.matager.app.store.StoreService;
import com.matager.app.token.TokenService;
import com.matager.app.user.User;
import com.matager.app.user.UserService;
import com.matager.app.user.model.NewUserModel;
import com.matager.app.user.model.SigninModel;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/v1/store/{storeId}/auth")
@RequiredArgsConstructor
public class StoreAuthController {

    private final UserService userService;
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
    public ResponseEntity<?> createUser(@PathVariable Long storeId, @Valid @RequestBody NewUserModel newUserModel) {
        ResponseModel.ResponseModelBuilder<?, ?> response = ResponseModel.builder()
                .timeStamp(LocalDateTime.now().toString());

        newUserModel.setStoreId(storeId);
        newUserModel.setRole(UserRole.STORE_USER);
        User user = userService.addNewUser(newUserModel);
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

        Map<String, Object> data = new HashMap<>();
        data.put("userName", user.getName());
        data.put("userEmail", user.getEmail());
        data.put("userRole", user.getRole());
        data.put("userDefaultStoreId", user.getDefaultStore() != null ? user.getDefaultStore().getId() : "No Default Store.");
        data.put("userStores",
                storeService.getStores(owner.getId()).stream()
                        .map(s ->
                                Map.of("id", s.getId(), "name", s.getName(), "iconUrl", "")).toArray());

        data.put("currencyCode", "EGP"); // TODO: change later
        data.put("currencySymbol", "$"); // TODO: change later
        data.put("token", tokenService.generateToken(user, jwt.getExpiresAt()));

        return ResponseEntity.ok(
                ResponseModel.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .message("User Data")
                        .data(data)
                        .build()
        );
    }



}
