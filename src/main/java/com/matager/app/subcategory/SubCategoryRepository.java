package com.matager.app.subcategory;

import com.matager.app.Item.Item;
import com.matager.app.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {
    @Query(value = "SELECT * FROM sub_category WHERE store_id = :storeId ", nativeQuery = true)
    List<SubCategory> findSubCategoryByStoreId(Long storeId);


}
