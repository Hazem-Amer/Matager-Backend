package com.matager.app.cart;

import com.matager.app.cart.cart_item.CartItem;
import com.matager.app.cart.cart_item.CartItemRepository;
import com.matager.app.Item.Item;
import com.matager.app.Item.ItemRepository;
import com.matager.app.auth.AuthenticationFacade;
import com.matager.app.store.Store;
import com.matager.app.store.StoreRepository;
import com.matager.app.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final AuthenticationFacade authenticationFacade;
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final ItemRepository itemRepository;
    private final StoreRepository storeRepository;

    @Override
    public List<CartItem> getCartItems(Long storeId) {
        Cart cart = getUserCartOrCreateNew(storeId);
        return cartItemRepository.findAllByCartId(cart.getId())
                .orElseThrow(() -> new RuntimeException("This Cart: " + cart.getId() + " has no items"));

    }

    @Override
    public CartItem addItemToCart(Long storeId, Long itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new RuntimeException("Item not found"));
        // Find existing cart or create a new one
        Cart cart = getUserCartOrCreateNew(storeId);

        // Find existing cart item and increase its quantity or create a new one
        Optional<CartItem> optionalCartItem = cartItemRepository.findByCartIdAndItemId(cart.getId(), item.getId());
        CartItem cartItem;
        if (optionalCartItem.isPresent()) {
            cartItem = optionalCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + 1);
        } else {
            cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setItem(item);
            cartItem.setName(item.getItemName());
            cartItem.setQuantity(1);
            cartItem.setListPrice(item.getListPrice());
        }
        return cartItemRepository.saveAndFlush(cartItem);
    }

    private Cart getUserCartOrCreateNew(Long storeId) {
        User user = authenticationFacade.getAuthenticatedUser();
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new RuntimeException("Store not found"));
        return cartRepository.findByStoreIdAndUserId(store.getId(), user.getId())
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setStore(store);
                    newCart.setUser(user);
                    return cartRepository.save(newCart);
                });
    }


    @Override
    public void removeItemFromCart(Long storeId, Long itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new RuntimeException("Item not found"));
        Cart cart = getUserCartOrCreateNew(storeId);
        CartItem cartItem = cartItemRepository.findByCartIdAndItemId(cart.getId(), item.getId())
                .orElseThrow(() -> new RuntimeException("Item not found in cart"));
        cartItemRepository.delete(cartItem);

    }

    @Override
    public void updateCartItemQuantity(Long storeId, Long itemId, int quantity) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new RuntimeException("Item not found"));
        Cart cart = getUserCartOrCreateNew(storeId);
        CartItem cartItem = cartItemRepository.findByCartIdAndItemId(cart.getId(), item.getId())
                .orElseThrow(() -> new RuntimeException("No Items in the cart"));
        if(quantity >0) {
            cartItem.setQuantity(quantity);
            cartItemRepository.saveAndFlush(cartItem);
        }else
            cartItemRepository.delete(cartItem);
    }


}