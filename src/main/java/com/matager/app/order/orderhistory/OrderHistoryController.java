package com.matager.app.order.orderhistory;

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
@RequestMapping("/v1/store/{storeId}/order")
@RequiredArgsConstructor
public class OrderHistoryController {
    private final OrderHistoryService orderHistoryService;
    @GetMapping
    public ResponseEntity<ResponseModel> getOrderHistory(@PathVariable Long storeId,@RequestParam(name = "status") String deliveryStatus) {

        return ResponseEntity.ok().body(
                ResponseModel.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("OrderHistory successfully retrieved")
                        .data(Map.of("OrderHistory", orderHistoryService.getOrders(storeId,deliveryStatus)))
                        .build());
    }

}
