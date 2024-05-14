package com.matager.app.subcategory;

import com.matager.app.auth.AuthenticationFacade;
import com.matager.app.category.CategoriesModel;
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
    public ResponseEntity<ResponseModel> addSubCategory(@RequestBody @Valid SubCategoryModel newSubCategoryModel) {
        ResponseModel.ResponseModelBuilder<?, ?> response = ResponseModel.builder().timeStamp(LocalDateTime.now().toString());
        try {
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
                            .data(Map.of("sub_category", subCategoryService.addSubCategory(owner,user, store, newSubCategoryModel)))
                            .build());
        } catch (Exception e) {
            log.error("Error adding this sub_category" + newSubCategoryModel.toString() + "reason: " + e.getMessage());
            return ResponseEntity.badRequest().body(
                    response
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .status(HttpStatus.BAD_REQUEST)
                            .message("Error adding this sub_category")
                            .reason(e.getMessage())
                            .build()
            );
        }
    }
    @GetMapping("/{subCategoryId}")
    public ResponseEntity<ResponseModel> getSubCategory(@PathVariable long subCategoryId) {
        ResponseModel.ResponseModelBuilder<?, ?> response = ResponseModel.builder().timeStamp(LocalDateTime.now().toString());
        try {
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
                            .data(Map.of("sub_category", subCategoryService.getSubCategory(owner, user,store, subCategoryId)))
                            .build());
        } catch (Exception e) {
            log.error("Error retrieving this sub_category"+subCategoryId  + "reason: " + e.getMessage());
            return ResponseEntity.badRequest().body(
                    response
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .status(HttpStatus.BAD_REQUEST)
                            .message("Error retrieving this sub_category")
                            .reason(e.getMessage())
                            .build()
            );
        }
    }
    @GetMapping
    public ResponseEntity<ResponseModel> getSubCategories() {
        ResponseModel.ResponseModelBuilder<?, ?> response = ResponseModel.builder().timeStamp(LocalDateTime.now().toString());
        try {
            User user = authenticationFacade.getAuthenticatedUser();
            Store store = user.getDefaultStore();
            Owner owner = user.getOwner();
            log.info("user:" + user);
            return ResponseEntity.ok().body(
                    ResponseModel.builder()
                            .timeStamp(LocalDateTime.now().toString())
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .message("sub_categories have been retrieved  successfully")
                            .data(Map.of("sub_categories", subCategoryService.getSubCategories(owner, user,store)))
                            .build());
        } catch (Exception e) {
            log.error("Error retrieving categories" +"\n" + "reason: " + e.getMessage());
            return ResponseEntity.badRequest().body(
                    response
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .status(HttpStatus.BAD_REQUEST)
                            .message("Error retrieving categories")
                            .reason(e.getMessage())
                            .build()
            );
        }
    }

    @PatchMapping("/{subCategoryId}")
    public ResponseEntity<ResponseModel> updateSubCategory(@PathVariable long subCategoryId,@RequestBody @Valid SubCategoryModel newSubCategory) {
        ResponseModel.ResponseModelBuilder<?, ?> response = ResponseModel.builder().timeStamp(LocalDateTime.now().toString());
        try {
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
                            .data(Map.of("sub_category", subCategoryService.updateSubCategory(owner, store,subCategoryId, newSubCategory)))
                            .build());
        } catch (Exception e) {
            log.error("Error updating this sub_category" + newSubCategory.toString() + "reason: " + e.getMessage());
            return ResponseEntity.badRequest().body(
                    response
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .status(HttpStatus.BAD_REQUEST)
                            .message("Error updating this sub_category")
                            .reason(e.getMessage())
                            .build()
            );
        }
    }
    @DeleteMapping("/{subCategoryId}")
    public ResponseEntity<ResponseModel> deleteCategory(@PathVariable long subCategoryId) {
        ResponseModel.ResponseModelBuilder<?, ?> response = ResponseModel.builder().timeStamp(LocalDateTime.now().toString());
        try {
            User user = authenticationFacade.getAuthenticatedUser();
            Store store = user.getDefaultStore();
            Owner owner = user.getOwner();
            log.info("user:" + user);
            subCategoryService.deleteSubCategory(owner,store,subCategoryId);
            return ResponseEntity.ok().body(
                    ResponseModel.builder()
                            .timeStamp(LocalDateTime.now().toString())
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .message("This sub_category has been deleted successfully")
                            .build());
        } catch (Exception e) {
            log.error("Error deleting this sub_category" + subCategoryId + "reason: " + e.getMessage());
            return ResponseEntity.badRequest().body(
                    response
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .status(HttpStatus.BAD_REQUEST)
                            .message("Error deleting this sub_category")
                            .reason(e.getMessage())
                            .build()
            );
        }
    }
}
