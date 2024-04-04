/*
 * @Abdullah Sallam
 */

package com.matager.app.payment;

import at.orderking.bossApp.common.helper.general.DateHelper;
import at.orderking.bossApp.common.helper.general.TimeUnit;
import at.orderking.bossApp.common.query_helper.QueryHelperService;
import at.orderking.bossApp.repository.dto.PaymentTypeSaleDto;
import at.orderking.bossApp.repository.dto.general.DateNameCountAmountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final QueryHelperService queryHelperService;

    @Override
    public List<PaymentTypeSaleDto> getSalesByPaymentType(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate) {
        return paymentRepository.getSalesByPaymentType(fromDate, toDate, ownerId).stream()
                .map(p -> new PaymentTypeSaleDto(p.getName(), p.getPaymentType(), p.getTotalAmount())).collect(Collectors.toList());
    }

    @Override
    public List<PaymentTypeSaleDto> getSalesByPaymentType(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Long storeId) {
        return paymentRepository.getSalesByPaymentType(fromDate, toDate, ownerId, storeId).stream()
                .map(p -> new PaymentTypeSaleDto(p.getName(), p.getPaymentType(), p.getTotalAmount())).collect(Collectors.toList());
    }

    @Override
    public List<DateNameCountAmountDto> getSalesByPaymentType(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Long storeId, TimeUnit timeUnit) {
        return paymentRepository.getSalesByPaymentType(queryHelperService.getDayStartTime(), fromDate, toDate, ownerId, storeId, timeUnit.name()).stream()
                .map(p -> new DateNameCountAmountDto(DateHelper.getDateFromDateTime(p.getDate()), p.getPayment(), p.getCount(), p.getAmount())).collect(Collectors.toList());
    }

    @Override
    public List<Payment> getPayments(Long ownerId, Long storeId) {
        return paymentRepository.findAllByOwnerIdAndStoreId(ownerId, storeId);
    }
}
