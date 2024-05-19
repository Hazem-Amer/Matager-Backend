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
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<ResponseModel> addCategory(@RequestPart(required = false) MultipartFile image, @RequestPart(required = false) MultipartFile icon, @RequestPart("data") @Valid CategoryModel newCategory) {
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
                        .data(Map.of("category", categoryService.addCategory(owner, store, newCategory, image, icon)))
                        .build());

    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<ResponseModel> getCategory(@PathVariable Long categoryId) {
        User user = authenticationFacade.getAuthenticatedUser();
        log.info("user:" + user);
        return ResponseEntity.ok().body(
                ResponseModel.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("This category has been retrieved successfully")
                        .data(Map.of("category", categoryService.getCategory(categoryId)))
                        .build()
        );
    }

    @GetMapping
    public ResponseEntity<ResponseModel> getCategories(@RequestParam(value = "storeId", required = true) Long storeId) {
        User user = authenticationFacade.getAuthenticatedUser();
        log.info("user:" + user);
        return ResponseEntity.ok().body(
                ResponseModel.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("Categories have been retrieved  successfully")
                        .data(Map.of("category", categoryService.getCategories(storeId)))
                        .build());

    }

    @PatchMapping("/{categoryId}")
    public ResponseEntity<ResponseModel> updateCategory(@RequestPart(required = false) MultipartFile image, @RequestPart(required = false) MultipartFile icon, @PathVariable Long categoryId, @RequestPart("data") @Valid CategoryModel newCategory) {

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
                        .data(Map.of("category", categoryService.updateCategory(owner, store, image, icon, categoryId, newCategory)))
                        .build());

    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ResponseModel> deleteCategory(@PathVariable Long categoryId) {
        User user = authenticationFacade.getAuthenticatedUser();
        Store store = user.getDefaultStore();
        Owner owner = user.getOwner();
        log.info("user:" + user);
        categoryService.deleteCategory(owner, store, categoryId);
        return ResponseEntity.ok().body(
                ResponseModel.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("This item has been deleted successfully")
                        .build());

    }
}
