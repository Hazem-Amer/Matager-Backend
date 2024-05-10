/*
 * @Abdullah Sallam
 */

package com.matager.app.owner;

import com.matager.app.user.User;

import java.util.List;

public interface OwnerService {
    List<Owner> getAllOwners();

    Owner getOwnerByUuid(String uuid);

    Owner getOwnerById(Long id);

    User addNewOwner(NewOwnerModel newOwner);

    void deleteOwner(Owner owner);
    boolean hasStore(Long ownerId, Long storeId);
}
