/*
 * @Abdullah Sallam
 */

package com.matager.app.Item;

import com.matager.app.owner.Owner;
import com.matager.app.store.Store;
import com.matager.app.user.User;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ItemService {
    Item getItem(Long itemId);


    Page<Item> getItemsFilteredAndSorted(Long storeId, String name, Long categoryId, Long subCategoryId, Boolean isVisible, String sort, int page, int size);
    List<Item> getRecommendedProducts(Long storeId, Long userId);

    Item saveItem(Owner owner, User user, Store store, ItemModel newItem, List<MultipartFile> imageMultipartFile);


    Item updateItem(Long storeId, Long itemId, ItemModel itemModel, List<MultipartFile> imageMultipartFile);

    void deleteItem(Long itemId);


}
