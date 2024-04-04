/*
 * @Abdullah Sallam
 */

package com.matager.app.Item;

import at.orderking.bossApp.repository.dto.StoreItemSaleDto;
import at.orderking.bossApp.repository.dto.general.NameCountDAmountDto;

import java.time.LocalDateTime;
import java.util.List;

public interface ItemViewService {

    List<NameCountDAmountDto> getTop10Items(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate);

    List<NameCountDAmountDto> getTop10ItemsStores(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate);

    List<NameCountDAmountDto> getTop10ItemsStoresByItemName(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, String itemName);

    List<NameCountDAmountDto> getTop10Items(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Long storeId);

    List<NameCountDAmountDto> getLeastItems(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate);

    List<NameCountDAmountDto> getLeastItems(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Long storeId);

    List<StoreItemSaleDto> getTop10ItemsPerStore(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate);

    List<StoreItemSaleDto> getTop10ItemsPerStore(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Long storeId);

    List<Item> getItemsBelowMinimumStockLevel(Long ownerId);

    Double getItemsBelowMinimumStockLevel(Long ownerId, Long storeId);

    String getItemIconUrlById(Long itemId);

    List<String> getItemProductGroups(Long ownerId);

    List<String> getItemProductGroups(Long ownerId, Long storeId);

    List<String> getItemSubcategories(Long ownerId);

    List<String> getItemSubcategories(Long ownerId, Long storeId);

    List<String> getItemCategories(Long ownerId);

    List<String> getItemCategories(Long ownerId, Long storeId);

}
