package com.matager.app.order.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.matager.app.order.DeliveryMethod;
import com.matager.app.order.DeliveryStatus;
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
public class SyncOrdersModel {
    private String storeUuid;
    private String orderCountryIcon;
    private Long orderNo;
    private String customerName;
    private String customerId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd' 'HH:mm:ss[.SSS][.SS][.S]")
    private LocalDateTime createdAt;
    private String deliveredBy;
    private String branch;
    private PaymentType paymentType;
    private DeliveryStatus deliveryStatus;
    private Boolean isPaid;
    private Double total;

}
