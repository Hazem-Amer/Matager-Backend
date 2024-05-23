package com.matager.app.Item;


import com.matager.app.category.Category;
import com.matager.app.store.Store;
import com.matager.app.subcategory.SubCategory;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
;

public class ItemSpecification {


    public static Specification<Item> storeIdEquals(Long storeId) {
        return (root, query, builder) -> {
            if (storeId != null) {
                Join<Item, Store> storeJoin = root.join("store", JoinType.LEFT);
                return builder.equal(storeJoin.get("id"), storeId);
            }
            return null;
        };
    }

    public static Specification<Item> itemNameContains(String itemName) {
        return (root, query, builder) -> {
            if (itemName != null && !itemName.isEmpty()) {
                String searchPattern = "%" + itemName + "%";
                return builder.like(builder.lower(root.get("itemName")), searchPattern.toLowerCase());
            }
            return null;
        };
    }

    public static Specification<Item> categoryIdEquals(Long categoryId) {
        return (root, query, builder) -> {
            if (categoryId != null) {
                Join<Item, Category> categoryJoin = root.join("category", JoinType.LEFT);
                return builder.equal(categoryJoin.get("id"), categoryId);
            }
            return null;
        };
    }

    public static Specification<Item> subCategoryIdEquals(Long subCategoryId) {
        return (root, query, builder) -> {
            if (subCategoryId != null) {
                Join<Item, SubCategory> subcategoryJoin = root.join("subcategory", JoinType.LEFT);
                return builder.equal(subcategoryJoin.get("id"), subCategoryId);
            }
            return null;
        };
    }

    public static Specification<Item> isVisibleEquals(Boolean isVisible) {
        return (root, query, builder) ->
                isVisible != null ? builder.equal(root.get("isVisible"), isVisible) : null;
    }
}
