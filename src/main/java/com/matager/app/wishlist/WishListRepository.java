package com.matager.app.wishlist;

import com.matager.app.cart.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WishListRepository extends JpaRepository<WishList,Long> {
    Optional<WishList> findByStoreIdAndUserId(Long storeId, Long userId);
}
