/*
 * @Abdullah Sallam
 */

package com.matager.app.order;

import com.matager.app.auth.AuthenticationFacade;
import com.matager.app.common.helper.res_model.ResponseModel;
import com.matager.app.order.model.OrderModel;
import com.matager.app.order.model.SyncOrdersModel;
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
@RequestMapping("/v1/order")
@RequiredArgsConstructor
public class OrderController {

    private final AuthenticationFacade authenticationFacade;
    private final OrderService orderService;

//    @PostMapping("/sync")
//    public ResponseEntity<ResponseModel> syncOrders(@RequestBody @Valid SyncOrdersModel ordersModel) {
//        User user = authenticationFacade.getAuthenticatedUser();
//
//        return ResponseEntity.ok().body(
//                ResponseModel.builder()
//                        .timeStamp(LocalDateTime.now().toString())
//                        .status(HttpStatus.OK)
//                        .statusCode(HttpStatus.OK.value())
//                        .message("Order/s saved successfully.")
//                        .data(Map.of("orders", orderService.syncOrders(user, ordersModel)))
//                        .build());
//    }
@PatchMapping("/{id}")
public ResponseEntity<ResponseModel> updateOrder(@PathVariable Long id, @RequestBody OrderModel orderModel) {
    return ResponseEntity.ok().body(
            ResponseModel.builder()
                    .timeStamp(LocalDateTime.now().toString())
                    .status(HttpStatus.OK)
                    .statusCode(HttpStatus.OK.value())
                    .message("This Order has been updated successfully")
                    .data(Map.of("Order", orderService.updateOrder(id,orderModel)))
                    .build());

}

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseModel> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok().body(
                ResponseModel.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("This Order has been updated successfully")
                        .build());
    }
}
