package com.matager.app.subcategory;

import com.matager.app.Item.Item;
import com.matager.app.category.Category;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {
    @Query(value = "SELECT * FROM sub_category WHERE store_id = :storeId ", nativeQuery = true)
    List<SubCategory> findSubCategoryByStoreId(Long storeId);

    @Query(value = "SELECT id FROM sub_category WHERE store_id = :storeId AND category_id = :categoryId ", nativeQuery = true)
    List<Long> findCategorySubCategoryIds(Long storeId,Long categoryId);
    @Modifying
    @Transactional
    @Query(value = "UPDATE sub_category SET category_id = :categoryId WHERE id = :subCategoryId AND owner_id = :ownerId AND store_id = :storeId", nativeQuery = true)
    int updateCategoryId(@Param("categoryId") Long categoryId,
                         @Param("subCategoryId") Long subCategoryId,
                         @Param("ownerId") Long ownerId,
                         @Param("storeId") Long storeId);
}

