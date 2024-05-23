package com.matager.app.StoreCatSubCategory;

import com.matager.app.Item.ItemService;
import com.matager.app.auth.AuthenticationFacade;
import com.matager.app.common.helper.res_model.ResponseModel;
import com.matager.app.user.User;
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
public class StoreCatSubCategoryController {
    private final AuthenticationFacade authenticationFacade;
    private final StoreCatSubCategoryService storeCatSubCategoryService;

    @GetMapping
    public ResponseEntity<ResponseModel> getCategories(@PathVariable Long storeId) {
        User user = authenticationFacade.getAuthenticatedUser();
        log.info("user:" + user);
        return ResponseEntity.ok().body(
                ResponseModel.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("Store Categories have been retrieved successfully")
                        .data(Map.of("storeCategories", storeCatSubCategoryService.getCategories(storeId)))
                        .build());

    }
}
