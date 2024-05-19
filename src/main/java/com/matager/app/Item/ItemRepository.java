/*
 * @Abdullah Sallam
 */

package com.matager.app.Item;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    Page<Item> findByStoreIdAndItemNameAndCategoryIdAndSubcategoryIdAndIsVisible(
            Long storeId, String itemName, Long categoryId, Long subCategoryId, Boolean isVisible, Pageable pageable);

    Page<Item> findByStoreIdAndItemNameAndCategoryIdAndSubcategoryId(
            Long storeId, String itemName, Long categoryId, Long subCategoryId, Pageable pageable);

    Page<Item> findByStoreIdAndItemNameAndCategoryIdAndIsVisible(
            Long storeId, String itemName, Long categoryId, Boolean isVisible, Pageable pageable);

    Page<Item> findByStoreIdAndItemNameAndSubcategoryIdAndIsVisible(
            Long storeId, String itemName, Long subCategoryId, Boolean isVisible, Pageable pageable);

    Page<Item> findByStoreIdAndCategoryIdAndSubcategoryIdAndIsVisible(
            Long storeId, Long categoryId, Long subCategoryId, Boolean isVisible, Pageable pageable);

    Page<Item> findByStoreIdAndItemNameAndCategoryId(
            Long storeId, String itemName, Long categoryId, Pageable pageable);

    Page<Item> findByStoreIdAndItemNameAndSubcategoryId(
            Long storeId, String itemName, Long subCategoryId, Pageable pageable);

    Page<Item> findByStoreIdAndCategoryIdAndSubcategoryId(
            Long storeId, Long categoryId, Long subCategoryId, Pageable pageable);

    Page<Item> findByStoreIdAndItemNameAndIsVisible(
            Long storeId, String itemName, Boolean isVisible, Pageable pageable);

    Page<Item> findByStoreIdAndCategoryIdAndIsVisible(
            Long storeId, Long categoryId, Boolean isVisible, Pageable pageable);

    Page<Item> findByStoreIdAndSubcategoryIdAndIsVisible(
            Long storeId, Long subCategoryId, Boolean isVisible, Pageable pageable);

    Page<Item> findByStoreIdAndItemName(
            Long storeId, String itemName, Pageable pageable);

    Page<Item> findByStoreIdAndCategoryId(
            Long storeId, Long categoryId, Pageable pageable);

    Page<Item> findByStoreIdAndSubcategoryId(
            Long storeId, Long subCategoryId, Pageable pageable);

    Page<Item> findByStoreIdAndIsVisible(
            Long storeId, Boolean isVisible, Pageable pageable);

    Page<Item> findByStoreId(Long storeId, Pageable pageable);



    Optional<Item> findByStoreIdAndItemNo(Long storeId, Long itemNo);
    @Query(value = "SELECT * FROM item WHERE store_id = :storeId AND id = :itemId", nativeQuery = true)
    Optional<Item> findByStoreIdAndItemId(Long storeId, Long itemId);


    Page<Item> findAllByStoreId(Long storeId, Pageable pageable);

    boolean existsByStoreIdAndItemNo(Long storeId, Long itemNo);



    @Query(value = "SELECT id FROM item WHERE store_id = :storeId AND item_no = :itemNo", nativeQuery = true)
    Long getIdByStoreIdAndItemNo(Long storeId, Long itemNo);

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
