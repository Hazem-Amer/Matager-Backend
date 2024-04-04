/*
 * @Abdullah Sallam
 */

package com.matager.app.user;

import at.orderking.bossApp.owner.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAllByOwnerId(Long ownerId);

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    /**
     * Added for POS sign in
     */
    Optional<User> findByName(String name);

    Optional<User> findByUuid(String uuid);

    Optional<User> findByOwnerIdAndDefaultStoreUuidAndAndRole(Long ownerId, String uuid, UserRole role);


    Long deleteByUuid(String uuid);

    void deleteAllByOwner(Owner owner);


}
