package com.matager.app.cart.cart_item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem,Long> {
    Optional<List<CartItem>> findAllByCartId(Long cartId);
    Optional<CartItem> findByCartIdAndItemId(Long cartId,Long itemId);
}
