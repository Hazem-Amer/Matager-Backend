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


    Item saveItem(User user, ItemModel newItem) ;


    Item saveItem(Owner owner, User user, Store store, ItemModel newItem, List<MultipartFile> imageMultipartFile);


    Item saveItem(Owner owner, User user, Store store, ItemModel newItem);

    Item updateItem(Owner owner, User user, Store store, ItemModel itemModel, List<MultipartFile> imageMultipartFile);


}
