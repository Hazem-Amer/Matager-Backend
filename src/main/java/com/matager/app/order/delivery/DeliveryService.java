package com.matager.app.order.delivery;

import at.orderking.bossApp.common.helper.general.TimeUnit;
import at.orderking.bossApp.repository.dto.general.*;

import java.time.LocalDateTime;
import java.util.List;

public interface DeliveryService {


    List<NameCountDto> getDriverDeliveries(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate);

    List<NameCountDto> getDriverDeliveries(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Long storeId);

    // Top 10 Customers
    List<NameAmountDto> getTop10Customers(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate);

    List<NameAmountDto> getTop10Customers(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, List<Long> storeIds);


    // District Deliveries
    List<String> getDistrictDeliveriesNames(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate);

    List<NameCountDto> getDistrictDeliveries(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate);

    List<DateCountDto> getDistrictDeliveries(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, TimeUnit timeUnit);

    List<DateCountDto> getDistrictDeliveries(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, TimeUnit timeUnit, String district);

    // Average Delivery Time

    List<DateAmountDto> getAverageDeliveryTime(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, TimeUnit timeUnit);

    // Delivery Revenue
    List<DateNameAmountDto> getDistrictDeliveryRevenue(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, TimeUnit timeUnit, List<String> districts);

    List<DateNameAmountDto> getDistrictDeliveryRevenue(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, TimeUnit timeUnit);

}
