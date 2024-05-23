package com.matager.app.subcategory;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {
    Optional<List<SubCategory>> findAllByStoreId(Long storeId);
    Optional<List<SubCategory>> findAllByStoreIdAndCategoryId(Long storeId,Long CategoryId);

    @Query(value = "SELECT id FROM sub_category WHERE store_id = :storeId AND category_id = :categoryId ", nativeQuery = true)
    List<Long> findCategorySubCategoryIds(Long storeId,Long categoryId);
    @Modifying
    @Transactional
    @Query(value = "UPDATE sub_category SET category_id = :categoryId WHERE id = :subCategoryId AND owner_id = :ownerId AND store_id = :storeId", nativeQuery = true)
    int updateCategoryById(@Param("categoryId") Long categoryId,
                           @Param("subCategoryId") Long subCategoryId,
                           @Param("ownerId") Long ownerId,
                           @Param("storeId") Long storeId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE sub_category SET category_id = null WHERE category_id = :categoryId ", nativeQuery = true)
    int removeCategoryIdById(@Param("categoryId") Long categoryId);
}

