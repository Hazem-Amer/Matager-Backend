/*
 * @Abdullah Sallam
 */

package com.matager.app.store;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StoreService {
    List<Store> getStores(Long ownerId);

    Store getStore(String uuid);

    Store addStore(MultipartFile icon, NewStoreModel newStoreModel);
    Store updateStore(String uuid,MultipartFile icon, NewStoreModel newStoreModel);

    Store deleteStore(String uuid);

}
