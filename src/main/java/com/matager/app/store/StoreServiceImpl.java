/*
 * @Abdullah Sallam
 */

package com.matager.app.store;

import at.orderking.bossApp.auth.AuthenticationFacade;
import at.orderking.bossApp.owner.Owner;
import at.orderking.bossApp.user.User;
import at.orderking.bossApp.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class StoreServiceImpl implements StoreService {

    private final AuthenticationFacade authenticationFacade;
    private final StoreRepository storeRepository;
    private final UserService userService;

    @Override
    public List<Store> getStores(Long ownerId) {
        return storeRepository.findAllByOwnerId(ownerId);
    }

    @Override
    public Store getStore(String uuid) {
        return storeRepository.findByUuid(uuid).orElseThrow(() -> new IllegalArgumentException("Store not found"));
    }

    @Override
    public Store addStore(NewStoreModel newStoreModel) {
        User signedUser = authenticationFacade.getAuthenticatedUser();
        Owner owner = signedUser.getOwner();

        Store store = new Store();
        store.setOwner(owner);
        store.setUuid(UUID.randomUUID() + "");
        store.setName(newStoreModel.getName());
        store.setBrand(newStoreModel.getBrand());
        store.setAddress(newStoreModel.getAddress());

        return storeRepository.save(store);
    }

    @Override
    public Store deleteStore(String uuid) {
        User signedUser = authenticationFacade.getAuthenticatedUser();
        Owner owner = signedUser.getOwner();

        Store store = storeRepository.findByOwnerIdAndUuid(owner.getId(), uuid).orElseThrow(() -> new IllegalArgumentException("Store not found"));


        storeRepository.delete(store);

        return store;
    }
}
