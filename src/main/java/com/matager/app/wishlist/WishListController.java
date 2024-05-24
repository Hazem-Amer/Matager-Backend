package com.matager.app.wishlist;

import com.matager.app.common.helper.res_model.ResponseModel;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;


@Slf4j
@RestController
@RequestMapping("/v1/store/{storeId}/wishlist")
@RequiredArgsConstructor
public class WishListController {

    private final WishListService wishListService;
    @GetMapping
    public ResponseEntity<ResponseModel> getWishList(@PathVariable Long storeId) {

        return ResponseEntity.ok().body(
                ResponseModel.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("WishList Items successfully retrieved")
                        .data(Map.of("WishList", wishListService.getWishListItems(storeId)))
                        .build());
    }

    @PostMapping("/add/{itemId}")
    public ResponseEntity<ResponseModel> addItemToWishList(@PathVariable Long storeId,@PathVariable Long itemId) {
        return ResponseEntity.ok().body(
                ResponseModel.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("Item added to WishList successfully")
                        .data(Map.of("WishListItem", wishListService.addItemToWishList(storeId,itemId)))
                        .build());
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<ResponseModel> removeItemFromWishList(@PathVariable Long storeId,@PathVariable Long itemId) {
        wishListService.removeItemFromWishList(storeId,itemId);
        return ResponseEntity.ok().body(
                ResponseModel.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("Item removed from WishList successfully")
                        .build());
    }
}
