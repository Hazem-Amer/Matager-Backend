/*
 * @Abdullah Sallam
 */

package com.matager.app.payment;

import com.matager.app.common.statistics.dto.PaymentTypeSaleDto;
import com.matager.app.common.statistics.dto.general.DateNameCountAmountDto;
import java.time.LocalDateTime;
import java.util.List;

public interface PaymentService {
    List<PaymentTypeSaleDto> getSalesByPaymentType(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate); // paymentType : amount

    List<PaymentTypeSaleDto> getSalesByPaymentType(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Long storeId); // paymentType : amount

    List<Payment> getPayments(Long ownerId, Long storeId);
}
