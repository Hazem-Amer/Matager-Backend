package com.matager.app.category;

import com.matager.app.Item.ItemService;
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
@RequestMapping("/v1/category")
@RequiredArgsConstructor
public class CategoryController {
    private final AuthenticationFacade authenticationFacade;
    private final ItemService itemService;
    private final CategoriesService categoriesService;

    @PostMapping
    public ResponseEntity<ResponseModel> addCategory(@RequestPart MultipartFile imageFile,@RequestPart MultipartFile iconFile,  @RequestPart @Valid CategoriesModel newCategory) {
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
                            .message("This category has been added successfully")
                            .data(Map.of("category", categoriesService.addCategory(owner, store, newCategory,imageFile,iconFile)))
                            .build());
        } catch (Exception e) {
            log.error("Error adding this category" + newCategory.toString() + "reason: " + e.getMessage());
            return ResponseEntity.badRequest().body(
                    response
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .status(HttpStatus.BAD_REQUEST)
                            .message("Error adding this category")
                            .reason(e.getMessage())
                            .build()
            );
        }
    }
    @GetMapping("/{categoryId}")
    public ResponseEntity<ResponseModel> getCategory(@PathVariable long categoryId) {
        ResponseModel.ResponseModelBuilder<?, ?> response = ResponseModel.builder().timeStamp(LocalDateTime.now().toString());
        try {
            User user = authenticationFacade.getAuthenticatedUser();
            log.info("user:" + user);
            return ResponseEntity.ok().body(
                    ResponseModel.builder()
                            .timeStamp(LocalDateTime.now().toString())
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .message("This category has been retrieved successfully")
                            .data(Map.of("category", categoriesService.getCategory(categoryId)))
                            .build());
        } catch (Exception e) {
            log.error("Error retrieving this category"+categoryId  + "reason: " + e.getMessage());
            return ResponseEntity.badRequest().body(
                    response
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .status(HttpStatus.BAD_REQUEST)
                            .message("Error retrieving this category")
                            .reason(e.getMessage())
                            .build()
            );
        }
    }
    @GetMapping
    public ResponseEntity<ResponseModel> getCategories(@RequestParam(value = "storeId", required = true) Long storeId) {
        ResponseModel.ResponseModelBuilder<?, ?> response = ResponseModel.builder().timeStamp(LocalDateTime.now().toString());
        try {
            User user = authenticationFacade.getAuthenticatedUser();
            log.info("user:" + user);
            return ResponseEntity.ok().body(
                    ResponseModel.builder()
                            .timeStamp(LocalDateTime.now().toString())
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .message("Categories have been retrieved  successfully")
                            .data(Map.of("category", categoriesService.getCategories(storeId)))
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

    @PatchMapping("/{categoryId}")
    public ResponseEntity<ResponseModel> updateCategory(@RequestPart MultipartFile newImageFile,@RequestPart MultipartFile newIconFile,@PathVariable long categoryId,@RequestPart @Valid CategoriesModel newCategory) {
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
                            .message("This item has been updated successfully")
                            .data(Map.of("category", categoriesService.updateCategory(owner, store,newImageFile,newIconFile,categoryId, newCategory)))
                            .build());
        } catch (Exception e) {
            log.error("Error updating this item" + newCategory.toString() + "reason: " + e.getMessage());
            return ResponseEntity.badRequest().body(
                    response
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .status(HttpStatus.BAD_REQUEST)
                            .message("Error updating this item")
                            .reason(e.getMessage())
                            .build()
            );
        }
    }
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ResponseModel> deleteCategory(@PathVariable long categoryId) {
        ResponseModel.ResponseModelBuilder<?, ?> response = ResponseModel.builder().timeStamp(LocalDateTime.now().toString());
        try {
            User user = authenticationFacade.getAuthenticatedUser();
            Store store = user.getDefaultStore();
            Owner owner = user.getOwner();
            log.info("user:" + user);
            categoriesService.deleteCategory(owner,store,categoryId);
            return ResponseEntity.ok().body(
                    ResponseModel.builder()
                            .timeStamp(LocalDateTime.now().toString())
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .message("This item has been deleted successfully")
                            .build());
        } catch (Exception e) {
            log.error("Error deleting this item" + categoryId + "reason: " + e.getMessage());
            return ResponseEntity.badRequest().body(
                    response
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .status(HttpStatus.BAD_REQUEST)
                            .message("Error deleting this item")
                            .reason(e.getMessage())
                            .build()
            );
        }
    }
}
