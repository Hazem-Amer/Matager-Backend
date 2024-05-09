/*
 * @Abdullah Sallam
 */

/*
 * @Abdullah Sallam
 */

package com.matager.app.order;

import com.matager.app.common.helper.general.DateHelper;

import com.matager.app.common.statistics.dto.general.*;
import com.matager.app.common.statistics.dto.general.DateNameAmountDto;
import com.matager.app.store.Store;
import com.matager.app.store.StoreRepository;
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

    @Override
    public Double getOrdersAmount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate) {
        return orderRepository.getOrdersAmount(fromDate, toDate, ownerId);
    }

    @Override
    public Double getOrdersAmount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Store store) {
        return orderRepository.getOrdersAmount(fromDate, toDate, ownerId, store.getId());
    }

    @Override
    public Double getOrdersAmount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Long storeId) {
        return orderRepository.getOrdersAmount(fromDate, toDate, ownerId, storeId);
    }

    @Override
    public Integer getOrdersCount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate) {
        return orderRepository.getOrdersCount(fromDate, toDate, ownerId);
    }

    @Override
    public Integer getOrdersCount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Long storeId) {
        return orderRepository.getOrdersCount(fromDate, toDate, ownerId, storeId);
    }

    @Override
    public Integer getOrdersCount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Store store) {
        return orderRepository.getOrdersCount(fromDate, toDate, ownerId, store.getId());
    }



    @Override
    public Double getCancellationsAmount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate) {
        return orderRepository.getOrdersCancellationsAmount(fromDate, toDate, ownerId);
    }


    @Override
    public Double getCancellationsAmount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Long storeId) {
        return orderRepository.getOrdersCancellationsAmount(fromDate, toDate, ownerId, storeId);
    }


    @Override
    public Double getCancellationsAmount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Store store) {
        return orderRepository.getOrdersCancellationsAmount(fromDate, toDate, ownerId, store.getId());
    }

    @Override
    public Integer getCancellationsCount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate) {
        return orderRepository.getOrdersCancellationsCount(fromDate, toDate, ownerId);
    }

    @Override
    public Integer getCancellationsCount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Long storeId) {
        return orderRepository.getOrdersCancellationsCount(fromDate, toDate, ownerId, storeId);
    }


    @Override
    public Integer getCancellationsCount(Long ownerId, Level level, LocalDateTime fromDate, LocalDateTime toDate) {
        return orderRepository.getOrdersCancellationsCount(fromDate, toDate, ownerId, level.name());
    }

    @Override
    public Integer getCancellationsCount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Store store) {
        return orderRepository.getOrdersCancellationsCount(fromDate, toDate, ownerId, store.getId());
    }
    @Override
    public Double getDiscountsAmount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate) {
        return orderRepository.getOrdersDiscountsAmount(fromDate, toDate, ownerId);
    }


    @Override
    public Double getDiscountsAmount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Long storeId) {
        return orderRepository.getOrdersDiscountsAmount(fromDate, toDate, ownerId, storeId);
    }


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


    @Override
    public Integer getDiscountsCount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Long storeId) {
        return orderRepository.getOrdersDiscountsCount(fromDate, toDate, ownerId, storeId);
    }


    @Override
    public Integer getDiscountsCount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Store store) {
        return orderRepository.getOrdersDiscountsCount(fromDate, toDate, ownerId, store.getId());
    }


    @Override
    public Integer getTakeAwayCount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate) {
        return orderRepository.getOrdersCount(fromDate, toDate, ownerId, Level.TAKEAWAY.name());
    }

    @Override
    public Integer getTakeAwayCount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Store store) {
        return orderRepository.getOrdersCount(fromDate, toDate, ownerId, store.getId(), Level.TAKEAWAY.name());
    }

    @Override
    public Integer getDineInCount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate) {
        return orderRepository.getOrdersCount(fromDate, toDate, ownerId, Level.DINE_IN.name());
    }

    @Override
    public Integer getDineInCount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Store store) {
        return orderRepository.getOrdersCount(fromDate, toDate, ownerId, store.getId(), Level.DINE_IN.name());
    }

    @Override
    public Integer getDeliveryCount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate) {
        return orderRepository.getOrdersCount(fromDate, toDate, ownerId, Level.DELIVERY.name());
    }


    @Override
    public Integer getDeliveryCount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Long storeId) {
        return orderRepository.getOrdersCount(fromDate, toDate, ownerId, storeId, Level.DELIVERY.name());
    }


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
    public List<NameCountAmountDto> getSalesByUser(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Long storeId) {
        return orderRepository.getStoreSalesByUser(fromDate, toDate, ownerId, storeId).stream()
                .map((r) -> new NameCountAmountDto(r.getName(), r.getCount(), r.getAmount()))
                .collect(Collectors.toList());
    }
}
