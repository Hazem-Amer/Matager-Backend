/*
 * @Abdullah Sallam
 */

package com.matager.app.store;

import com.matager.app.auth.AuthenticationFacade;
import com.matager.app.category.CategoryModel;
import com.matager.app.common.helper.res_model.ResponseModel;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @GetMapping("/{uuid}")
    public ResponseEntity<ResponseModel> getStore(@PathVariable String uuid) {
        return ResponseEntity.ok().body(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("Store retrieved successfully")
                        .data(Map.of("store", storeService.getStore(uuid)))
                        .build());
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> addStore(@RequestPart MultipartFile icon, @RequestPart @Validated NewStoreModel data) {

        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("Store added successfully.")
                        .data(Map.of("store", storeService.addStore(icon, data)))
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

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{uuid}")
    public ResponseEntity<?> updateStore(@PathVariable String uuid, @RequestPart(required = false) MultipartFile icon, @RequestPart("data") @Valid NewStoreModel data) {

        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("Store updated successfully.")
                        .data(Map.of("store", storeService.updateStore(uuid, icon, data)))
                        .build());
    }


}
