/*
 * @Abdullah Sallam
 */

/*
 * @Abdullah Sallam
 */

package com.matager.app.order;

import at.orderking.bossApp.common.helper.general.TimeUnit;
import at.orderking.bossApp.repository.dto.general.*;
import at.orderking.bossApp.store.Store;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderViewService {

//    List<Order> getOrders(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate);

    List<DateAmountDto> getOrdersGraph(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate);

    List<NameAmountDto> getOrderStoresSales(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId);


    // Sales Totals
    Double getOrdersAmount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate);

    Double getOrdersAmount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Store store);

    Double getOrdersAmount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Long storeId);

    Integer getOrdersCount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate);

    Integer getOrdersCount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Long storeId);


    Integer getOrdersCount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Store store);

    List<DateCountAmountDto> getOrdersSales(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, TimeUnit timeUnit, Long storeId);

    // Cancellations Totals
    Double getCancellationsAmount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate);

    Double getCancellationsAmount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Long storeId);

    Double getCancellationsAmount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Store store);

    List<DateCountAmountDto> getCancellations(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, TimeUnit timeUnit);

    List<DateCountAmountDto> getCancellations(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, TimeUnit timeUnit, Long storeId);

    Integer getCancellationsCount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate);

    Integer getCancellationsCount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Long storeId);

    Integer getCancellationsCount(Long ownerId, Level level, LocalDateTime fromDate, LocalDateTime toDate);

    Integer getCancellationsCount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Store store);

    // Discounts Totals
    Double getDiscountsAmount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate);

    Double getDiscountsAmount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Long storeId);

    Integer getDiscountsCount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate);

    /**
     * added
     */
    List<NameCountDto> getDiscountStores(Long ownerId, LocalDateTime left, LocalDateTime right);

    Integer getDiscountsCount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Long storeId);

    Integer getDiscountsCount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Store store);


    // Levels Totals
    Integer getTakeAwayCount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate);

    Integer getTakeAwayCount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Store store);

    Integer getDineInCount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate);

    Integer getDineInCount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Store store);

    Integer getDeliveryCount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate);

    Integer getDeliveryCount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Long storeId);

    Integer getDeliveryCount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Store store);

    Integer getOpenDeliveryCount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate);

    Integer getOpenDeliveryCount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Long storeId);


    // Please keep the method signature as this: (Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, any other parameters last...) best regards, Abdullah Sallam

    List<NameAmountDto> getSourcesSales(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, List<String> productGroups);

    List<NameAmountDto> getSourcesSales(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate);

    List<DateAmountDto> getAverageBasketSize(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, TimeUnit timeUnit);

    List<DateNameAmountDto> getAverageBasketSize(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, TimeUnit timeUnit, List<Long> storeIds);

    List<DateAmountDto> getDeliveryAverageBasketSize(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, TimeUnit timeUnit);

    List<DateAmountDto> getDeliveryAverageBasketSize(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, TimeUnit timeUnit, String district);

    List<DateAmountDto> getStoresDailySales(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, List<Long> storeIds);

    List<DateNameAmountDto> getStoresSalesMultiLine(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, TimeUnit timeUnit, List<Long> storeIds);

    List<DateNameAmountDto> getStoresDiscounts(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, TimeUnit timeUnit, List<Long> storeIds);

    List<NameCountAmountDto> getSalesByUser(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Long storeId);

}
