/*
 * @Abdullah Sallam
 */

package com.matager.app.Item;

import com.matager.app.owner.Owner;
import com.matager.app.store.Store;
import com.matager.app.store.StoreRepository;
import com.matager.app.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final StoreRepository storeRepository;

//    @Override
//    public List<Item> getItems(Long ownerId) {
//        return itemRepository.findAllByOwnerId(ownerId, PageRequest.of(0,100));
//    }


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
    public List<Item> syncItems(User user, SyncItemsModel itemsModel) {
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
    public Item saveItem(User user, ItemModel newItem) {
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

    @Transactional
    @Override
    public Item saveItem(Owner owner, User user, Store store, ItemModel newItem) {
        Item item = new Item();

        item.setOwner(owner);
        item.setStore(store);
        item.setItemNo(newItem.getItemNo());
        item.setItemName(newItem.getName());
        item.setIconUrl(newItem.getIconUrl()); // TODO: Upload icon to server and save url
        item.setMinimumStockLevel(newItem.getMinimumStockLevel());
        item.setAmount(newItem.getAmount());
        item.setSale(newItem.isSale());
        item.setListPrice(newItem.getListPrice());
        item.setPurchasePrice(newItem.getPurchasePrice());
        item.setProductGroup(newItem.getProductGroup());
        item.setCategory(newItem.getCategory());
        item.setSubcategory(newItem.getSubcategory());
        item.setUnit(newItem.getUnit());
        item.setDefaultSupplier(newItem.getDefaultSupplier());
        item = itemRepository.save(item);
        itemRepository.flush();

        return item;
    }

    @Transactional
    @Override
    public Item updateItem(Owner owner, User user, Store store, ItemModel itemModel) {

        Item item = itemRepository.findByStoreIdAndItemNo(store.getId(), itemModel.getItemNo()).orElseThrow(() -> new RuntimeException("Item not found."));

        if (itemModel.getName() != null) item.setItemName(itemModel.getName());
        if (itemModel.getIconUrl() != null) item.setIconUrl(itemModel.getIconUrl());
        if (itemModel.getMinimumStockLevel() != null) item.setMinimumStockLevel(itemModel.getMinimumStockLevel());
        if (itemModel.getAmount() != null) item.setAmount(itemModel.getAmount());
        if (itemModel.isSale() != item.isSale()) item.setSale(itemModel.isSale());
        if (itemModel.getListPrice() != null) item.setListPrice(itemModel.getListPrice());
        if (itemModel.getPurchasePrice() != null) item.setPurchasePrice(itemModel.getPurchasePrice());
        if (itemModel.getProductGroup() != null) item.setProductGroup(itemModel.getProductGroup());
        if (itemModel.getCategory() != null) item.setCategory(itemModel.getCategory());
        if (itemModel.getSubcategory() != null) item.setSubcategory(itemModel.getSubcategory());
        if (itemModel.getUnit() != null) item.setUnit(itemModel.getUnit());
        if (itemModel.getDefaultSupplier() != null) item.setDefaultSupplier(itemModel.getDefaultSupplier());


        item = itemRepository.save(item);
        itemRepository.flush();

        return item;
    }


}
