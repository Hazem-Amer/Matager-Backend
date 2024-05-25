package com.matager.app.cart;

import com.matager.app.cart.cart_item.CartItem;
import com.matager.app.cart.cart_item.CartItemRepository;
import com.matager.app.Item.Item;
import com.matager.app.Item.ItemRepository;
import com.matager.app.auth.AuthenticationFacade;
import com.matager.app.cart.cart_item.CartItemsModel;
import com.matager.app.order.DeliveryStatus;
import com.matager.app.order.Order;
import com.matager.app.order.OrderRepository;
import com.matager.app.order.OrderService;
import com.matager.app.order.customer.Customer;
import com.matager.app.order.customer.CustomerRepository;
import com.matager.app.order.model.OrderModel;
import com.matager.app.order.orderItem.OrderItem;
import com.matager.app.order.orderItem.OrderItemRepository;
import com.matager.app.owner.Owner;
import com.matager.app.owner.OwnerRepository;
import com.matager.app.payment.PaymentType;
import com.matager.app.store.Store;
import com.matager.app.store.StoreRepository;
import com.matager.app.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private final OwnerRepository ownerRepository;
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderService orderService;

    @Override
    public CartItemsModel getCartItems(Long storeId) {
        Cart cart = getUserCartOrCreateNew(storeId);
        List<CartItem> cartItems = cartItemRepository.findAllByCartId(cart.getId())
                .orElseThrow(() -> new RuntimeException("This Cart: " + cart.getId() + " has no items"));
        double subTotal = 0d;
        int cartItemsCount = 0;
        for (CartItem cartItem : cartItems) {
            subTotal += cartItem.getQuantity() * cartItem.getListPrice();
            cartItemsCount++;
        }
        return new CartItemsModel(cartItems, subTotal, cartItemsCount);

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
            cartItem.setQuantity(1d);
            cartItem.setListPrice(item.getListPrice());
        }
        return cartItemRepository.saveAndFlush(cartItem);
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
    public void updateCartItemQuantity(Long storeId, Long itemId, Double quantity) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new RuntimeException("Item not found"));
        Cart cart = getUserCartOrCreateNew(storeId);
        CartItem cartItem = cartItemRepository.findByCartIdAndItemId(cart.getId(), item.getId())
                .orElseThrow(() -> new RuntimeException("No Items in the cart"));
        if (quantity > 0) {
            cartItem.setQuantity(quantity);
            cartItemRepository.saveAndFlush(cartItem);
        } else
            cartItemRepository.delete(cartItem);
    }

    @Override
    public Order cartCheckOut(Long storeId) {
        User user = authenticationFacade.getAuthenticatedUser();
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new RuntimeException("Store not found"));
        Owner owner = store.getOwner();
        Cart cart = cartRepository.findByStoreIdAndUserId(store.getId(), user.getId())
                .orElseThrow(() -> new RuntimeException("User has no Cart"));
        cartItemRepository.findAllByCartId(cart.getId())
                .orElseThrow(() -> new RuntimeException("Cart has no Items"));
        Order order = new Order();
        List<OrderItem> orderItems = new ArrayList<>();
        order.setOwner(owner);
        order.setStore(store);
        if (cart.getCartItems() != null) {
            for (CartItem cartItem : cart.getCartItems()) {
                Item item = cartItem.getItem();
                orderItems.add(new OrderItem(owner, store, order, item, item.getItemNo(), item.getItemName(), cartItem.getQuantity(), item.getListPrice(), 0d, 0d));
            }
            //for testing only
            Customer customer = new Customer();
            customer.setName(user.getName());
            customer.setEmail(user.getEmail());
            customerRepository.saveAndFlush(customer);

            order.setDeliveryStatus(DeliveryStatus.PROCESSING);
            order.setCustomer(customer);
            order.setItems(orderItemRepository.saveAllAndFlush(orderItems));
            order.setIsPaid(false);
            order.setPaymentType(PaymentType.CASH);
        }

        order = orderRepository.saveAndFlush(order);
        return order;
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

}