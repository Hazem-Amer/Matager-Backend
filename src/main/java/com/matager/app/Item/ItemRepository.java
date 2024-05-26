/*
 * @Abdullah Sallam
 */

package com.matager.app.Item;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long>, JpaSpecificationExecutor<Item> {

    @Query(value = "SELECT * FROM item WHERE store_id = :storeId AND id = :itemId", nativeQuery = true)
    Optional<Item> findByStoreIdAndItemId(Long storeId, Long itemId);


    @Query(value = "SELECT i FROM UserProductClicks upc " +
            "RIGHT JOIN upc.item i " +
            "WHERE upc.store.id = :storeId AND upc.user.id = :userId " +
            "ORDER BY IFNULL(upc.clickCount,1) DESC")
    List<Item> findRecommendedItems(Long storeId, Long userId);

    Page<Item> findAllByStoreId(Long storeId, Pageable pageable);

    List<Item> findAllByOwnerId(Long ownerId, Pageable pageable);

    @Query(value = "SELECT * FROM item WHERE owner_id = :ownerId AND amount < minimum_stock_level", nativeQuery = true)
    List<Item> findItemsBelowMinimumStockLevel(Long ownerId);

    @Query(value = "SELECT IFNULL(COUNT(*),0) FROM item WHERE owner_id = :ownerId AND store_id = :storeId AND amount < minimum_stock_level", nativeQuery = true)
    Double getItemsBelowMinimumStockLevel(Long ownerId, Long storeId);

    @Query(value = "SELECT icon_url FROM item WHERE id = :itemId", nativeQuery = true)
    String findItemIconUrlById(Long itemId);

    @Modifying
    @Query(value = "DELETE FROM item i WHERE i.store_id = :storeId", nativeQuery = true)
    void deleteAllByStoreId(Long storeId);

    @Query(value = "SELECT DISTINCT category FROM item WHERE owner_id = :ownerId AND category != '' AND category IS NOT NULL", nativeQuery = true)
    List<String> getItemCategories(Long ownerId);

    @Query(value = "SELECT DISTINCT category FROM item WHERE owner_id = :ownerId AND store_id = :storeId AND category != '' AND category IS NOT NULL", nativeQuery = true)
    List<String> getItemCategories(Long ownerId, Long storeId);

    @Query(value = "SELECT DISTINCT subcategory FROM item WHERE owner_id = :ownerId AND subcategory != '' AND subcategory IS NOT NULL", nativeQuery = true)
    List<String> getItemSubcategories(Long ownerId);

    @Query(value = "SELECT DISTINCT subcategory FROM item WHERE owner_id = :ownerId AND store_id = :storeId AND subcategory != '' AND subcategory IS NOT NULL", nativeQuery = true)
    List<String> getItemSubcategories(Long ownerId, Long storeId);

    @Query(value = "SELECT DISTINCT product_group FROM item WHERE owner_id = :ownerId AND product_group != '' AND subcategory IS NOT NULL", nativeQuery = true)
    List<String> getItemProductGroups(Long ownerId);

    @Query(value = "SELECT DISTINCT product_group FROM item WHERE owner_id = :ownerId AND store_id = :storeId AND product_group != '' AND subcategory IS NOT NULL", nativeQuery = true)
    List<String> getItemProductGroups(Long ownerId, Long storeId);


}
