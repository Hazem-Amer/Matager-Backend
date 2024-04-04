/*
 * @Abdullah Sallam
 */

package com.matager.app.order.orderItem;

import at.orderking.bossApp.common.helper.general.DateHelper;
import at.orderking.bossApp.common.helper.general.TimeUnit;
import at.orderking.bossApp.common.query_helper.QueryHelperService;
import at.orderking.bossApp.repository.dto.GroupItemAmountDto;
import at.orderking.bossApp.repository.dto.ItemSaleDto;
import at.orderking.bossApp.repository.dto.StoreItemSaleDto;
import at.orderking.bossApp.repository.dto.general.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final QueryHelperService queryHelperService;

    @Override
    public List<NameCountDAmountDto> getTop10ItemsOrderedByCount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate) {
        return orderItemRepository.findTop10ItemsOrderedByCount(fromDate, toDate, ownerId).stream()
                .map(item -> new NameCountDAmountDto(item.getName(), item.getSalesCount(), item.getSalesAmount())).collect(Collectors.toList());
    }

    @Override
    public List<NameCountDAmountDto> getTop10ItemsOrderedByAmount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate) {
        return orderItemRepository.findTop10ItemsOrderedByAmount(fromDate, toDate, ownerId).stream()
                .map(item -> new NameCountDAmountDto(item.getName(), item.getSalesCount(), item.getSalesAmount())).collect(Collectors.toList());
    }

    /**
     * added
     */
    @Override
    public List<NameCountDAmountDto> getTop10ItemsStoresOrderedByAmount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate) {
        return orderItemRepository.findTop10ItemsStoresOrderedByAmount(fromDate, toDate, ownerId).stream()
                .map(item -> new NameCountDAmountDto(item.getName(), item.getSalesCount(), item.getSalesAmount())).collect(Collectors.toList());
    }

    /**
     * added
     */
    @Override
    public List<NameCountDAmountDto> getTop10ItemsStoresByItemNameOrderedByAmount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, String itemName) {
        return orderItemRepository.findItemStoresComparison(fromDate, toDate, ownerId, itemName).stream()
                .map(item -> new NameCountDAmountDto(item.getName(), item.getSalesCount(), item.getSalesAmount())).collect(Collectors.toList());
    }

    @Override
    public List<NameCountDAmountDto> getTop10ItemsOrderedByAmount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Long storeId) {
        return orderItemRepository.findTop10ItemsOrderedByAmount(fromDate, toDate, ownerId, storeId).stream()
                .map(item -> new NameCountDAmountDto(item.getName(), item.getSalesCount(), item.getSalesAmount())).collect(Collectors.toList());
    }

    @Override
    public List<NameCountDAmountDto> getLeastItemsOrderedByAmount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate) {
        return orderItemRepository.findLeastItemsOrderedByAmount(fromDate, toDate, ownerId).stream()
                .map(item -> new NameCountDAmountDto(item.getName(), item.getSalesCount(), item.getSalesAmount())).collect(Collectors.toList());
    }

    @Override
    public List<NameCountDAmountDto> getLeastItemsOrderedByAmount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Long storeId) {
        return orderItemRepository.findLeastItemsOrderedByAmount(fromDate, toDate, ownerId, storeId).stream()
                .map(item -> new NameCountDAmountDto(item.getName(), item.getSalesCount(), item.getSalesAmount())).collect(Collectors.toList());
    }

    @Override
    public List<StoreItemSaleDto> getTop10ItemsPerStoreOrderedByCount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate) {
        return orderItemRepository.findTop10ItemsPerStoreOrderedByCount(fromDate, toDate, ownerId).stream()
                .map(item -> new StoreItemSaleDto(item.getStore(), item.getId(), item.getName(), item.getSalesCount(), item.getSalesAmount())).collect(Collectors.toList());
    }

    @Override
    public List<StoreItemSaleDto> getTop10ItemsPerStoreOrderedByAmount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate) {
        return orderItemRepository.findTop10ItemsPerStoreOrderedByAmount(fromDate, toDate, ownerId).stream()
                .map(item -> new StoreItemSaleDto(item.getStore(), item.getId(), item.getName(), item.getSalesCount(), item.getSalesAmount())).collect(Collectors.toList());
    }

    @Override
    public List<StoreItemSaleDto> getTop10ItemsPerStoreOrderedByAmount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Long storeId) {
        return orderItemRepository.findTop10ItemsPerStoreOrderedByAmount(fromDate, toDate, ownerId, storeId).stream()
                .map(item -> new StoreItemSaleDto(item.getStore(), item.getId(), item.getName(), item.getSalesCount(), item.getSalesAmount())).collect(Collectors.toList());
    }

    @Override
    public List<ItemSaleDto> getItemsSales(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, List<Long> ids) {
        return orderItemRepository.findItemsSales(ownerId, fromDate, toDate, ids).stream()
                .map(item -> new ItemSaleDto(item.getId(), item.getName(), item.getAmount(), item.getCount())).collect(Collectors.toList());
    }

    @Override
    public List<ItemSaleDto> getItemsSales(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate) {
        return orderItemRepository.findTop5ItemsSales(ownerId, fromDate, toDate).stream()
                .map(item -> new ItemSaleDto(item.getId(), item.getName(), item.getAmount(), item.getCount())).collect(Collectors.toList());
    }

    @Override
    public List<ItemSaleDto> getItemsSales(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Long storeId) {
        return orderItemRepository.findTop5ItemsSales(ownerId, fromDate, toDate, storeId).stream()
                .map(item -> new ItemSaleDto(item.getId(), item.getName(), item.getAmount(), item.getCount())).collect(Collectors.toList());
    }

//    @Override
//    public List<ItemSalePro> getItemsSales(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Long storeId, List<Long> ids) {
//        return orderItemRepository.findItemsSales(ownerId, fromDate, toDate, storeId, ids);
//    }

    @Override
    public List<ItemSaleDto> getItemsSales(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Long storeId, List<String> productGroups) {
        return orderItemRepository.findTop5ItemsSales(ownerId, fromDate, toDate, storeId, productGroups).stream()
                .map(item -> new ItemSaleDto(item.getId(), item.getName(), item.getAmount(), item.getCount())).collect(Collectors.toList());
    }

    @Override
    public List<NameAmountDto> getProductGroupsSales(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate) {
        return orderItemRepository.findProductGroupsSales(fromDate, toDate, ownerId).stream()
                .map(g -> new NameAmountDto(g.getName(), g.getTotalAmount())).collect(Collectors.toList());
    }

    @Override
    public List<NameAmountDto> getProductGroupsSalesStore(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, String productGroup, List<Long> storeIds) {
        return orderItemRepository.findProductGroupsSales(fromDate, toDate, ownerId, productGroup, storeIds).stream()
                .map(g -> new NameAmountDto(g.getName(), g.getTotalAmount())).collect(Collectors.toList());
    }

    @Override
    public List<NameAmountDto> getCategorySalesStore(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, String category, List<Long> storeIds) {
        return orderItemRepository.findCategorySales(fromDate, toDate, ownerId, category, storeIds).stream()
                .map(g -> new NameAmountDto(g.getName(), g.getTotalAmount())).collect(Collectors.toList());
    }

    @Override
    public List<NameAmountDto> getSubcategorySalesStore(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, String subcategory, List<Long> storeIds) {
        return orderItemRepository.findSubcategorySales(fromDate, toDate, ownerId, subcategory, storeIds).stream()
                .map(g -> new NameAmountDto(g.getName(), g.getTotalAmount())).collect(Collectors.toList());
    }

    @Override
    public List<DateAmountDto> getProductGroupsSalesTime(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, String productGroup, TimeUnit timeUnit) {
        return orderItemRepository.findProductGroupsSalesByTimeUnit(queryHelperService.getDayStartTime(), fromDate, toDate, ownerId, productGroup, timeUnit.name()).stream()
                .map(r -> new DateAmountDto(DateHelper.getDateFromDateTime(r.getDate()), r.getAmount())).collect(Collectors.toList());
    }

    @Override
    public List<DateAmountDto> getCategoriesSalesTime(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, String category, TimeUnit timeUnit) {
        return orderItemRepository.findCategorySalesByTimeUnit(queryHelperService.getDayStartTime(), fromDate, toDate, ownerId, category, timeUnit.name()).stream()
                .map(r -> new DateAmountDto(DateHelper.getDateFromDateTime(r.getDate()), r.getAmount())).collect(Collectors.toList());
    }

    @Override
    public List<DateAmountDto> getSubcategoriesSalesTime(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, String subcategory, TimeUnit timeUnit) {
        return orderItemRepository.findSubcategorySalesByTimeUnit(queryHelperService.getDayStartTime(), fromDate, toDate, ownerId, subcategory, timeUnit.name()).stream()
                .map(r -> new DateAmountDto(DateHelper.getDateFromDateTime(r.getDate()), r.getAmount())).collect(Collectors.toList());
    }


    @Override
    public List<NameAmountDto> getProductGroupsSales(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, List<String> productGroups) {
        return orderItemRepository.findProductGroupsSales(fromDate, toDate, productGroups, ownerId).stream()
                .map(g -> new NameAmountDto(g.getName(), g.getTotalAmount())).collect(Collectors.toList());
    }

    @Override
    public List<NameAmountDto> getCategorySales(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, List<String> categories) {
        return orderItemRepository.findCategoriesSales(fromDate, toDate, categories, ownerId).stream()
                .map(g -> new NameAmountDto(g.getName(), g.getTotalAmount())).collect(Collectors.toList());
    }

    @Override
    public List<NameAmountDto> getSubcategorySales(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, List<String> subcategories) {
        return orderItemRepository.findSubcategoriesSales(fromDate, toDate, subcategories, ownerId).stream()
                .map(g -> new NameAmountDto(g.getName(), g.getTotalAmount())).collect(Collectors.toList());
    }


    @Override
    public List<NameAmountDto> getProductGroupsSales(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Long storeId) {
        return orderItemRepository.findProductGroupsSales(fromDate, toDate, ownerId, storeId).stream()
                .map(g -> new NameAmountDto(g.getName(), g.getTotalAmount())).collect(Collectors.toList());
    }

    @Override
    public List<NameAmountDto> getProductGroupsSales(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Long storeId, List<String> productGroups) {
        return orderItemRepository.findProductGroupsSales(fromDate, toDate, ownerId, storeId, productGroups).stream()
                .map(g -> new NameAmountDto(g.getName(), g.getTotalAmount())).collect(Collectors.toList());
    }

    @Override
    public List<String> getProductGroupsNames(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate) {
        return orderItemRepository.findProductGroupsNames(fromDate, toDate, ownerId);
    }

    @Override
    public List<String> getProductGroupsNames(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Long storeId) {
        return orderItemRepository.findProductGroupsNames(fromDate, toDate, ownerId, storeId);
    }

    @Override
    public List<DateCountAmountDto> getProductGroupsSalesTime(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Long storeId, String productGroup, TimeUnit timeUnit) {
        return orderItemRepository.findProductGroupsSales(queryHelperService.getDayStartTime(), fromDate, toDate, ownerId, storeId, productGroup, timeUnit.name()).stream()
                .map(r -> new DateCountAmountDto(DateHelper.getDateFromDateTime(r.getDate()), r.getCount(), r.getAmount())).collect(Collectors.toList());
    }

    @Override
    public List<DateCountAmountDto> getCategoriesSalesTime(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Long storeId, String category, TimeUnit timeUnit) {
        return orderItemRepository.findCategoriesSales(queryHelperService.getDayStartTime(), fromDate, toDate, ownerId, storeId, category, timeUnit.name()).stream()
                .map(r -> new DateCountAmountDto(DateHelper.getDateFromDateTime(r.getDate()), r.getCount(), r.getAmount())).collect(Collectors.toList());
    }

    @Override
    public List<DateCountAmountDto> getSubcategoriesSalesTime(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Long storeId, String subcategory, TimeUnit timeUnit) {
        return orderItemRepository.findSubcategoriesSales(queryHelperService.getDayStartTime(), fromDate, toDate, ownerId, storeId, subcategory, timeUnit.name()).stream()
                .map(r -> new DateCountAmountDto(DateHelper.getDateFromDateTime(r.getDate()), r.getCount(), r.getAmount())).collect(Collectors.toList());
    }

    @Override
    public List<GroupItemAmountDto> getProductGroupsSalesItems(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, List<String> productGroups) {

        return productGroups.stream()
                .map(
                        (group) -> orderItemRepository.findProductGroupsSalesItems(fromDate, toDate, ownerId, group).stream()
                                .map((gI) -> new GroupItemAmountDto(gI.getGroup(), gI.getItemName(), gI.getAmount())).collect(Collectors.toList())
                ).flatMap(Collection::stream)
                .collect(Collectors.toList());

    }

    @Override
    public List<GroupItemAmountDto> getCategoriesSalesItems(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, List<String> categories) {
        return categories.stream()
                .map(
                        (group) -> orderItemRepository.findCategoriesSalesItems(fromDate, toDate, ownerId, group).stream()
                                .map((gI) -> new GroupItemAmountDto(gI.getGroup(), gI.getItemName(), gI.getAmount())).collect(Collectors.toList())
                ).flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    @Override
    public List<GroupItemAmountDto> getSubcategoriesSalesItems(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, List<String> subcategories) {
        return subcategories.stream()
                .map(
                        (group) -> orderItemRepository.findSubcategoriesSalesItems(fromDate, toDate, ownerId, group).stream()
                                .map((gI) -> new GroupItemAmountDto(gI.getGroup(), gI.getItemName(), gI.getAmount())).collect(Collectors.toList())
                ).flatMap(Collection::stream)
                .collect(Collectors.toList());

    }


}
