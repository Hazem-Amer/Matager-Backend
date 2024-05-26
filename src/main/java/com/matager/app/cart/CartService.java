package com.matager.app.cart;

import com.matager.app.cart.cart_item.CartItem;
import com.matager.app.cart.cart_item.CartItemsModel;
import com.matager.app.order.Order;

import java.util.List;

public interface CartService {
    CartItemsModel getCartItems(Long storeId);

    CartItem addItemToCart(Long storeId, Long itemId);

    void removeItemFromCart(Long storeId, Long itemId);

    void updateCartItemQuantity(Long storeId, Long itemId, Double quantity);
    Order cartCheckOut(Long storeId);

}
