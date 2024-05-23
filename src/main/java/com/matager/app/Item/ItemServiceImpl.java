/*
 * @Abdullah Sallam
 */

package com.matager.app.Item;

import com.matager.app.ItemImage.ItemImage;
import com.matager.app.ItemImage.ItemImageRepository;
import com.matager.app.category.Category;
import com.matager.app.category.CategoryRepository;
import com.matager.app.file.FileType;
import com.matager.app.file.FileUploadService;
import com.matager.app.owner.Owner;
import com.matager.app.store.Store;
import com.matager.app.store.StoreRepository;
import com.matager.app.subcategory.SubCategory;
import com.matager.app.subcategory.SubCategoryRepository;
import com.matager.app.user.User;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final ItemImageRepository itemImageRepository;
    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final StoreRepository storeRepository;
    private final FileUploadService fileUploadService;
//    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public Item getItem(Long itemId) {
        return itemRepository.findById(itemId).orElseThrow(() -> new RuntimeException("Item : " + itemId + " not found"));
    }


//    @Override
//    public Page<Item> getItemsWithFiler(Long storeId, String name, Long categoryId, Long subCategoryId, Boolean isVisible, int page, int size) {
//        Pageable pageable = PageRequest.of(page, size);
//        Page<Item> itemPage;
//        boolean isNameProvided = name != null && !name.isEmpty();
//
//        if (isNameProvided && categoryId != null && subCategoryId != null && isVisible != null) {
//            itemPage = itemRepository.findByStoreIdAndItemNameAndCategoryIdAndSubcategoryIdAndIsVisible(storeId, name, categoryId, subCategoryId, isVisible, pageable);
//        } else if (isNameProvided && categoryId != null && subCategoryId != null) {
//            itemPage = itemRepository.findByStoreIdAndItemNameAndCategoryIdAndSubcategoryId(storeId, name, categoryId, subCategoryId, pageable);
//        } else if (isNameProvided && categoryId != null && isVisible != null) {
//            itemPage = itemRepository.findByStoreIdAndItemNameAndCategoryIdAndIsVisible(storeId, name, categoryId, isVisible, pageable);
//        } else if (isNameProvided && subCategoryId != null && isVisible != null) {
//            itemPage = itemRepository.findByStoreIdAndItemNameAndSubcategoryIdAndIsVisible(storeId, name, subCategoryId, isVisible, pageable);
//        } else if (categoryId != null && subCategoryId != null && isVisible != null) {
//            itemPage = itemRepository.findByStoreIdAndCategoryIdAndSubcategoryIdAndIsVisible(storeId, categoryId, subCategoryId, isVisible, pageable);
//        } else if (isNameProvided && categoryId != null) {
//            itemPage = itemRepository.findByStoreIdAndItemNameAndCategoryId(storeId, name, categoryId, pageable);
//        } else if (isNameProvided && subCategoryId != null) {
//            itemPage = itemRepository.findByStoreIdAndItemNameAndSubcategoryId(storeId, name, subCategoryId, pageable);
//        } else if (categoryId != null && subCategoryId != null) {
//            itemPage = itemRepository.findByStoreIdAndCategoryIdAndSubcategoryId(storeId, categoryId, subCategoryId, pageable);
//        } else if (isNameProvided && isVisible != null) {
//            itemPage = itemRepository.findByStoreIdAndItemNameAndIsVisible(storeId, name, isVisible, pageable);
//        } else if (categoryId != null && isVisible != null) {
//            itemPage = itemRepository.findByStoreIdAndCategoryIdAndIsVisible(storeId, categoryId, isVisible, pageable);
//        } else if (subCategoryId != null && isVisible != null) {
//            itemPage = itemRepository.findByStoreIdAndSubcategoryIdAndIsVisible(storeId, subCategoryId, isVisible, pageable);
//        } else if (isNameProvided) {
//            itemPage = itemRepository.findByStoreIdAndItemName(storeId, name, pageable);
//        } else if (categoryId != null) {
//            itemPage = itemRepository.findByStoreIdAndCategoryId(storeId, categoryId, pageable);
//        } else if (subCategoryId != null) {
//            itemPage = itemRepository.findByStoreIdAndSubcategoryId(storeId, subCategoryId, pageable);
//        } else if (isVisible != null) {
//            itemPage = itemRepository.findByStoreIdAndIsVisible(storeId, isVisible, pageable);
//        } else {
//            itemPage = itemRepository.findByStoreId(storeId, pageable);
//        }
//
//        return itemPage;
//
//    }

    public Page<Item> getItemsFilteredAndSorted(Long storeId, String itemName, Long categoryId, Long subCategoryId, Boolean isVisible, String sort, int page, int size) {
        Pageable pageable;
        if (sort.contains(";")) {
            String[] sortParts = sort.split(";");
            String sortField = sortParts[0];
            String sortDirection = sortParts[1];
            Sort.Direction direction = sortDirection.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
            pageable = PageRequest.of(page, size, Sort.by(direction, sortField));
        } else {
            pageable = PageRequest.of(page, size, Sort.by(sort));
        }
        Specification<Item> spec = Specification.where(ItemSpecification.storeIdEquals(storeId))
                .and(ItemSpecification.itemNameContains(itemName))
                .and(ItemSpecification.categoryIdEquals(categoryId))
                .and(ItemSpecification.subCategoryIdEquals(subCategoryId))
                .and(ItemSpecification.isVisibleEquals(isVisible));

        return itemRepository.findAll(spec, pageable);
    }

    @Override
    public Item saveItem(Owner owner, User user, Store store, ItemModel newItem, List<MultipartFile> imageMultipartFiles) {
        Item item = new Item();
        Category category = categoryRepository.findById(newItem.getCategoryId()).orElseThrow(() -> new RuntimeException("Category not found"));
        item.setOwner(owner);
        if (newItem.getStoreUuid() != null) {
            store = storeRepository.findByOwnerIdAndUuid(owner.getId(), newItem.getStoreUuid()).orElseThrow(() -> new RuntimeException("Store not found"));
        } else {
            if (user.getDefaultStore() == null)
                throw new RuntimeException("No default store found, please specify store uuid");
            store = user.getDefaultStore();
        }

        List<Long> categorySubCategoryIds = subCategoryRepository.findCategorySubCategoryIds(store.getId(), category.getId());
        if (categorySubCategoryIds.contains(newItem.getSubcategoryId())) {
            SubCategory subCategory = subCategoryRepository.findById(newItem.getSubcategoryId()).orElseThrow(() -> new RuntimeException("SubCategory not found"));
            item.setSubcategory(subCategory);
        } else throw new RuntimeException("SubCategory: " + newItem.getSubcategoryId() + " is not found in this Category");
        item.setStore(store);
        item.setItemNo(newItem.getItemNo());
        item.setItemName(newItem.getName());
        item.setSale(newItem.isSale());
        item.setListPrice(newItem.getListPrice());
        item.setCategory(category);
        item.setMaximumOrderQuantity(newItem.getMaximumOrderQuantity());
        item.setMinimumOrderQuantity(newItem.getMinimumOrderQuantity());
        item.setQuantity(newItem.getQuantity());
        item.setSkuNumber(newItem.getSkuNumber());
        item.setWeight(newItem.getWeight());
        item.setDescription(newItem.getDescription());
        item.setQuantity(newItem.getQuantity());
        item.setIsVisible(newItem.getIsVisible());

        item = itemRepository.saveAndFlush(item);

        saveItemImage(newItem, imageMultipartFiles, item);

        itemRepository.saveAndFlush(item);

        return item;
    }


    @Transactional
    @Override
    public Item updateItem(Long storeId, Long itemId, ItemModel itemModel, List<MultipartFile> imageMultipartFiles) {
        Item item = itemRepository.findByStoreIdAndItemId(storeId, itemId)
                .orElseThrow(() -> new RuntimeException("Item not found."));

        if (itemModel.getName() != null) item.setItemName(itemModel.getName());

        if (itemModel.isSale() != item.isSale()) item.setSale(itemModel.isSale());

        if (itemModel.getListPrice() != null) item.setListPrice(itemModel.getListPrice());

        if (itemModel.getCostPrice() != null) item.setCostPrice(itemModel.getCostPrice());

        if (itemModel.getMaximumOrderQuantity() != null)
            item.setMaximumOrderQuantity(itemModel.getMaximumOrderQuantity());

        if (itemModel.getMinimumOrderQuantity() != null)
            item.setMinimumOrderQuantity(itemModel.getMinimumOrderQuantity());

        if (itemModel.getQuantity() != null) item.setQuantity(itemModel.getQuantity());

        if (itemModel.getSkuNumber() != null) item.setSkuNumber(itemModel.getSkuNumber());

        if (itemModel.getWeight() != null) item.setWeight(itemModel.getWeight());

        if (itemModel.getDescription() != null) item.setDescription(itemModel.getDescription());

        if (itemModel.getIsVisible() != null) item.setIsVisible(itemModel.getIsVisible());
        if (itemModel.getCategoryId() != null) {
            Category category = categoryRepository.findById(itemModel.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found: " + itemModel.getCategoryId()));
            item.setCategory(category);
        }
        if (itemModel.getSubcategoryId() != null) {
            SubCategory subCategory = subCategoryRepository.findById(itemModel.getSubcategoryId())
                    .orElseThrow(() -> new RuntimeException("SubCategory not found: " + itemModel.getSubcategoryId()));
            item.setSubcategory(subCategory);
        }
        if (imageMultipartFiles != null && !imageMultipartFiles.isEmpty()) {
            saveItemImage(itemModel, imageMultipartFiles, item);
        }

        itemRepository.saveAndFlush(item);
        return item;
    }

    private void saveItemImage(ItemModel itemModel, List<MultipartFile> imageMultipartFiles, Item item) {
        ArrayList<ItemImage> itemImages = new ArrayList<>();
        for (MultipartFile file : imageMultipartFiles) {
            String imageUrl = fileUploadService.upload(FileType.ITEM_IMAGE, file);
            ItemImage image = itemImageRepository.saveAndFlush(new ItemImage(item, imageUrl, itemModel.getMainImage().equals(file.getOriginalFilename())));
            itemImages.add(image);
        }
        item.setItemImages(itemImages);
    }

    @Transactional
    @Override
    public void deleteItem(Long itemId) {
        List<ItemImage> itemImages = itemImageRepository.findAllByItemId(itemId).
                orElseThrow(() -> new RuntimeException("Cannot get the item images for this item : " + itemId));
        itemImageRepository.deleteAll(itemImages);
        itemRepository.deleteById(itemId);

    }


}
