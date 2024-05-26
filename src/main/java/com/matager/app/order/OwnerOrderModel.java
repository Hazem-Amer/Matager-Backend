package com.matager.app.order;

import com.matager.app.payment.PaymentType;
import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OwnerOrderModel {
    private String userName;
    private PaymentType paymentType;
    private DeliveryStatus deliveryStatus;
    private Boolean isPaid;
    private LocalDateTime createdAt;
    private LocalDateTime deliveredAt;
    private Double total;
}
