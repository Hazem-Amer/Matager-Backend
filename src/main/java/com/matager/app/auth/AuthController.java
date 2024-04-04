/*
 * @Abdullah Sallam
 */

package com.matager.app.auth;

import at.orderking.bossApp.common.helper.res_model.ResponseModel;
import at.orderking.bossApp.owner.Owner;
import at.orderking.bossApp.store.Store;
import at.orderking.bossApp.store.StoreService;
import at.orderking.bossApp.token.TokenService;
import at.orderking.bossApp.user.User;
import at.orderking.bossApp.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final TokenService tokenService;
    private final AuthenticationFacade authenticationFacade;
    private final StoreService storeService;

    @PreAuthorize("hasAnyRole('SERVER_ADMIN','ADMIN')")
    @GetMapping("/pos/token")
    public ResponseEntity<?> generatePOSToken(@RequestParam String storeUuid) {
        ResponseModel.ResponseModelBuilder<?, ?> response = ResponseModel.builder()
                .timeStamp(LocalDateTime.now().toString());

        User user = userService.getPOSUser(storeUuid);
        Owner owner = user.getOwner();

        String token = tokenService.generateEndlessToken(user, owner.getShardNum()); // Endless token for now

        log.info("POS token generated: " + user.getName());

        return ResponseEntity.ok(
                response
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .message("POS token generated successfully")
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
                                        "token", tokenService.generateToken(user, owner.getShardNum(), jwt.getExpiresAt()))
                        )
                        .build()
        );
    }


    @PreAuthorize("hasRole('POS')")
    @GetMapping("/pos/@me")
    public ResponseEntity<?> getPosData() {
        User user = authenticationFacade.getAuthenticatedUser();

        Store store = user.getDefaultStore();
        Owner owner = store.getOwner();

        Map<String, Object> data = new HashMap<>();

        data.put("ownerUuid", owner.getUuid());
        data.put("ownerName", owner.getName());
        data.put("storeUuid", store.getUuid());
        data.put("storeName", store.getName());

        return ResponseEntity.ok(
                ResponseModel.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .message("POS Data")
                        .data(data)
                        .build()
        );
    }

}
