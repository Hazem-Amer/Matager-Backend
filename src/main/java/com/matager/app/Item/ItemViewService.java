/*
 * @Abdullah Sallam
 */

package com.matager.app.Item;

import java.time.LocalDateTime;
import java.util.List;

public interface ItemViewService {


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
