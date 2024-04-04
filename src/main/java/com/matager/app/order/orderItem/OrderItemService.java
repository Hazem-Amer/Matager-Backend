/*
 * @Abdullah Sallam
 */

package com.matager.app.order.orderItem;

import at.orderking.bossApp.common.helper.general.TimeUnit;
import at.orderking.bossApp.repository.dto.*;
import at.orderking.bossApp.repository.dto.general.*;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderItemService {

    List<NameCountDAmountDto> getTop10ItemsOrderedByCount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate);

    List<NameCountDAmountDto> getTop10ItemsOrderedByAmount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate);

    List<NameCountDAmountDto> getTop10ItemsStoresOrderedByAmount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate);

    List<NameCountDAmountDto> getTop10ItemsStoresByItemNameOrderedByAmount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, String itemName);

    List<NameCountDAmountDto> getTop10ItemsOrderedByAmount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Long storeId);

    List<NameCountDAmountDto> getLeastItemsOrderedByAmount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate);

    List<NameCountDAmountDto> getLeastItemsOrderedByAmount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Long storeId);


    List<StoreItemSaleDto> getTop10ItemsPerStoreOrderedByCount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate);

    List<StoreItemSaleDto> getTop10ItemsPerStoreOrderedByAmount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate);

    List<StoreItemSaleDto> getTop10ItemsPerStoreOrderedByAmount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Long storeId);


    List<ItemSaleDto> getItemsSales(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate);

    List<ItemSaleDto> getItemsSales(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, List<Long> ids);

    List<ItemSaleDto> getItemsSales(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Long storeId);
//    List<ItemSaleDto> getItemsSales(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Long storeId, List<Long> ids);

    List<ItemSaleDto> getItemsSales(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Long storeId, List<String> productGroups);

    List<NameAmountDto> getProductGroupsSales(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate);

    List<NameAmountDto> getProductGroupsSalesStore(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, String productGroup, List<Long> storeIds);

    List<NameAmountDto> getCategorySalesStore(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, String category, List<Long> storeIds);

    List<NameAmountDto> getSubcategorySalesStore(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, String subcategory, List<Long> storeIds);

    // Sales over time

    // All stores
    List<DateAmountDto> getProductGroupsSalesTime(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, String productGroup, TimeUnit timeUnit);

    List<DateAmountDto> getCategoriesSalesTime(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, String category, TimeUnit timeUnit);

    List<DateAmountDto> getSubcategoriesSalesTime(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, String subcategory, TimeUnit timeUnit);

    // Specific store
    List<DateCountAmountDto> getProductGroupsSalesTime(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Long storeId, String productGroup, TimeUnit timeUnit);

    List<DateCountAmountDto> getCategoriesSalesTime(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Long storeId, String category, TimeUnit timeUnit);

    List<DateCountAmountDto> getSubcategoriesSalesTime(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Long storeId, String subcategory, TimeUnit timeUnit);


    List<NameAmountDto> getProductGroupsSales(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, List<String> productGroups);

    List<NameAmountDto> getCategorySales(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, List<String> categories);

    List<NameAmountDto> getSubcategorySales(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, List<String> subcategories);


    List<NameAmountDto> getProductGroupsSales(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Long storeId);

    List<NameAmountDto> getProductGroupsSales(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Long storeId, List<String> productGroups);

    List<String> getProductGroupsNames(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate);

    List<String> getProductGroupsNames(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Long storeId);


    List<GroupItemAmountDto> getProductGroupsSalesItems(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, List<String> productGroups);

    List<GroupItemAmountDto> getCategoriesSalesItems(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, List<String> categories);

    List<GroupItemAmountDto> getSubcategoriesSalesItems(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, List<String> subcategories);


}
