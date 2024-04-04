package com.matager.app.owner.settings;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OwnerSettingRepository extends JpaRepository<OwnerSetting, Long> {

    boolean existsByOwnerIdAndName(Long id, String name);

    Optional<OwnerSetting> findByOwnerIdAndName(Long storeId, String name);

    @Query(value = "SELECT value FROM owner_setting WHERE owner_id = :ownerId AND name = :setting", nativeQuery = true)
    Optional<String> getValueByOwnerIdAndSetting(Long ownerId, String setting);
}
