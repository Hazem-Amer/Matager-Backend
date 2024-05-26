package com.matager.app.order.orderhistory;

import com.matager.app.ItemImage.ItemImage;
import com.matager.app.order.DeliveryStatus;
import com.matager.app.payment.PaymentType;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderHistoryModel {
    private String userName;
    private PaymentType paymentType;
    private DeliveryStatus deliveryStatus;
    private Boolean isPaid;
    private LocalDateTime createdAt;
    private LocalDateTime deliveredAt;
    private Double total;
    private List<ItemImage> itemImages;
}
