package com.matager.app.wishlist.wishlist_item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishListItemRepository extends JpaRepository<WishListItem,Long> {
    Optional<List<WishListItem>> findAllByWishListId(Long wishListId);
    Optional<WishListItem> findByWishListIdAndItemId(Long wishListId, Long itemId);
}
