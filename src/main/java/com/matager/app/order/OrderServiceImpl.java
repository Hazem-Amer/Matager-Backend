/*
 * @Abdullah Sallam
 */

package com.matager.app.order;

import com.matager.app.Item.Item;
import com.matager.app.Item.ItemRepository;
import com.matager.app.cart.Cart;
import com.matager.app.cart.cart_item.CartItem;
import com.matager.app.order.customer.Customer;
import com.matager.app.order.customer.CustomerRepository;
import com.matager.app.order.delivery.DeliveryCustomerRepository;
import com.matager.app.order.delivery.DeliveryOrderRepository;
import com.matager.app.order.model.OrderModel;
import com.matager.app.order.orderItem.OrderItem;
import com.matager.app.order.orderItem.OrderItemRepository;
import com.matager.app.owner.Owner;
import com.matager.app.payment.PaymentRepository;
import com.matager.app.store.Store;
import com.matager.app.store.StoreRepository;
import com.matager.app.user.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final StoreRepository storeRepository;
    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CustomerRepository customerRepository;

    @Override
    public Order updateOrder(Long orderId, OrderModel orderModel) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("order " + orderId + " not found"));
        List<OrderItem> orderItems = new ArrayList<>();
        for (Long id : orderModel.getItemsIds()) {
            OrderItem orderItem = orderItemRepository.findById(id).orElseThrow(() -> new RuntimeException("order " + orderId + " not found"));
            orderItems.add(orderItem);
        }
        order.setItems(orderItems);
        order.setPaymentType(orderModel.getPaymentType());
        order.setDeliveryMethod(orderModel.getDeliveryMethod());
        order.setDeliveryStatus(orderModel.getDeliveryStatus());
        order.setIsPaid(orderModel.getIsPaid());
        order.setCreatedAt(orderModel.getCreatedAt());
        order.setDeliveredAt(orderModel.getDeliveredAt());
        order.setTotal(orderModel.getTotal());
        return orderRepository.saveAndFlush(order);
    }

    @Override
    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }

    @Override
    public Map<String, Long> getOrdersInfo(Long storeId) {
        Map<String, Long> orderInfoCounts = new HashMap<>();
        storeRepository.findById(storeId).orElseThrow(() -> new RuntimeException("Store not found"));
        orderInfoCounts.put("CANCELLED", orderRepository.countByDeliveryStatus(storeId, DeliveryStatus.CANCELLED.name()));
        orderInfoCounts.put("DELIVERED", orderRepository.countByDeliveryStatus(storeId, DeliveryStatus.DELIVERED.name()));
        orderInfoCounts.put("SHIPPED", orderRepository.countByDeliveryStatus(storeId, DeliveryStatus.SHIPPED.name()));
        orderInfoCounts.put("PENDING", orderRepository.countByDeliveryStatus(storeId, DeliveryStatus.PENDING.name()));
        orderInfoCounts.put("PROCESSING", orderRepository.countByDeliveryStatus(storeId, DeliveryStatus.PROCESSING.name()));
        orderInfoCounts.put("TOTAL", orderRepository.countAllOrders(storeId));

        return orderInfoCounts;
    }


//    @Override
//    @Transactional
//    public List<Order> syncOrders(User user, SyncOrdersModel ordersModel) {
//        Owner owner = user.getOwner();
//
//        Store store;
//
//        if (ordersModel.getStoreUuid() != null) {
//            store = storeRepository.findByOwnerIdAndUuid(owner.getId(), ordersModel.getStoreUuid()).orElseThrow(() -> new RuntimeException("Store not found."));
//        } else {
//            if (user.getDefaultStore() == null)
//                throw new RuntimeException("No default store found, please specify store uuid.");
//            store = user.getDefaultStore();
//        }
//
//
//        List<Order> orders = new ArrayList<>();
//
//        for (OrderModel orderModel : ordersModel.getOrders()) {
//            if (orderRepository.existsByStoreIdAndInvoiceNo(store.getId(), orderModel.getInvoiceNo())) {
//                orders.add(updateOrder(owner, user, store, orderModel));
//            } else {
//                orders.add(saveOrder(owner, user, store, orderModel));
//            }
//        }
//
//
//        return orders;
//    }

//    @Override
//    @Transactional
//    public Order saveOrder(User user, OrderModel newOrder) {
//        Owner owner = user.getOwner();
//
//        Store store;
//
//        if (newOrder.getStoreUuid() != null) {
//            store = storeRepository.findByOwnerIdAndUuid(owner.getId(), newOrder.getStoreUuid()).orElseThrow(() -> new RuntimeException("Store not found."));
//        } else {
//            if (user.getDefaultStore() == null)
//                throw new RuntimeException("No default store found, please specify store uuid.");
//            store = user.getDefaultStore();
//        }


//        return saveOrder(owner, user, store, newOrder);
//    }

//    @Override
//    @Transactional
//    public Order saveOrder(Owner owner, User user, Store store, Cart cart) {
//        // User is not used here, but it's passed to the method to be used in the future if needed (e.g. to check if the user has the permission to create orders, monitor who saved the order).
//        Order order = new Order();
//        List<OrderItem> orderItems = new ArrayList<>();
//        order.setOwner(owner);
//        order.setStore(store);
//        order = orderRepository.saveAndFlush(order);
//        if (cart.getCartItems() != null) {
//            for (CartItem cartItem : cart.getCartItems()) {
//                Item item = cartItem.getItem();
//                orderItems.add(new OrderItem(owner, store, order, item, item.getItemName(), cartItem.getQuantity(), item.getListPrice(), 0d, 0d));
//            }
//            order.setDeliveryStatus(DeliveryStatus.PENDING);
//            order.setUser(user);
//            order.setItems(orderItemRepository.saveAllAndFlush(orderItems));
//        }
//
//        order = orderRepository.saveAndFlush(order);
//        return order;
//    }

//    @Override
//    public Order updateOrder(Owner owner, User user, Store store, OrderModel orderModel) {
//        return null;
//    }

//    @Transactional
//    @Override
//    public Order updateOrder(Owner owner, User user, Store store, OrderModel orderModel) {
//
//        Order order = orderRepository.findByStoreIdAndInvoiceNo(store.getId(), orderModel.getInvoiceNo()).orElseThrow(() -> new OrderException(orderModel.getInvoiceNo(), "Order not found."));
//
//
//        // Update order details
//        if (orderModel.getCreatedAt() != null) order.setCreatedAt(orderModel.getCreatedAt());
////        if (orderModel.getLevel() != null) order.setLevel(orderModel.getLevel());
//        if (orderModel.getIsCancelled() != null) order.setIsCancelled(orderModel.getIsCancelled());
//        if (orderModel.getTotal() != null) order.setTotal(orderModel.getTotal());
//
//        order = orderRepository.saveAndFlush(order);
//
//
//        // Update order items (Replace all items with the new ones)
//        if (orderModel.getItems() != null) {
//            orderItemRepository.deleteAllByOrderId(order.getId());
//            for (OrderItemModel item : orderModel.getItems()) {
//                OrderItem orderItem = new OrderItem();
//                orderItem.setOwner(order.getOwner());
//                orderItem.setStore(order.getStore());
//                orderItem.setOrder(order);
//                if (item.getItemNo() != null && item.getItemNo() != 0) {
////                    orderItem.setItem(itemRepository.findByStoreIdAndItemNo(order.getStore().getId(), item.getItemNo()).orElseThrow(() -> new OrderException(orderModel.getInvoiceNo(), "Item with number " + item.getItemNo() + " not found on the system.")));
//                    Optional<Item> optionalItem = itemRepository.findByStoreIdAndItemNo(store.getId(), item.getItemNo());
//                    if (optionalItem.isPresent()) { // Null references are allowed
//                        orderItem.setItem(optionalItem.get());
//                    }
//                }
//                orderItem.setItemNo(item.getItemNo());
//                orderItem.setItemName(item.getItemName());
//                orderItem.setPrice(item.getPrice());
//                orderItem.setListPrice(item.getListPrice());
//                orderItem.setDiscount(item.getDiscount());
//                orderItemRepository.saveAndFlush(orderItem);
//
//            }
//        }
//
//        // Update delivery order details
//        if (orderModel.getLevel() != null) {
//            if (orderModel.getLevel().equals(Level.DELIVERY)) {
//                DeliveryOrder deliveryOrder = deliveryOrderRepository.findByOrderId(order.getId()).orElse(new DeliveryOrder());
//                deliveryOrder.setOwner(order.getOwner());
//                deliveryOrder.setStore(order.getStore());
//                deliveryOrder.setOrder(order);
//                if (orderModel.getCustomerNo() != null) {
////                    deliveryOrder.setDeliveryCustomer(deliveryCustomerRepository.findByStoreIdAndCustomerNo(order.getStore().getId(), orderModel.getCustomerNo()).orElseThrow(() -> new OrderException(orderModel.getInvoiceNo(), "Delivery customer with number " + orderModel.getCustomerNo() + "not found on the system.")));
//                    Optional<DeliveryCustomer> optionalDeliveryCustomer = deliveryCustomerRepository.findByStoreIdAndCustomerNo(store.getId(), orderModel.getCustomerNo());
//                }
//                if (orderModel.getOrderNo() != null) deliveryOrder.setOrderNo(orderModel.getOrderNo());
//                if (orderModel.getSource() != null) deliveryOrder.setSource(orderModel.getSource());
//                if (orderModel.getDriverName() != null) deliveryOrder.setDriverName(orderModel.getDriverName());
//                if (orderModel.getZoneName() != null) deliveryOrder.setZoneName(orderModel.getZoneName());
//                if (orderModel.getDeliveryTime() != null) deliveryOrder.setDeliveryTime(orderModel.getDeliveryTime());
//                if (orderModel.getIsCancelled() != null) deliveryOrder.setIsCancelled(orderModel.getIsCancelled());
//                if (orderModel.getIsDelivered() != null) deliveryOrder.setIsDelivered(orderModel.getIsDelivered());
//                deliveryOrder = deliveryOrderRepository.saveAndFlush(deliveryOrder);
//            }
//        }
//
//
//        // Update payments (Replace all payments with the new ones)
//        if (orderModel.getPayments() != null) {
//            paymentRepository.deleteAllByOrderId(order.getId());
//            for (PaymentModel p : orderModel.getPayments()) {
//                Payment payment = new Payment();
//                payment.setOwner(order.getOwner());
//                payment.setStore(order.getStore());
//                payment.setOrder(order);
//                payment.setPaymentType(p.getType());
//                payment.setName(p.getName());
//                payment.setAmount(p.getAmount());
//                paymentRepository.saveAndFlush(payment);
//            }
//        }
//
//        return order;


}