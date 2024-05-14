package com.matager.app.subcategory;

import com.matager.app.auth.AuthenticationFacade;
import com.matager.app.common.helper.res_model.ResponseModel;
import com.matager.app.owner.Owner;
import com.matager.app.store.Store;
import com.matager.app.user.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/v1/sub_category")
@RequiredArgsConstructor
public class SubCategoryController {
    private final AuthenticationFacade authenticationFacade;
    private final SubCategoriesService subCategoryService;

    @PostMapping
    public ResponseEntity<ResponseModel> addSubCategory(@RequestPart(required = false) MultipartFile icon, @RequestPart("data") @Valid SubCategoryModel subCategoryModel) {
        User user = authenticationFacade.getAuthenticatedUser();
        Store store = user.getDefaultStore();
        Owner owner = user.getOwner();
        log.info("user:" + user);
        return ResponseEntity.ok().body(
                ResponseModel.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("This sub_category has been added successfully")
                        .data(Map.of("sub_category", subCategoryService.addSubCategory(owner, user, store, icon, subCategoryModel)))
                        .build());
    }

    @GetMapping("/{subCategoryId}")
    public ResponseEntity<ResponseModel> getSubCategory(@PathVariable Long subCategoryId) {
        User user = authenticationFacade.getAuthenticatedUser();
        Store store = user.getDefaultStore();
        Owner owner = user.getOwner();
        log.info("user:" + user);
        return ResponseEntity.ok().body(
                ResponseModel.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("This sub_category has been retrieved successfully")
                        .data(Map.of("sub_category", subCategoryService.getSubCategory(owner, user, store, subCategoryId)))
                        .build());
    }

    @GetMapping
    public ResponseEntity<ResponseModel> getSubCategories(@RequestParam(value = "storeId", required = true) Long storeId) {

        User user = authenticationFacade.getAuthenticatedUser();
        log.info("user:" + user);
        return ResponseEntity.ok().body(
                ResponseModel.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("sub_categories have been retrieved  successfully")
                        .data(Map.of("sub_categories", subCategoryService.getSubCategories(storeId)))
                        .build());
    }

    @PatchMapping("/{subCategoryId}")
    public ResponseEntity<ResponseModel> updateSubCategory(@PathVariable Long subCategoryId, @RequestPart(required = false) MultipartFile icon, @RequestPart("data") @Valid SubCategoryModel newSubCategory) {
        User user = authenticationFacade.getAuthenticatedUser();
        Store store = user.getDefaultStore();
        Owner owner = user.getOwner();
        log.info("user:" + user);
        return ResponseEntity.ok().body(
                ResponseModel.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("This sub_category has been updated successfully")
                        .data(Map.of("sub_category", subCategoryService.updateSubCategory(owner, store, subCategoryId, icon, newSubCategory)))
                        .build());

    }

    @DeleteMapping("/{subCategoryId}")
    public ResponseEntity<ResponseModel> deleteCategory(@PathVariable Long subCategoryId) {
        User user = authenticationFacade.getAuthenticatedUser();
        Store store = user.getDefaultStore();
        Owner owner = user.getOwner();

        subCategoryService.deleteSubCategory(owner, store, subCategoryId);
        return ResponseEntity.ok().body(
                ResponseModel.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("This sub_category has been deleted successfully")
                        .build());

    }
}
