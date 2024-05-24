package com.matager.app.category;

import com.matager.app.category.CategoryService;
import com.matager.app.common.helper.res_model.ResponseModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RequestMapping("/v1/store/{storeId}/category")
@RequiredArgsConstructor
@RestController
@Slf4j
public class StoreCategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<ResponseModel> getCategories(@PathVariable Long storeId) {

        return ResponseEntity.ok().body(
                ResponseModel.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("Store Categories have been retrieved successfully")
                        .data(Map.of("categories", categoryService.getCategories(storeId)))
                        .build());

    }
}
