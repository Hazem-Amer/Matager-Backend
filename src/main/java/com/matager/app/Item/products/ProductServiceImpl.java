package com.matager.app.Item.products;

import com.matager.app.Item.Item;
import com.matager.app.Item.ItemService;
import lombok.RequiredArgsConstructor;
import org.jvnet.hk2.annotations.Service;
import org.springframework.data.domain.Page;


@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {
    private final ItemService itemService;
    @Override
    public Page<Item> getItemsFilteredAndSorted(Long storeId, String name, Long categoryId, Long subCategoryId, Boolean isVisible, String sort, int page, int size) {
        return itemService.getItemsFilteredAndSorted(storeId, name, categoryId, subCategoryId, isVisible, sort, page, size);
    }

    @Override
    public Item getItem(Long itemId) {
        return itemService.getItem(itemId);
    }
}
