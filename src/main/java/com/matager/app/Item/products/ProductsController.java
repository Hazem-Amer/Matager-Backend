package com.matager.app.Item.products;

import com.matager.app.common.helper.res_model.ResponseModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RequestMapping("/v1/store/{storeId}/products")
@RequiredArgsConstructor
@RestController
@Slf4j
public class ProductsController {
        private final ProductService productService;


    @GetMapping
    public ResponseEntity<ResponseModel> getProducts(@PathVariable Long storeId,
                                                  @RequestParam(name = "search", required = false) String name,
                                                  @RequestParam(required = false) Long categoryId,
                                                  @RequestParam(required = false) Long subCategoryId,
                                                  @RequestParam(required = false) Boolean isVisible,
                                                  @RequestParam(defaultValue = "listPrice;asc") String sort,
                                                  @RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "50") int size) {
        return ResponseEntity.ok().body(
                ResponseModel.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("Items of this store have been retrieved successfully")
                        .data(Map.of("Products", productService.getProductsFilteredAndSorted(storeId, name, categoryId, subCategoryId, isVisible, sort, page, size)))
                        .build());
    }
    @GetMapping("/{itemId}")
    public ResponseEntity<ResponseModel> getProduct(@PathVariable Long storeId,@PathVariable Long itemId) {
        return ResponseEntity.ok().body(
                ResponseModel.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("This item has been retrieved successfully")
                        .data(Map.of("Product", productService.getProduct(storeId ,itemId)))
                        .build());
    }

}
