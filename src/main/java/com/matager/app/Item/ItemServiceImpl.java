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
import org.springframework.data.domain.PageRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final ItemImageRepository itemImageRepository;
    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final StoreRepository storeRepository;
    private final FileUploadService fileUploadService;

    @Override
    public List<Item> getItems(Long ownerId) {
        return itemRepository.findAllByOwnerId(ownerId, PageRequest.of(0, 100));
    }


    @Override
    public List<Item> getItems(Long ownerId, int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return itemRepository.findAllByOwnerId(ownerId, pageRequest);
    }

    @Override
    public List<Item> getSaleItems(Long ownerId, int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return itemRepository.findAllByOwnerIdAndIsSale(ownerId, true, pageRequest);
    }

    @Override
    public List<Item> getSaleItems(Long ownerId) {
        return itemRepository.findAllByOwnerIdAndIsSale(ownerId, true);
    }

    @Transactional
    @Override
    public List<Item> syncItems(User user, SyncItemsModel itemsModel) throws Exception {
        Owner owner = user.getOwner();

        Store store;

        if (itemsModel.getStoreUuid() != null) {
            store = storeRepository.findByOwnerIdAndUuid(owner.getId(), itemsModel.getStoreUuid()).orElseThrow(() -> new RuntimeException("Store not found."));
        } else {
            if (user.getDefaultStore() == null)
                throw new RuntimeException("No default store found, please specify store uuid.");
            store = user.getDefaultStore();
        }

        List<Item> items = new ArrayList<>();

        for (ItemModel itemModel : itemsModel.getItems()) {
            if (itemRepository.existsByStoreIdAndItemNo(store.getId(), itemModel.getItemNo())) {
                items.add(updateItem(owner, user, store, itemModel));
            } else {
                items.add(saveItem(owner, user, store, itemModel));
            }

        }

        return items;
    }

    @Transactional
    @Override
    public Item saveItem(User user, ItemModel newItem) throws Exception {
        Owner owner = user.getOwner();

        Store store;

        if (newItem.getStoreUuid() != null) {
            store = storeRepository.findByOwnerIdAndUuid(owner.getId(), newItem.getStoreUuid()).orElseThrow(() -> new RuntimeException("Store not found."));
        } else {
            if (user.getDefaultStore() == null)
                throw new RuntimeException("No default store found, please specify store uuid.");
            store = user.getDefaultStore();
        }

        return saveItem(owner, user, store, newItem);
    }

    @Override
    public Item saveItem(Owner owner, User user, Store store, ItemModel newItem, List<MultipartFile> imageMultipartFiles) throws Exception {
        Item item = new Item();
        Optional<Category> optionalCategory = categoryRepository.findById(newItem.getCategoryId());
        Optional<SubCategory> optionalSubCategory = subCategoryRepository.findById(newItem.getSubcategoryId());
        item.setOwner(owner);
        item.setStore(store);
        item.setItemNo(newItem.getItemNo());
        item.setItemName(newItem.getName());
        item.setSale(newItem.isSale());
        item.setListPrice(newItem.getListPrice());
        if(optionalCategory.isPresent())
            item.setCategory(optionalCategory.get());
        else
            throw new NotFoundException("Category not found with ID: " + newItem.getCategoryId());
        if(optionalSubCategory.isPresent())
            item.setSubcategory(optionalSubCategory.get());
        else
             throw new NotFoundException("Sub Category not found with ID: " + newItem.getSubcategoryId());


        item.setMaximumOrderQuantity(newItem.getMaximumOrderQuantity());
        item.setMinimumOrderQuantity(newItem.getMinimumOrderQuantity());
        item.setQuantity(newItem.getQuantity());
        item.setSkuNumber(newItem.getSkuNumber());
        item.setWeight(newItem.getWeight());
        item.setDescription(newItem.getDescription());
        item.setQuantity(newItem.getQuantity());
        item.setIsVisible(newItem.getIsVisible());

        item = itemRepository.saveAndFlush(item);

        ArrayList<ItemImage> itemImages = new ArrayList<>();
        for (MultipartFile file : imageMultipartFiles) {
            String imageUrl = fileUploadService.upload(FileType.ITEM_PHOTO, file);
            System.out.println(newItem.getMainImage().equals(file.getName()));
            ItemImage image = itemImageRepository.saveAndFlush(new ItemImage(item, imageUrl, newItem.getMainImage().equals(file.getName())));
            itemImages.add(image);
        }
        item.setItemImages(itemImages);

        item = itemRepository.saveAndFlush(item);

        return item;
    }


    @Override
    public Item saveItem(Owner owner, User user, Store store, ItemModel newItem) throws Exception {
        Item item = new Item();
        Optional<Category> optionalCategory = categoryRepository.findById(newItem.getCategoryId());
        Optional<SubCategory> optionalSubCategory = subCategoryRepository.findById(newItem.getSubcategoryId());

        item.setOwner(owner);
        item.setStore(store);
        item.setItemNo(newItem.getItemNo());
        item.setItemName(newItem.getName());
        item.setSale(newItem.isSale());
        item.setListPrice(newItem.getListPrice());

        if(optionalCategory.isPresent())
            item.setCategory(optionalCategory.get());
        else
            throw new NotFoundException("Category not found with ID: " + newItem.getCategoryId());
        if(optionalSubCategory.isPresent())
            item.setSubcategory(optionalSubCategory.get());
        else
            throw new NotFoundException("SubCategory not found with ID: " + newItem.getSubcategoryId());



        item.setMaximumOrderQuantity(newItem.getMaximumOrderQuantity());
        item.setMinimumOrderQuantity(newItem.getMinimumOrderQuantity());
        item.setQuantity(newItem.getQuantity());
        item.setSkuNumber(newItem.getSkuNumber());
        item.setWeight(newItem.getWeight());
        item.setDescription(newItem.getDescription());
        item.setQuantity(newItem.getQuantity());
        item.setIsVisible(newItem.getIsVisible());

        item = itemRepository.save(item);
        itemRepository.flush();

        return item;
    }

    @Transactional
    @Override
    public Item updateItem(Owner owner, User user, Store store, ItemModel itemModel) {
        Item item = itemRepository.findByStoreIdAndItemNo(store.getId(), itemModel.getItemNo()).orElseThrow(() -> new RuntimeException("Item not found."));
        Optional<Category> optionalCategory = categoryRepository.findById(itemModel.getCategoryId());
        Optional<SubCategory> optionalSubCategory = subCategoryRepository.findById(itemModel.getSubcategoryId());
        if (itemModel.getName() != null) item.setItemName(itemModel.getName());
        if (itemModel.isSale() != item.isSale()) item.setSale(itemModel.isSale());
        if (itemModel.getListPrice() != null) item.setListPrice(itemModel.getListPrice());
        if(optionalCategory.isPresent())
             item.setCategory(optionalCategory.get());
        else
            throw new NotFoundException("Category not found with ID: " + itemModel.getCategoryId());
        if(optionalSubCategory.isPresent())
            item.setSubcategory(optionalSubCategory.get());
        else
            throw new NotFoundException("SubCategory not found with ID: " + itemModel.getCategoryId());
        if (itemModel.getCostPrice() != null)
            item.setCostPrice(itemModel.getCostPrice());

        if (itemModel.getMaximumOrderQuantity() != null)
            item.setMaximumOrderQuantity(itemModel.getMaximumOrderQuantity());

        if (itemModel.getMinimumOrderQuantity() != null)
            item.setMinimumOrderQuantity(itemModel.getMinimumOrderQuantity());

        if (itemModel.getQuantity() != null)
            item.setQuantity(itemModel.getQuantity());

        if (itemModel.getSkuNumber() != null)
            item.setSkuNumber(itemModel.getSkuNumber());

        if (itemModel.getWeight() != null)
            item.setWeight(itemModel.getWeight());

        if (itemModel.getDescription() != null)
            item.setDescription(itemModel.getDescription());

        if (itemModel.getIsVisible() != null)
            item.setIsVisible(itemModel.getIsVisible());

        item = itemRepository.save(item);
        itemRepository.flush();

        return item;
    }


}
