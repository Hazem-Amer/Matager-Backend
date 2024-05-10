/*
 * @Abdullah Sallam
 */

package com.matager.app.owner;

import com.matager.app.owner.settings.OwnerSettingService;
import com.matager.app.store.StoreRepository;
import com.matager.app.user.User;
import com.matager.app.user.UserRepository;
import com.matager.app.user.UserRole;
import com.matager.app.user.UserService;
import com.matager.app.user.model.NewUserModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OwnerServiceImpl implements OwnerService {

    private final OwnerSettingService ownerSettingService;
    private final OwnerRepository ownerRepository;
    private final UserService userService;
    private final StoreRepository storeRepository;

    @Override
    public List<Owner> getAllOwners() {
        return ownerRepository.findAll();
    }

    @Override
    public Owner getOwnerByUuid(String uuid) {
        return ownerRepository.findByUuid(uuid).orElseThrow(() -> new IllegalArgumentException("Owner dose not exists."));
    }

    @Override
    public Owner getOwnerById(Long id) {
        return ownerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Owner dose not exists."));
    }

    @Transactional
    @Override
    public User addNewOwner(NewOwnerModel newOwner) {

        Owner owner = new Owner();
        owner.setUuid(UUID.randomUUID().toString());
        owner.setName(newOwner.getName());
        owner.setEmail(newOwner.getEmail());

        Owner savedOwner = ownerRepository.save(owner);

        ownerSettingService.initOwnerSettings(savedOwner.getId());

        return userService.addNewUser(
                NewUserModel.builder()
                        .ownerUuid(owner.getUuid()).role(UserRole.ADMIN).email(owner.getEmail())
                        .password(newOwner.getPassword()).defaultStoreUuid("").build());
    }

    // Not implemented yet, will be implemented in the future when we have a better understanding of the system
    @Override
    public void deleteOwner(Owner owner) {
        // Delete all owner related data like users, orders etc... then delete owner

    }

    @Override
    public boolean hasStore(Long ownerId, Long storeId) {
        return storeRepository.existsByOwnerIdAndId(ownerId, storeId);
    }
}
