/*
 * @Abdullah Sallam
 */

package com.matager.app.Item;

import com.matager.app.order.orderItem.OrderItemService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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
