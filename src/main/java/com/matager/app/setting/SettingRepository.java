package com.matager.app.setting;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SettingRepository extends JpaRepository<Setting, Long> {


    boolean existsByStoreIdAndName(Long id, String name);

    Optional<Setting> findByStoreIdAndName(Long storeId, String name);

    @Query(value = "SELECT value FROM setting WHERE store_id = :storeId AND name = :name", nativeQuery = true)
    Optional<String> getValueByStoreIdAndName(Long storeId, String name);
}
