package com.matager.app.Item;

import com.matager.app.auth.AuthenticationFacade;
import com.matager.app.common.helper.res_model.ResponseModel;
import com.matager.app.owner.Owner;
import com.matager.app.store.Store;
import com.matager.app.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/v1/item")
@RequiredArgsConstructor
public class ItemController {

    private final AuthenticationFacade authenticationFacade;
    private final ItemService itemService;

    @PostMapping
    public ResponseEntity<ResponseModel> saveItem(@RequestPart(required = false) List<MultipartFile> images, @RequestPart("data") @Valid ItemModel itemModel) {

        User user = authenticationFacade.getAuthenticatedUser();
        Store store = user.getDefaultStore();
        Owner owner = user.getOwner();
        log.info("user:" + user);
        return ResponseEntity.ok().body(
                ResponseModel.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("This item has been added successfully")
                        .data(Map.of("item", itemService.saveItem(owner, user, store, itemModel, images)))
                        .build());

    }

    @GetMapping("/{itemId}")
    public ResponseEntity<ResponseModel> getItem(@PathVariable Long itemId) {
        return ResponseEntity.ok().body(
                ResponseModel.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("This item has been retrieved successfully")
                        .data(Map.of("item", itemService.getItem(itemId)))
                        .build());
    }

    @GetMapping
    public ResponseEntity<ResponseModel> getItems(@RequestParam Long storeId, @RequestParam(name = "search",required = false) String name,
                                                  @RequestParam(required = false) Long categoryId, @RequestParam(required = false) Long subCategoryId,
                                                  @RequestParam(required = false) Boolean isVisible,@RequestParam(required = false) String sort ,@RequestParam int page, @RequestParam int size) {
        return ResponseEntity.ok().body(
                ResponseModel.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("Items of this store have been retrieved successfully")
                        .data(Map.of("items", itemService.getItemsFilteredAndSorted(storeId, name, categoryId, subCategoryId, isVisible, sort,page, size)))
                        .build());
    }


    @PatchMapping("/{itemId}")
    public ResponseEntity<ResponseModel> updateItem(@PathVariable Long itemId,@RequestParam Long storeId,@RequestPart(required = false) List<MultipartFile> images, @RequestPart("data") @Valid ItemModel itemsModel) {
        return ResponseEntity.ok().body(
                ResponseModel.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("This item has been updated successfully")
                        .data(Map.of("item", itemService.updateItem(storeId,itemId, itemsModel,images)))
                        .build());
    }


    @DeleteMapping("/{itemId}")
    public ResponseEntity<ResponseModel> deleteItem(@PathVariable Long itemId) {
        itemService.deleteItem(itemId);
        return ResponseEntity.ok().body(
                ResponseModel.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("This item has been deleted successfully")
                        .build());
    }


}
