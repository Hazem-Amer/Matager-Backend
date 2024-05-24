package com.matager.app.Cart;

import com.matager.app.Cart.CartItem.CartItem;
import com.matager.app.Cart.CartItem.CartItemModel;

import java.util.List;

public interface CartService {
    List<CartItem> getCartItems(Long storeId);

    CartItem addItemToCart(Long storeId, Long itemId);

    void removeItemFromCart(Long storeId, Long itemId);

    void updateCartItemQuantity(Long storeId, Long itemId, int quantity);

}
