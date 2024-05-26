package com.matager.app.wishlist;

import com.matager.app.Item.Item;
import com.matager.app.Item.ItemRepository;
import com.matager.app.auth.AuthenticationFacade;
import com.matager.app.store.Store;
import com.matager.app.store.StoreRepository;
import com.matager.app.user.User;
import com.matager.app.wishlist.wishlist_item.WishListItem;
import com.matager.app.wishlist.wishlist_item.WishListItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WishListServiceImpl implements WishListService {
    private final WishListItemRepository wishListItemRepository;
    private final WishListRepository wishListRepository;
    private final ItemRepository itemRepository;
    private final AuthenticationFacade authenticationFacade;
    private final StoreRepository storeRepository;

    @Override
    public List<WishListItem> getWishListItems(Long storeId) {
        WishList wishList = getUserWIshListOrCreateNew(storeId);
        return wishListItemRepository.findAllByWishListId(wishList.getId())
                .orElseThrow(() -> new RuntimeException("WishList: " + wishList.getId() + " has no items"));

    }

    @Override
    public WishListItem addItemToWishList(Long storeId, Long itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new RuntimeException("Product not found"));
        // Find existing WishList or create a new one
        WishList wishList = getUserWIshListOrCreateNew(storeId);

        // Find existing WishList item
        Optional<WishListItem> optionalWishListItem = wishListItemRepository.findByWishListIdAndItemId(wishList.getId(), item.getId());
        WishListItem wishListItem;
        if (optionalWishListItem.isPresent()) {
            throw new RuntimeException("Product already in wishlist");
        } else {
            wishListItem = new WishListItem();
            wishListItem.setWishList(wishList);
            wishListItem.setItem(item);
            wishListItem.setName(item.getItemName());
            wishListItem.setListPrice(item.getListPrice());
        }
        return wishListItemRepository.saveAndFlush(wishListItem);
    }


    @Override
    public void removeItemFromWishList(Long storeId, Long itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new RuntimeException("Product not found"));
        WishList wishList = getUserWIshListOrCreateNew(storeId);
        WishListItem wishListItem = wishListItemRepository.findByWishListIdAndItemId(wishList.getId(), item.getId())
                .orElseThrow(() -> new RuntimeException("Product not found in the WishList"));
        wishListItemRepository.delete(wishListItem);
    }

    private WishList getUserWIshListOrCreateNew(Long storeId) {
        User user = authenticationFacade.getAuthenticatedUser();
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new RuntimeException("Store not found"));
        return wishListRepository.findByStoreIdAndUserId(store.getId(), user.getId())
                .orElseGet(() -> {
                    WishList newWishList = new WishList();
                    newWishList.setStore(store);
                    newWishList.setUser(user);
                    return wishListRepository.saveAndFlush(newWishList);
                });
    }
}
