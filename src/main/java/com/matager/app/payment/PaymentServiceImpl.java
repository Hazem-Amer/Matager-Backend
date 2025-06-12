/*
 * @Abdullah Sallam
 */

package com.matager.app.payment;

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
    public List<Payment> getPayments(Long ownerId, Long storeId) {
        return paymentRepository.findAllByOwnerIdAndStoreId(ownerId, storeId);
    }
}
