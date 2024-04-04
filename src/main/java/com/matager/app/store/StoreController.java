/*
 * @Abdullah Sallam
 */

package com.matager.app.store;

import at.orderking.bossApp.auth.AuthenticationFacade;
import at.orderking.bossApp.common.helper.res_model.ResponseModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/v1/store")
@RequiredArgsConstructor
@RestController
public class StoreController {

    private final AuthenticationFacade authenticationFacade;
    private final StoreService storeService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<?> getStores() {
        Long ownerId = authenticationFacade.getAuthenticatedUser().getOwner().getId();

        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("Stores fetched successfully.")
                        .data(Map.of("stores", storeService.getStores(ownerId)))
                        .build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> addStore(@RequestBody @Validated NewStoreModel newStoreModel) {

        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("Store added successfully.")
                        .data(Map.of("store", storeService.addStore(newStoreModel)))
                        .build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> deleteStore(@PathVariable String uuid) {

        Store deletedStore = storeService.deleteStore(uuid);

        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("Store " + deletedStore.getName() + " deleted successfully.")
                        .build());
    }


}
