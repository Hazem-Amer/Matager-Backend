package com.matager.app.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<List<Category>> findAllCategoryByStoreId(Long storeId);
    @Query(value = "SELECT * FROM category WHERE store_id = :storeId AND  id = :categoryId", nativeQuery = true)
    Category findCategoryByStoreIdAndId(Long categoryId,Long storeId);
}
