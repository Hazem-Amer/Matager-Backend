package com.matager.app.Item.products;

import com.matager.app.Item.Item;
import org.springframework.data.domain.Page;


public interface ProductService {
    Page<Item> getProductsFilteredAndSorted(Long storeId, String name, Long categoryId, Long subCategoryId, Boolean isVisible, String sort, int page, int size);
    Item getProduct(Long storeId,Long itemId);
}
