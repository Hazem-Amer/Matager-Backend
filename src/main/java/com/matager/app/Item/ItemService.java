/*
 * @Abdullah Sallam
 */

package com.matager.app.Item;

import com.matager.app.owner.Owner;
import com.matager.app.store.Store;
import com.matager.app.user.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ItemService {
    List<Item> getItems(Long ownerId);
    List<Item> getItems(Long ownerId, int pageNumber, int pageSize);

    List<Item> getSaleItems(Long ownerId, int pageNumber, int pageSize);


    List<Item> getSaleItems(Long ownerId);


    List<Item> syncItems(User user, SyncItemsModel itemsModel) throws Exception;

    Item saveItem(User user, ItemModel newItem) throws Exception;


    Item saveItem(Owner owner, User user, Store store, ItemModel newItem,List<MultipartFile> imageMultipartFile) throws Exception;


    Item saveItem(Owner owner, User user, Store store, ItemModel newItem) throws Exception;

    Item updateItem(Owner owner, User user, Store store, ItemModel itemModel);


}
