package com.matager.app.cart;

import com.matager.app.cart.cart_item.CartItem;

import java.util.List;

public interface CartService {
    List<CartItem> getCartItems(Long storeId);

    CartItem addItemToCart(Long storeId, Long itemId);

    void removeItemFromCart(Long storeId, Long itemId);

    void updateCartItemQuantity(Long storeId, Long itemId, int quantity);

}
