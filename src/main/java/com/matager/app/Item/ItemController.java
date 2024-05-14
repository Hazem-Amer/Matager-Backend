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

    @PatchMapping
    public ResponseEntity<ResponseModel> updateItem(@RequestPart(required = false) List<MultipartFile> images, @RequestBody @Valid ItemModel itemsModel) {
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
                            .data(Map.of("items", itemService.updateItem(owner, user, store, itemsModel, images)))
                            .build());
        } catch (Exception e) {

            throw new RuntimeException(e);
        }
    }

}
