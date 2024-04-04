/*
 * @Abdullah Sallam
 */

package com.matager.app.Item;

import at.orderking.bossApp.order.orderItem.OrderItemService;
import at.orderking.bossApp.repository.dto.StoreItemSaleDto;
import at.orderking.bossApp.repository.dto.general.NameCountDAmountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemViewServiceImpl implements ItemViewService {

    private final ItemRepository itemRepository;
    private final OrderItemService orderItemService;

    @Override
    public List<Item> getItemsBelowMinimumStockLevel(Long ownerId) {
        return itemRepository.findItemsBelowMinimumStockLevel(ownerId);
    }

    @Override
    public List<NameCountDAmountDto> getTop10Items(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate) {
        return orderItemService.getTop10ItemsOrderedByAmount(ownerId, fromDate, toDate);
    }

    /**
     * added
     */
    @Override
    public List<NameCountDAmountDto> getTop10ItemsStores(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate) {
        return orderItemService.getTop10ItemsStoresOrderedByAmount(ownerId, fromDate, toDate);
    }

    /**
     * added
     */
    @Override
    public List<NameCountDAmountDto> getTop10ItemsStoresByItemName(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, String itemName) {
        return orderItemService.getTop10ItemsStoresByItemNameOrderedByAmount(ownerId, fromDate, toDate, itemName);
    }

    @Override
    public List<NameCountDAmountDto> getTop10Items(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Long storeId) {
        return orderItemService.getTop10ItemsOrderedByAmount(ownerId, fromDate, toDate, storeId);
    }

    @Override
    public List<NameCountDAmountDto> getLeastItems(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate) {
        return orderItemService.getLeastItemsOrderedByAmount(ownerId, fromDate, toDate);
    }

    @Override
    public List<NameCountDAmountDto> getLeastItems(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Long storeId) {
        return orderItemService.getLeastItemsOrderedByAmount(ownerId, fromDate, toDate, storeId);
    }

    @Override
    public List<StoreItemSaleDto> getTop10ItemsPerStore(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate) {
        return orderItemService.getTop10ItemsPerStoreOrderedByAmount(ownerId, fromDate, toDate);
    }

    @Override
    public List<StoreItemSaleDto> getTop10ItemsPerStore(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Long storeId) {
        return orderItemService.getTop10ItemsPerStoreOrderedByAmount(ownerId, fromDate, toDate, storeId);
    }

    @Override
    public Double getItemsBelowMinimumStockLevel(Long ownerId, Long storeId) {
        return itemRepository.getItemsBelowMinimumStockLevel(ownerId, storeId);
    }

    @Override
    public String getItemIconUrlById(Long itemId) {
        return itemRepository.findItemIconUrlById(itemId);
    }

    @Override
    public List<String> getItemCategories(Long ownerId) {
        return itemRepository.getItemCategories(ownerId);
    }

    @Override
    public List<String> getItemCategories(Long ownerId, Long storeId) {
        return itemRepository.getItemCategories(ownerId, storeId);
    }

    @Override
    public List<String> getItemSubcategories(Long ownerId) {
        return itemRepository.getItemSubcategories(ownerId);
    }

    @Override
    public List<String> getItemSubcategories(Long ownerId, Long storeId) {
        return itemRepository.getItemSubcategories(ownerId, storeId);
    }

    @Override
    public List<String> getItemProductGroups(Long ownerId) {
        return itemRepository.getItemProductGroups(ownerId);
    }

    @Override
    public List<String> getItemProductGroups(Long ownerId, Long storeId) {
        return itemRepository.getItemProductGroups(ownerId, storeId);
    }
}
