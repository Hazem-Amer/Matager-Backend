/*
 * @Abdullah Sallam
 */

package com.matager.app.owner;

import java.util.List;

public interface OwnerService {
    List<Owner> getAllOwners();

    Owner getOwnerByUuid(String uuid);

    Owner getOwnerById(Long id);

    Owner addNewOwner(NewOwnerModel newOwner);

    void deleteOwner(Owner owner);
    boolean hasStore(Long ownerId, Long storeId);
}
