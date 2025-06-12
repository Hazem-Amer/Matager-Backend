/*
 * @Abdullah Sallam
 */

package com.matager.app.store;

import com.matager.app.auth.AuthenticationFacade;
import com.matager.app.file.FileType;
import com.matager.app.file.FileUploadService;
import com.matager.app.owner.Owner;
import com.matager.app.user.User;
import com.matager.app.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class StoreServiceImpl implements StoreService {

    private final AuthenticationFacade authenticationFacade;
    private final StoreRepository storeRepository;
    private final UserService userService;
    private final FileUploadService fileUploadService;

    @Override
    public List<Store> getStores(Long ownerId) {
        return storeRepository.findAllByOwnerId(ownerId);
    }

    @Override
    public Store getStore(String uuid) {
        return storeRepository.findByUuid(uuid).orElseThrow(() -> new IllegalArgumentException("Store not found"));
    }

    @Override
    public Store addStore(MultipartFile icon, NewStoreModel newStoreModel) {
        User signedUser = authenticationFacade.getAuthenticatedUser();
        Owner owner = signedUser.getOwner();

        Store store = new Store();
        store.setOwner(owner);
        store.setUuid(UUID.randomUUID() + "");
        store.setName(newStoreModel.getName());
        store.setBrand(newStoreModel.getBrand());
        store.setAddress(newStoreModel.getAddress());
        store.setIconUrl(fileUploadService.upload(FileType.STORE_ICON, icon));

        return storeRepository.save(store);
    }


    @Override
    public Store updateStore(String uuid, MultipartFile icon, NewStoreModel newStoreModel) {
        User signedUser = authenticationFacade.getAuthenticatedUser();
        Owner owner = signedUser.getOwner();

        Store store = storeRepository.findByOwnerIdAndUuid(owner.getId(), uuid)
                .orElseThrow(() -> new IllegalArgumentException("Store not found"));

        // Update basic information
        if (newStoreModel != null ) {
            store.setName(newStoreModel.getName());
            store.setBrand(newStoreModel.getBrand());
            store.setAddress(newStoreModel.getAddress());
        }
        // Update icon only if a new one is provided
        if (icon != null && !icon.isEmpty()) {
            store.setIconUrl(fileUploadService.upload(FileType.STORE_ICON, icon));
        }

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
