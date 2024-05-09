/*
 * @Abdullah Sallam
 */

package com.matager.app.payment;

import com.matager.app.common.helper.general.DateHelper;

import com.matager.app.common.statistics.dto.PaymentTypeSaleDto;
import com.matager.app.common.statistics.dto.general.DateNameCountAmountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;

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
    public List<Payment> getPayments(Long ownerId, Long storeId) {
        return paymentRepository.findAllByOwnerIdAndStoreId(ownerId, storeId);
    }
}
