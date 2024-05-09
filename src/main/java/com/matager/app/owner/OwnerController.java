/*
 * @Abdullah Sallam
 */

package com.matager.app.owner;

import com.matager.app.common.helper.res_model.ResponseModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/v1/owner")
@RequiredArgsConstructor
public class OwnerController {

    private final OwnerService ownerService;

    // Called From Central Server, can't be called from anywhere else.
//    @PreAuthorize("hasRole('SERVER_ADMIN')")
    @PostMapping
    public ResponseEntity<?> addOwner(@RequestBody @Valid NewOwnerModel newOwner) {
        return ResponseEntity.ok(ResponseModel.builder()
                .statusCode(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("Owner created successfully.")
                .data(Map.of("owner", ownerService.addNewOwner(newOwner)))
                .build()
        );
    }

}
