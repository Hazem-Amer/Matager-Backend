package com.matager.app.order.delivery;

import com.matager.app.common.helper.general.DateHelper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {

    private final DeliveryOrderRepository deliveryOrderRepository;
    private final DeliveryCustomerRepository deliveryCustomerRepository;


}
