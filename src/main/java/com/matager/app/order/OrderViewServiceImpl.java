/*
 * @Abdullah Sallam
 */

/*
 * @Abdullah Sallam
 */

package com.matager.app.order;

import at.orderking.bossApp.common.helper.general.DateHelper;
import at.orderking.bossApp.common.helper.general.TimeUnit;
import at.orderking.bossApp.common.query_helper.QueryHelperService;
import at.orderking.bossApp.repository.dto.general.*;
import at.orderking.bossApp.repository.dto.general.DateNameAmountDto;
import at.orderking.bossApp.store.Store;
import at.orderking.bossApp.store.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderViewServiceImpl implements OrderViewService {

    private final OrderRepository orderRepository;
    private final StoreRepository storeRepository;
    private final OrderCacheService orderCacheService;
    private final QueryHelperService queryHelperService;

//    @Override
//    public List<Order> getOrders(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate) {
//        log.debug("orders: {}", orderCacheService.getOrders(ownerId, fromDate, toDate));
//
//        return orderCacheService.getOrders(ownerId, fromDate, toDate);
//    }

    @Override
    public List<DateAmountDto> getOrdersGraph(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate) {
        return orderRepository.getOrdersGraph(fromDate, toDate, ownerId).stream()
                .map((r) -> new DateAmountDto(r.getDate(), r.getAmount())).collect(Collectors.toList());
    }

    @Override
    public List<NameAmountDto> getOrderStoresSales(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId) {
        return orderRepository.getOrderStoresSales(fromDate, toDate, ownerId).stream()
                .map((r) -> new NameAmountDto(r.getName(), r.getTotalAmount())).collect(Collectors.toList());
    }

    //    @Override
//    public Double getOrdersAmount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate) {
//        List<Order> orders = getOrders(ownerId, fromDate, toDate);
//        return orders.stream().filter(order -> !order.getIsCancelled()).mapToDouble(Order::getTotal).sum();
//    }
//
    @Override
    public Double getOrdersAmount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate) {
        return orderRepository.getOrdersAmount(fromDate, toDate, ownerId);
    }

    //    @Override
//    public Double getOrdersAmount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Store store) {
//        List<Order> orders = getOrders(ownerId, fromDate, toDate);
//        return orders.stream().filter(order -> order.getStore().equals(store) && !order.getIsCancelled()).mapToDouble(Order::getTotal).sum();
//    }
//
    @Override
    public Double getOrdersAmount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Store store) {
        return orderRepository.getOrdersAmount(fromDate, toDate, ownerId, store.getId());
    }

    //    @Override
//    public Double getOrdersAmount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Long storeId) {
//        List<Order> orders = getOrders(ownerId, fromDate, toDate);
//        return orders.stream().filter(order -> order.getStore().getId().equals(storeId) && !order.getIsCancelled()).mapToDouble(Order::getTotal).sum();
//    }
//
    @Override
    public Double getOrdersAmount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Long storeId) {
        return orderRepository.getOrdersAmount(fromDate, toDate, ownerId, storeId);
    }


    //    @Override
//    public Integer getOrdersCount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate) {
//        List<Order> orders = getOrders(ownerId, fromDate, toDate);
//        return orders.size();
//    }
//
    @Override
    public Integer getOrdersCount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate) {
        return orderRepository.getOrdersCount(fromDate, toDate, ownerId);
    }

//    @Override
//    public Integer getOrdersCount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Long storeId) {
//        List<Order> orders = getOrders(ownerId, fromDate, toDate);
//        return orders.stream().filter(order -> order.getStore().getId().equals(storeId) && !order.getIsCancelled()).mapToInt(order -> 1).sum();
//    }

    @Override
    public Integer getOrdersCount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Long storeId) {
        return orderRepository.getOrdersCount(fromDate, toDate, ownerId, storeId);
    }

//    @Override
//    public Integer getOrdersCount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Store store) {
//        List<Order> orders = getOrders(ownerId, fromDate, toDate);
//        return orders.stream().filter(order -> order.getStore().equals(store) && !order.getIsCancelled()).mapToInt(order -> 1).sum();
//    }

    @Override
    public Integer getOrdersCount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Store store) {
        return orderRepository.getOrdersCount(fromDate, toDate, ownerId, store.getId());
    }

    @Override
    public List<DateCountAmountDto> getOrdersSales(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, TimeUnit timeUnit, Long storeId) {
        return orderRepository.getOrdersSales(queryHelperService.getDayStartTime(), fromDate, toDate, ownerId, storeId, timeUnit.name()).stream()
                .map((r) -> new DateCountAmountDto(DateHelper.getDateFromDateTime(r.getDate()), r.getCount(), r.getAmount()))
                .collect(Collectors.toList());
    }

//    @Override
//    public Double getCancellationsAmount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate) {
//        List<Order> orders = getOrders(ownerId, fromDate, toDate);
//        return orders.stream().filter(Order::getIsCancelled).mapToDouble(Order::getTotal).sum();
//    }

    @Override
    public Double getCancellationsAmount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate) {
        return orderRepository.getOrdersCancellationsAmount(fromDate, toDate, ownerId);
    }


//TODO DECIDE WHICH WE WILL CHOOSE (STORE ID OR STORE OBJECT)

//    @Override
//    public Double getCancellationsAmount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Long storeId) {
//        List<Order> orders = getOrders(ownerId, fromDate, toDate);
//        return orders.stream().filter(order -> order.getStore().getId().equals(storeId) && order.getIsCancelled()).mapToDouble(Order::getTotal).sum();
//    }

    @Override
    public Double getCancellationsAmount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Long storeId) {
        return orderRepository.getOrdersCancellationsAmount(fromDate, toDate, ownerId, storeId);
    }

//    @Override
//    public Double getCancellationsAmount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Store store) {
//        List<Order> orders = getOrders(ownerId, fromDate, toDate);
//        return orders.stream().filter(order -> order.getStore().equals(store) && order.getIsCancelled()).mapToDouble(Order::getTotal).sum();
//    }

    @Override
    public Double getCancellationsAmount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Store store) {
        return orderRepository.getOrdersCancellationsAmount(fromDate, toDate, ownerId, store.getId());
    }
    //TODO -----------------------------------------------

    @Override
    public List<DateCountAmountDto> getCancellations(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, TimeUnit timeUnit) {
        return orderRepository.getOrdersCancellations(queryHelperService.getDayStartTime(), fromDate, toDate, ownerId, timeUnit.name()).stream()
                .map((r) -> new DateCountAmountDto(DateHelper.getDateFromDateTime(r.getDate()), r.getCount(), r.getAmount()))
                .collect(Collectors.toList());
    }

    @Override
    public List<DateCountAmountDto> getCancellations(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, TimeUnit timeUnit, Long storeId) {
        return orderRepository.getOrdersCancellations(queryHelperService.getDayStartTime(), fromDate, toDate, ownerId, storeId, timeUnit.name()).stream()
                .map((r) -> new DateCountAmountDto(DateHelper.getDateFromDateTime(r.getDate()), r.getCount(), r.getAmount()))
                .collect(Collectors.toList());
    }

//    @Override
//    public Integer getCancellationsCount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate) {
//        List<Order> orders = getOrders(ownerId, fromDate, toDate);
//        return orders.stream().filter(Order::getIsCancelled).mapToInt(order -> 1).sum();
//    }

    @Override
    public Integer getCancellationsCount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate) {
        return orderRepository.getOrdersCancellationsCount(fromDate, toDate, ownerId);
    }

//    @Override
//    public Integer getCancellationsCount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Long storeId) {
//        List<Order> orders = getOrders(ownerId, fromDate, toDate);
//        return orders.stream().filter(order -> order.getStore().getId().equals(storeId) && order.getIsCancelled()).mapToInt(order -> 1).sum();
//    }

    @Override
    public Integer getCancellationsCount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Long storeId) {
        return orderRepository.getOrdersCancellationsCount(fromDate, toDate, ownerId, storeId);
    }

//    @Override
//    public Integer getCancellationsCount(Long ownerId, Level level, LocalDateTime fromDate, LocalDateTime toDate) {
//        List<Order> orders = getOrders(ownerId, fromDate, toDate);
//        return orders.stream().filter(order -> order.getLevel().equals(level) && order.getIsCancelled()).mapToInt(order -> 1).sum();
//    }

    @Override
    public Integer getCancellationsCount(Long ownerId, Level level, LocalDateTime fromDate, LocalDateTime toDate) {
        return orderRepository.getOrdersCancellationsCount(fromDate, toDate, ownerId, level.name());
    }

//    @Override
//    public Integer getCancellationsCount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Store store) {
//        List<Order> orders = getOrders(ownerId, fromDate, toDate);
//        return orders.stream().filter(order -> order.getStore().equals(store) && order.getIsCancelled()).mapToInt(order -> 1).sum();
//    }

    @Override
    public Integer getCancellationsCount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Store store) {
        return orderRepository.getOrdersCancellationsCount(fromDate, toDate, ownerId, store.getId());
    }

//    @Override
//    public Double getDiscountsAmount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate) {
//        List<Order> orders = getOrders(ownerId, fromDate, toDate);
//        return orders.stream().filter(order -> !order.getIsCancelled() && order.getItems().stream().anyMatch(((oi) -> oi.getDiscount() != null && oi.getDiscount() > 0))).mapToDouble(order -> order.getItems().stream().mapToDouble(oi -> (oi.getPrice() / (1 - oi.getDiscount())) - oi.getPrice()).sum()).sum();
//    }

    @Override
    public Double getDiscountsAmount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate) {
        return orderRepository.getOrdersDiscountsAmount(fromDate, toDate, ownerId);
    }

//    @Override
//    public Double getDiscountsAmount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Long storeId) {
//        List<Order> orders = getOrders(ownerId, fromDate, toDate);
//
//        return orders.stream().filter(order -> !order.getIsCancelled() && order.getStore().getId().equals(storeId) && order.getItems().stream().anyMatch(((oi) -> oi.getDiscount() != null && oi.getDiscount() > 0))).mapToDouble(order -> order.getItems().stream().mapToDouble(oi -> (oi.getPrice() / (1 - oi.getDiscount())) - oi.getPrice()).sum()).sum();
//    }

    @Override
    public Double getDiscountsAmount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Long storeId) {
        return orderRepository.getOrdersDiscountsAmount(fromDate, toDate, ownerId, storeId);
    }


//    @Override
//    public Integer getDiscountsCount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate) {
//        List<Order> orders = getOrders(ownerId, fromDate, toDate);
//        return orders.stream()
//                .filter(
//                        order -> !order.getIsCancelled() && order.getItems().stream()
//                                .anyMatch(
//                                        (
//                                                (oi) -> oi.getDiscount() != null && oi.getDiscount() > 0
//                                        )
//                                )
//                ).mapToInt(
//                        order -> order.getItems().stream().mapToInt(oi -> 1).sum()
//                ).sum();
//    }

    @Override
    public Integer getDiscountsCount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate) {
        return orderRepository.getOrdersDiscountsCount(fromDate, toDate, ownerId);
    }

    /**
     * added
     */
    @Override
    public List<NameCountDto> getDiscountStores(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate) {
        return orderRepository.getDiscountStores(fromDate, toDate, ownerId).stream()
                .map((r) -> new NameCountDto(r.getName(), r.getTotalCount()))
                .collect(Collectors.toList());
    }

//    @Override
//    public Integer getDiscountsCount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Long storeId) {
//        List<Order> orders = getOrders(ownerId, fromDate, toDate);
//        return orders.stream().filter(order -> !order.getIsCancelled() && order.getStore().getId().equals(storeId) && order.getItems().stream().anyMatch(((oi) -> oi.getDiscount() != null && oi.getDiscount() > 0))).mapToInt(order -> order.getItems().stream().mapToInt(oi -> 1).sum()).sum();
//    }

    @Override
    public Integer getDiscountsCount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Long storeId) {
        return orderRepository.getOrdersDiscountsCount(fromDate, toDate, ownerId, storeId);
    }

//    @Override
//    public Integer getDiscountsCount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Store store) {
//        List<Order> orders = getOrders(ownerId, fromDate, toDate);
//        return null;
//    }

    @Override
    public Integer getDiscountsCount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Store store) {
        return orderRepository.getOrdersDiscountsCount(fromDate, toDate, ownerId, store.getId());
    }

//    @Override
//    public Integer getTakeAwayCount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate) {
//        List<Order> orders = getOrders(ownerId, fromDate, toDate);
//        return orders.stream().filter(order -> order.getLevel().equals(Level.TAKEAWAY) && !order.getIsCancelled()).mapToInt(order -> 1).sum();
//    }

    @Override
    public Integer getTakeAwayCount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate) {
        return orderRepository.getOrdersCount(fromDate, toDate, ownerId, Level.TAKEAWAY.name());
    }

//    @Override
//    public Integer getTakeAwayCount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Store store) {
//        List<Order> orders = getOrders(ownerId, fromDate, toDate);
//        return orders.stream().filter(order -> order.getStore().equals(store) && order.getLevel().equals(Level.TAKEAWAY) && !order.getIsCancelled()).mapToInt(order -> 1).sum();
//    }

    @Override
    public Integer getTakeAwayCount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Store store) {
        return orderRepository.getOrdersCount(fromDate, toDate, ownerId, store.getId(), Level.TAKEAWAY.name());
    }

//    @Override
//    public Integer getDineInCount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate) {
//        List<Order> orders = getOrders(ownerId, fromDate, toDate);
//        return orders.stream().filter(order -> order.getLevel().equals(Level.DINE_IN) && !order.getIsCancelled()).mapToInt(order -> 1).sum();
//    }

    @Override
    public Integer getDineInCount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate) {
        return orderRepository.getOrdersCount(fromDate, toDate, ownerId, Level.DINE_IN.name());
    }

//    @Override
//    public Integer getDineInCount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Store store) {
//        List<Order> orders = getOrders(ownerId, fromDate, toDate);
//        return orders.stream().filter(order -> order.getStore().equals(store) && order.getLevel().equals(Level.DINE_IN) && !order.getIsCancelled()).mapToInt(order -> 1).sum();
//    }

    @Override
    public Integer getDineInCount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Store store) {
        return orderRepository.getOrdersCount(fromDate, toDate, ownerId, store.getId(), Level.DINE_IN.name());
    }

//    @Override
//    public Integer getDeliveryCount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate) {
//        List<Order> orders = getOrders(ownerId, fromDate, toDate);
//        return orders.stream().filter(order -> order.getLevel().equals(Level.DELIVERY) && !order.getIsCancelled()).mapToInt(order -> 1).sum();
//    }

    @Override
    public Integer getDeliveryCount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate) {
        return orderRepository.getOrdersCount(fromDate, toDate, ownerId, Level.DELIVERY.name());
    }

//    @Override
//    public Integer getDeliveryCount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Long storeId) {
//        List<Order> orders = getOrders(ownerId, fromDate, toDate);
//        return orders.stream().filter(order -> order.getStore().getId().equals(storeId) && order.getLevel().equals(Level.DELIVERY) && !order.getIsCancelled()).mapToInt(order -> 1).sum();
//    }

    @Override
    public Integer getDeliveryCount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Long storeId) {
        return orderRepository.getOrdersCount(fromDate, toDate, ownerId, storeId, Level.DELIVERY.name());
    }

//    @Override
//    public Integer getDeliveryCount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Store store) {
//        List<Order> orders = getOrders(ownerId, fromDate, toDate);
//        return orders.stream().filter(order -> order.getStore().equals(store) && order.getLevel().equals(Level.DELIVERY) && !order.getIsCancelled()).mapToInt(order -> 1).sum();
//    }

    @Override
    public Integer getDeliveryCount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Store store) {
        return orderRepository.getOrdersCount(fromDate, toDate, ownerId, store.getId(), Level.DELIVERY.name());
    }

    @Override
    public Integer getOpenDeliveryCount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate) {
        return orderRepository.getOpenDeliveryOrders(fromDate, toDate, ownerId);
    }

    @Override
    public Integer getOpenDeliveryCount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Long storeId) {
        return orderRepository.getOpenDeliveryOrders(fromDate, toDate, ownerId, storeId);
    }

    @Override
    public List<NameAmountDto> getSourcesSales(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, List<String> productGroups) {
        return orderRepository.getSourceSales(fromDate, toDate, productGroups, ownerId).stream()
                .map((r) -> new NameAmountDto(r.getName(), r.getTotalAmount()))
                .collect(Collectors.toList());
    }

    @Override
    public List<NameAmountDto> getSourcesSales(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate) {
        return orderRepository.getSourceSales(fromDate, toDate, ownerId).stream()
                .map((r) -> new NameAmountDto(r.getName(), r.getTotalAmount()))
                .collect(Collectors.toList());
    }

    @Override
    public List<DateAmountDto> getAverageBasketSize(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, TimeUnit timeUnit) {
        return orderRepository.getAverageBasketSize(queryHelperService.getDayStartTime(), fromDate, toDate, ownerId, timeUnit.name()).stream()
                .map((r) -> new DateAmountDto(DateHelper.getDateFromDateTime(r.getDate()), r.getAmount()))
                .collect(Collectors.toList());
    }

    @Override
    public List<DateNameAmountDto> getAverageBasketSize(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, TimeUnit timeUnit, List<Long> storeIds) {
        return
                orderRepository.getAverageBasketSize(queryHelperService.getDayStartTime(), fromDate, toDate, ownerId, storeIds, timeUnit.name()).stream()
                        .map((r) -> new DateNameAmountDto(DateHelper.getDateFromDateTime(r.getDate()), r.getStore(), r.getAmount()))
                        .collect(Collectors.toList());
    }

    @Override
    public List<DateAmountDto> getDeliveryAverageBasketSize(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, TimeUnit timeUnit) {
        return orderRepository.getDeliveryAverageBasketSizeByDistrict(queryHelperService.getDayStartTime(), fromDate, toDate, ownerId, timeUnit.name()).stream()
                .map((r) -> new DateAmountDto(DateHelper.getDateFromDateTime(r.getDate()), r.getAmount()))
                .collect(Collectors.toList());
    }

    @Override
    public List<DateAmountDto> getDeliveryAverageBasketSize(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, TimeUnit timeUnit, String district) {
        return orderRepository.getDeliveryAverageBasketSizeByDistrict(queryHelperService.getDayStartTime(), fromDate, toDate, ownerId, district, timeUnit.name()).stream()
                .map((r) -> new DateAmountDto(DateHelper.getDateFromDateTime(r.getDate()), r.getAmount()))
                .collect(Collectors.toList());
    }


    @Override
    public List<DateAmountDto> getStoresDailySales(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, List<Long> storeIds) {
        return orderRepository.getStoresDailySales(queryHelperService.getDayStartTime(), fromDate, toDate, storeIds, ownerId).stream()
                .map((r) -> new DateAmountDto(DateHelper.getDateFromDateTime(r.getDate()), r.getAmount()))
                .collect(Collectors.toList());
    }

    @Override
    public List<DateNameAmountDto> getStoresSalesMultiLine(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, TimeUnit timeUnit, List<Long> storeIds) {
        return orderRepository.getStoresSalesMulti(queryHelperService.getDayStartTime(), fromDate, toDate, storeIds, ownerId, timeUnit.name()).stream()
                .map((r) -> new DateNameAmountDto(DateHelper.getDateFromDateTime(r.getDate()), r.getName(), r.getTotalAmount()))
                .collect(Collectors.toList());
    }

    @Override
    public List<DateNameAmountDto> getStoresDiscounts(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, TimeUnit timeUnit, List<Long> storeIds) {
        return orderRepository.getStoresDiscounts(queryHelperService.getDayStartTime(), fromDate, toDate, storeIds, ownerId, timeUnit.name()).stream()
                .map((r) -> new DateNameAmountDto(DateHelper.getDateFromDateTime(r.getDate()), r.getStore(), r.getAmount()))
                .collect(Collectors.toList());
    }

    @Override
    public List<NameCountAmountDto> getSalesByUser(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Long storeId) {
        return orderRepository.getStoreSalesByUser(fromDate, toDate, ownerId, storeId).stream()
                .map((r) -> new NameCountAmountDto(r.getName(), r.getCount(), r.getAmount()))
                .collect(Collectors.toList());
    }
}
