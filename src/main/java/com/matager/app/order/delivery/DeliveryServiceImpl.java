package com.matager.app.order.delivery;

import at.orderking.bossApp.common.helper.general.DateHelper;
import at.orderking.bossApp.common.helper.general.TimeUnit;
import at.orderking.bossApp.common.query_helper.QueryHelperService;
import at.orderking.bossApp.repository.dto.general.*;
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
    private final QueryHelperService queryHelperService;

    @Override
    public List<NameCountDto> getDriverDeliveries(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate) {
        return deliveryOrderRepository.getDriverDeliveries(fromDate, toDate, ownerId).stream()
                .map((r) -> new NameCountDto(r.getName(), r.getTotalCount())).collect(Collectors.toList());
    }

    @Override
    public List<NameCountDto> getDriverDeliveries(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Long storeId) {
        return deliveryOrderRepository.getDriverDeliveries(fromDate, toDate, ownerId, storeId).stream()
                .map((r) -> new NameCountDto(r.getName(), r.getTotalCount())).collect(Collectors.toList());
    }

    @Override
    public List<String> getDistrictDeliveriesNames(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate) {
        return deliveryOrderRepository.getDistrictDeliveriesNames(fromDate, toDate, ownerId);
    }

    @Override
    public List<NameCountDto> getDistrictDeliveries(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate) {
        return deliveryOrderRepository.getDistrictDeliveries(fromDate, toDate, ownerId).stream()
                .map((r) -> new NameCountDto(r.getName(), r.getTotalCount())).collect(Collectors.toList());
    }

    @Override
    public List<NameAmountDto> getTop10Customers(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate) {
        return deliveryCustomerRepository.getTop10Customers(fromDate, toDate, ownerId).stream()
                .map((r) -> new NameAmountDto(r.getName(), r.getTotalAmount())).collect(Collectors.toList());
    }

    @Override
    public List<NameAmountDto> getTop10Customers(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, List<Long> storeIds) {
        return deliveryCustomerRepository.getTop10Customers(fromDate, toDate, storeIds, ownerId).stream()
                .map((r) -> new NameAmountDto(r.getName(), r.getTotalAmount())).collect(Collectors.toList());
    }


    @Override
    public List<DateCountDto> getDistrictDeliveries(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, TimeUnit timeUnit) {
        return deliveryOrderRepository.getDistrictDeliveries(queryHelperService.getDayStartTime(), fromDate, toDate, ownerId, timeUnit.name()).stream()
                .map((r) -> new DateCountDto(DateHelper.getDateFromDateTime(r.getDate()), r.getCount())).collect(Collectors.toList());
    }

    @Override
    public List<DateCountDto> getDistrictDeliveries(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, TimeUnit timeUnit, String district) {
        return deliveryOrderRepository.getDistrictDeliveries(queryHelperService.getDayStartTime(), fromDate, toDate, district, ownerId, timeUnit.name()).stream()
                .map((r) -> new DateCountDto(DateHelper.getDateFromDateTime(r.getDate()), r.getCount())).collect(Collectors.toList());
    }

    @Override
    public List<DateAmountDto> getAverageDeliveryTime(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, TimeUnit timeUnit) {
        return deliveryOrderRepository.getAverageDeliveryTime(queryHelperService.getDayStartTime(), fromDate, toDate, ownerId, timeUnit.name()).stream()
                .map((r) -> new DateAmountDto(DateHelper.getDateFromDateTime(r.getDate()), r.getAmount())).collect(Collectors.toList());
    }


    @Override
    public List<DateNameAmountDto> getDistrictDeliveryRevenue(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, TimeUnit timeUnit, List<String> districts) {
        return deliveryOrderRepository.getDistrictDeliveryRevenue(queryHelperService.getDayStartTime(), fromDate, toDate, districts, ownerId, timeUnit.name()).stream()
                .map((r) -> new DateNameAmountDto(DateHelper.getDateFromDateTime(r.getDate()), r.getDistrict(), r.getAmount())).collect(Collectors.toList());
    }

    @Override
    public List<DateNameAmountDto> getDistrictDeliveryRevenue(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, TimeUnit timeUnit) {
        return deliveryOrderRepository.getDistrictDeliveryRevenue(queryHelperService.getDayStartTime(), fromDate, toDate, ownerId, timeUnit.name()).stream()
                .map((r) -> new DateNameAmountDto(DateHelper.getDateFromDateTime(r.getDate()), r.getDistrict(), r.getAmount())).collect(Collectors.toList());
    }


}
