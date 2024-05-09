package com.matager.app.Item;

import com.matager.app.auth.AuthenticationFacade;
import com.matager.app.common.helper.res_model.ResponseModel;
import com.matager.app.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/v1/item")
@RequiredArgsConstructor
public class ItemController {

    private final AuthenticationFacade authenticationFacade;
    private final ItemService itemService;

    @PostMapping("/sync")
    public ResponseEntity<ResponseModel> syncItems(@RequestBody @Valid SyncItemsModel itemsModel) {
        User user = authenticationFacade.getAuthenticatedUser();

        return ResponseEntity.ok().body(
                ResponseModel.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("Items saved successfully.")
                        .data(Map.of("items", itemService.syncItems(user, itemsModel)))
                        .build());
    }

}
