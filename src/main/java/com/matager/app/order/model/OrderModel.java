/*
 * @Abdullah Sallam
 */

package com.matager.app.order.model;

import com.matager.app.order.DeliveryMethod;
import com.matager.app.order.DeliveryStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.matager.app.payment.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Validated
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class OrderModel {
    private String storeUuid;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd' 'HH:mm:ss[.SSS][.SS][.S]")
    private LocalDateTime createdAt;
    private Double total;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd' 'HH:mm:ss[.SSS][.SS][.S]")
    private LocalDateTime deliveredAt;
    private List<Long> itemsIds;
    private PaymentType paymentType;
    private DeliveryMethod deliveryMethod;
    private DeliveryStatus deliveryStatus;
    private Boolean isPaid;
    private String discountCode;
    private Long orderNo;
    private Double subTotal;
    private Double deliveryFees;
    private Double discount;
    private Double tax;
}
