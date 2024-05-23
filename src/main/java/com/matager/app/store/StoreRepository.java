/*
 * @Abdullah Sallam
 */

package com.matager.app.store;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    Optional<Store> findByUuid(String uuid);

    Optional<Store> findByOwnerIdAndUuid(Long ownerId, String uuid);
    Optional<Store> findByOwnerIdAndId(Long ownerId, Long id);
    boolean existsByOwnerIdAndId(Long ownerId, Long storeId);

    List<Store> findAllByOwnerId(Long ownerId);

}
