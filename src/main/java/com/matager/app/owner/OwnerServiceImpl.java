/*
 * @Abdullah Sallam
 */

package com.matager.app.owner;

import com.matager.app.owner.settings.OwnerSettingService;
import com.matager.app.store.StoreRepository;
import com.matager.app.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OwnerServiceImpl implements OwnerService {

    private final OwnerSettingService ownerSettingService;
    private final OwnerRepository ownerRepository;
    private final UserRepository userRepository;
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

    @Override
    public Owner addNewOwner(NewOwnerModel newOwner) {

        Owner owner = new Owner();
        owner.setShardNum(newOwner.getShardNum());
        owner.setId(newOwner.getId());
        owner.setUuid(newOwner.getOwnerUuid());
        owner.setName(newOwner.getName());
        owner.setEmail(newOwner.getEmail());

        Owner savedOwner = ownerRepository.save(owner);

        ownerSettingService.initOwnerSettings(savedOwner.getId());

        return savedOwner;
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
