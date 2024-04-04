/*
 * @Abdullah Sallam
 */

package com.matager.app.store;

import java.util.List;

public interface StoreService {
    List<Store> getStores(Long ownerId);

    Store getStore(String uuid);

    Store addStore(NewStoreModel newStoreModel);

    Store deleteStore(String uuid);

}
