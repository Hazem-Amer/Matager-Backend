/*
 * @Abdullah Sallam
 */

package com.matager.app.payment;

import at.orderking.bossApp.common.helper.general.TimeUnit;
import at.orderking.bossApp.repository.dto.PaymentTypeSaleDto;
import at.orderking.bossApp.repository.dto.general.DateNameCountAmountDto;

import java.time.LocalDateTime;
import java.util.List;

public interface PaymentService {
    List<PaymentTypeSaleDto> getSalesByPaymentType(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate); // paymentType : amount

    List<PaymentTypeSaleDto> getSalesByPaymentType(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Long storeId); // paymentType : amount

    List<DateNameCountAmountDto> getSalesByPaymentType(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Long storeId, TimeUnit timeUnit);


    List<Payment> getPayments(Long ownerId, Long storeId);
}
