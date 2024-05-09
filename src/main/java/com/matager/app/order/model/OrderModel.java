/*
 * @Abdullah Sallam
 */

package com.matager.app.order.model;

import com.matager.app.order.Level;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Validated
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class OrderModel {
    private String storeUuid;

    // Order
    @NotNull(message = "Invoice Number is required.")
    private Long invoiceNo; // RechnungNr
    private String cashierName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd' 'HH:mm:ss[.SSS][.SS][.S]")
    private LocalDateTime createdAt;
    private Level level; // Ebene
    private String subLevel; // User created levels 'if level = Ebenen' like Garden, OutDoor, Indoor and so on...
    private String tableName;
    private String orderCaptainName;
    private Boolean isCancelled;
    private Double total;

    // Delivery, just for delivery orders
    private Long orderNo; // AuftargNr, Required just for delivery orders
    private Long customerNo; // KundenNr, Required just for delivery orders
    private String source; // Tel, OnlineShop, Talabat etc...
    private String driverName;
    private String zoneName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd' 'HH:mm:ss[.SSS][.SS][.S]")
    private LocalDateTime deliveryTime;
    private Boolean isDelivered;

    // Order Items
    private List<OrderItemModel> items;

    // Payment
    private List<PaymentModel> payments;


}
