package com.matager.app.Item;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.matager.app.auth.AuthenticationFacade;
import com.matager.app.common.helper.res_model.ResponseModel;
import com.matager.app.owner.Owner;
import com.matager.app.store.Store;
import com.matager.app.token.TokenService;
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

    @GetMapping("/sync")
    public ResponseEntity<ResponseModel> syncItems(@RequestBody @Valid SyncItemsModel itemsModel) {
        ResponseModel.ResponseModelBuilder<?, ?> response = ResponseModel.builder().timeStamp(LocalDateTime.now().toString());

        try {
            User user = authenticationFacade.getAuthenticatedUser();
            return ResponseEntity.ok().body(
                    ResponseModel.builder()
                            .timeStamp(LocalDateTime.now().toString())
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .message("Items retrieved successfully")
                            .data(Map.of("items", itemService.syncItems(user, itemsModel)))
                            .build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @PostMapping
    public ResponseEntity<ResponseModel> saveItem(@RequestPart(required = false) List<MultipartFile> itemImages, @RequestPart @Valid ItemModel itemModel) throws JsonProcessingException {
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
                            .message("This item has been added successfully")
                            .data(Map.of("item", itemService.saveItem(owner, user, store, itemModel, itemImages)))
                            .build());
        } catch (Exception e) {
            log.error("Error adding this item" + itemModel.toString() + "reason: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @PatchMapping("/update_item")
    public ResponseEntity<ResponseModel> updateItem(@RequestBody @Valid ItemModel itemsModel) {
        ResponseModel.ResponseModelBuilder<?, ?> response = ResponseModel.builder().timeStamp(LocalDateTime.now().toString());
        try {
            User user = authenticationFacade.getAuthenticatedUser();
            Store store = user.getDefaultStore();
            Owner owner = user.getOwner();
            return ResponseEntity.ok().body(
                    ResponseModel.builder()
                            .timeStamp(LocalDateTime.now().toString())
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .message("Items saved successfully.")
                            .data(Map.of("items", itemService.updateItem(owner, user, store, itemsModel)))
                            .build());
        } catch (Exception e) {

            throw new RuntimeException(e);
        }
    }

}
