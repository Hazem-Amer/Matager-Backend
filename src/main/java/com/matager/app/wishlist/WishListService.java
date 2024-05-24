package com.matager.app.wishlist;


import com.matager.app.wishlist.wishlist_item.WishListItem;

import java.util.List;

public interface WishListService {
    List<WishListItem> getWishListItems(Long storeId);

    WishListItem addItemToWishList(Long storeId, Long itemId);

    void removeItemFromWishList(Long storeId, Long itemId);

}
