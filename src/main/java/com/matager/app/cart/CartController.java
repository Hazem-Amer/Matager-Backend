package com.matager.app.cart;

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
@RequestMapping("/v1/store/{storeId}/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    @GetMapping
    public ResponseEntity<ResponseModel> getCart(@PathVariable Long storeId) {

        return ResponseEntity.ok().body(
                ResponseModel.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("CartItems successfully retrieved")
                        .data(Map.of("Cart", cartService.getCartItems(storeId)))
                        .build());
    }

    @PostMapping("/add/{itemId}")
    public ResponseEntity<ResponseModel> addItemToCart(@PathVariable Long storeId,@PathVariable Long itemId) {
        return ResponseEntity.ok().body(
                ResponseModel.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("Item added to cart successfully")
                        .data(Map.of("CartItem", cartService.addItemToCart(storeId,itemId)))
                        .build());
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<ResponseModel> removeItemFromCart(@PathVariable Long storeId,@PathVariable Long itemId) {
        cartService.removeItemFromCart(storeId,itemId);
        return ResponseEntity.ok().body(
                ResponseModel.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("Item removed from cart successfully")
                        .build());
    }

    @PatchMapping("/add/{itemId}")
    public ResponseEntity<ResponseModel> updateItemQuantity(@PathVariable Long storeId,
                                                            @PathVariable Long itemId,
                                                            @RequestBody Map<String, Double> quantity) {
        cartService.updateCartItemQuantity(storeId, itemId, quantity.get("quantity"));
        return ResponseEntity.ok().body(
                ResponseModel.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("Item quantity updated successfully")
                        .build());
    }
    @PostMapping("/checkout")
    public ResponseEntity<ResponseModel> cartCheckOut(@PathVariable Long storeId)
    {

        cartService.cartCheckOut(storeId);
        return ResponseEntity.ok().body(
                ResponseModel.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("Order has been placed successfully")
                        .build());
    }
}