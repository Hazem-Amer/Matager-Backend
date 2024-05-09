/*
 * @Abdullah Sallam
 */

package com.matager.app.order;

import com.matager.app.Item.Item;
import com.matager.app.Item.ItemRepository;
import com.matager.app.order.delivery.DeliveryCustomer;
import com.matager.app.order.delivery.DeliveryCustomerRepository;
import com.matager.app.order.delivery.DeliveryOrder;
import com.matager.app.order.delivery.DeliveryOrderRepository;
import com.matager.app.order.model.OrderItemModel;
import com.matager.app.order.model.OrderModel;
import com.matager.app.order.model.PaymentModel;
import com.matager.app.order.model.SyncOrdersModel;
import com.matager.app.order.orderItem.OrderItem;
import com.matager.app.order.orderItem.OrderItemRepository;
import com.matager.app.owner.Owner;
import com.matager.app.payment.Payment;
import com.matager.app.payment.PaymentRepository;
import com.matager.app.store.Store;
import com.matager.app.store.StoreRepository;
import com.matager.app.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final StoreRepository storeRepository;
    private final ItemRepository itemRepository;
    private final DeliveryCustomerRepository deliveryCustomerRepository;
    private final OrderRepository orderRepository;
    private final DeliveryOrderRepository deliveryOrderRepository;
    private final OrderItemRepository orderItemRepository;
    private final PaymentRepository paymentRepository;

    @Override
    @Transactional
    public List<Order> syncOrders(User user, SyncOrdersModel ordersModel) {
        Owner owner = user.getOwner();

        Store store;

        if (ordersModel.getStoreUuid() != null) {
            store = storeRepository.findByOwnerIdAndUuid(owner.getId(), ordersModel.getStoreUuid()).orElseThrow(() -> new RuntimeException("Store not found."));
        } else {
            if (user.getDefaultStore() == null)
                throw new RuntimeException("No default store found, please specify store uuid.");
            store = user.getDefaultStore();
        }


        List<Order> orders = new ArrayList<>();

        for (OrderModel orderModel : ordersModel.getOrders()) {
            if (orderRepository.existsByStoreIdAndInvoiceNo(store.getId(), orderModel.getInvoiceNo())) {
                orders.add(updateOrder(owner, user, store, orderModel));
            } else {
                orders.add(saveOrder(owner, user, store, orderModel));
            }
        }


        return orders;
    }

    @Override
    @Transactional
    public Order saveOrder(User user, OrderModel newOrder) {
        Owner owner = user.getOwner();

        Store store;

        if (newOrder.getStoreUuid() != null) {
            store = storeRepository.findByOwnerIdAndUuid(owner.getId(), newOrder.getStoreUuid()).orElseThrow(() -> new RuntimeException("Store not found."));
        } else {
            if (user.getDefaultStore() == null)
                throw new RuntimeException("No default store found, please specify store uuid.");
            store = user.getDefaultStore();
        }


        return saveOrder(owner, user, store, newOrder);
    }

    @Override
    @Transactional
    public Order saveOrder(Owner owner, User user, Store store, OrderModel newOrder) {
        // User is not used here, but it's passed to the method to be used in the future if needed (e.g. to check if the user has the permission to create orders, monitor who saved the order).

        Order order = new Order();
        order.setOwner(owner);
        order.setCreatedAt(newOrder.getCreatedAt());
        order.setStore(store);
        order.setInvoiceNo(newOrder.getInvoiceNo());
        order.setCashierName(newOrder.getCashierName());
        order.setLevel(newOrder.getLevel());
        order.setSubLevel(newOrder.getSubLevel());
        order.setTableName(newOrder.getTableName());
        order.setOrderCaptainName(newOrder.getOrderCaptainName());
        order.setIsCancelled(newOrder.getIsCancelled());
        order.setTotal(newOrder.getTotal());

        order = orderRepository.saveAndFlush(order);

        if (newOrder.getItems() != null)
            for (OrderItemModel itemModel : newOrder.getItems()) {
                OrderItem orderItem = new OrderItem();
                orderItem.setOwner(owner);
                orderItem.setStore(store);
                orderItem.setOrder(order);
                if (itemModel.getItemNo() != null && itemModel.getItemNo() != 0) {
                    //                orderItem.setItem(itemRepository.findByStoreIdAndItemNo(store.getId(), item.getItemNo()).orElseThrow(() -> new OrderException(newOrder.getInvoiceNo(), "Item with number " + item.getItemNo() + " not found on the system.")));
                    Optional<Item> optionalItem = itemRepository.findByStoreIdAndItemNo(store.getId(), itemModel.getItemNo());
                    if (optionalItem.isPresent()) { // Null references are allowed
                        orderItem.setItem(optionalItem.get());
                    }
                }
                orderItem.setItemNo(itemModel.getItemNo());
                orderItem.setItemName(itemModel.getItemName());
                orderItem.setCount(itemModel.getCount());
                orderItem.setPrice(itemModel.getPrice());
                orderItem.setListPrice(itemModel.getListPrice());
                orderItem.setDiscount(itemModel.getDiscount());
                orderItem.setVat(itemModel.getVat());
                orderItem.setProductGroup(itemModel.getProductGroup());
                orderItem.setZeroPriceReason(itemModel.getZeroPriceReason());
                orderItemRepository.saveAndFlush(orderItem);
            }


        if (order.getLevel().equals(Level.DELIVERY)) {
            DeliveryOrder deliveryOrder = new DeliveryOrder();
            deliveryOrder.setOwner(owner);
            deliveryOrder.setStore(store);
            deliveryOrder.setOrder(order);
//            deliveryOrder.setDeliveryCustomer(deliveryCustomerRepository.findByStoreIdAndCustomerNo(store.getId(), newOrder.getCustomerNo()).orElseThrow(() -> new OrderException(newOrder.getInvoiceNo(), "Delivery customer with number " + newOrder.getCustomerNo() + " not found on the system.")));
            Optional<DeliveryCustomer> optionalDeliveryCustomer = deliveryCustomerRepository.findByStoreIdAndCustomerNo(store.getId(), newOrder.getCustomerNo());
            if (optionalDeliveryCustomer.isPresent())
                deliveryOrder.setDeliveryCustomer(optionalDeliveryCustomer.get()); // Null references are allowed
            deliveryOrder.setInvoiceNo(newOrder.getInvoiceNo());
            deliveryOrder.setOrderNo(newOrder.getOrderNo());
            deliveryOrder.setSource(newOrder.getSource());
            deliveryOrder.setDriverName(newOrder.getDriverName());
            deliveryOrder.setZoneName(newOrder.getZoneName());
            deliveryOrder.setDeliveryTime(newOrder.getDeliveryTime());
            deliveryOrder.setIsCancelled(newOrder.getIsCancelled());
            deliveryOrder.setIsDelivered(newOrder.getIsDelivered());
            deliveryOrder = deliveryOrderRepository.saveAndFlush(deliveryOrder);
        }

        if (newOrder.getPayments() != null)
            for (PaymentModel p : newOrder.getPayments()) {
                Payment payment = new Payment();
                payment.setOwner(owner);
                payment.setStore(store);
                payment.setOrder(order);
                payment.setPaymentType(p.getType());
                payment.setName(p.getName());
                payment.setAmount(p.getAmount());
                paymentRepository.saveAndFlush(payment);
            }


        return order;
    }

    @Transactional
    @Override
    public Order updateOrder(Owner owner, User user, Store store, OrderModel orderModel) {

        Order order = orderRepository.findByStoreIdAndInvoiceNo(store.getId(), orderModel.getInvoiceNo()).orElseThrow(() -> new OrderException(orderModel.getInvoiceNo(), "Order not found."));


        // Update order details
        if (orderModel.getCreatedAt() != null) order.setCreatedAt(orderModel.getCreatedAt());
        if (orderModel.getCashierName() != null) order.setCashierName(orderModel.getCashierName());
        if (orderModel.getLevel() != null) order.setLevel(orderModel.getLevel());
        if (orderModel.getSubLevel() != null) order.setSubLevel(orderModel.getSubLevel());
        if (orderModel.getTableName() != null) order.setTableName(orderModel.getTableName());
        if (orderModel.getOrderCaptainName() != null) order.setOrderCaptainName(orderModel.getOrderCaptainName());
        if (orderModel.getIsCancelled() != null) order.setIsCancelled(orderModel.getIsCancelled());
        if (orderModel.getTotal() != null) order.setTotal(orderModel.getTotal());

        order = orderRepository.saveAndFlush(order);


        // Update order items (Replace all items with the new ones)
        if (orderModel.getItems() != null) {
            orderItemRepository.deleteAllByOrderId(order.getId());
            for (OrderItemModel item : orderModel.getItems()) {
                OrderItem orderItem = new OrderItem();
                orderItem.setOwner(order.getOwner());
                orderItem.setStore(order.getStore());
                orderItem.setOrder(order);
                if (item.getItemNo() != null && item.getItemNo() != 0) {
//                    orderItem.setItem(itemRepository.findByStoreIdAndItemNo(order.getStore().getId(), item.getItemNo()).orElseThrow(() -> new OrderException(orderModel.getInvoiceNo(), "Item with number " + item.getItemNo() + " not found on the system.")));
                    Optional<Item> optionalItem = itemRepository.findByStoreIdAndItemNo(store.getId(), item.getItemNo());
                    if (optionalItem.isPresent()) { // Null references are allowed
                        orderItem.setItem(optionalItem.get());
                    }
                }
                orderItem.setItemNo(item.getItemNo());
                orderItem.setItemName(item.getItemName());
                orderItem.setCount(item.getCount());
                orderItem.setPrice(item.getPrice());
                orderItem.setListPrice(item.getListPrice());
                orderItem.setDiscount(item.getDiscount());
                orderItem.setVat(item.getVat());
                orderItem.setProductGroup(item.getProductGroup());
                orderItem.setZeroPriceReason(item.getZeroPriceReason());
                orderItemRepository.saveAndFlush(orderItem);

            }
        }

        // Update delivery order details
        if (orderModel.getLevel() != null) {
            if (orderModel.getLevel().equals(Level.DELIVERY)) {
                DeliveryOrder deliveryOrder = deliveryOrderRepository.findByOrderId(order.getId()).orElse(new DeliveryOrder());
                deliveryOrder.setOwner(order.getOwner());
                deliveryOrder.setStore(order.getStore());
                deliveryOrder.setOrder(order);
                if (orderModel.getCustomerNo() != null) {
//                    deliveryOrder.setDeliveryCustomer(deliveryCustomerRepository.findByStoreIdAndCustomerNo(order.getStore().getId(), orderModel.getCustomerNo()).orElseThrow(() -> new OrderException(orderModel.getInvoiceNo(), "Delivery customer with number " + orderModel.getCustomerNo() + "not found on the system.")));
                    Optional<DeliveryCustomer> optionalDeliveryCustomer = deliveryCustomerRepository.findByStoreIdAndCustomerNo(store.getId(), orderModel.getCustomerNo());
                    if (optionalDeliveryCustomer.isPresent())
                        deliveryOrder.setDeliveryCustomer(optionalDeliveryCustomer.get()); // Null references are allowed
                }
                if (orderModel.getOrderNo() != null) deliveryOrder.setOrderNo(orderModel.getOrderNo());
                if (orderModel.getSource() != null) deliveryOrder.setSource(orderModel.getSource());
                if (orderModel.getDriverName() != null) deliveryOrder.setDriverName(orderModel.getDriverName());
                if (orderModel.getZoneName() != null) deliveryOrder.setZoneName(orderModel.getZoneName());
                if (orderModel.getDeliveryTime() != null) deliveryOrder.setDeliveryTime(orderModel.getDeliveryTime());
                if (orderModel.getIsCancelled() != null) deliveryOrder.setIsCancelled(orderModel.getIsCancelled());
                if (orderModel.getIsDelivered() != null) deliveryOrder.setIsDelivered(orderModel.getIsDelivered());
                deliveryOrder = deliveryOrderRepository.saveAndFlush(deliveryOrder);
            }
        }


        // Update payments (Replace all payments with the new ones)
        if (orderModel.getPayments() != null) {
            paymentRepository.deleteAllByOrderId(order.getId());
            for (PaymentModel p : orderModel.getPayments()) {
                Payment payment = new Payment();
                payment.setOwner(order.getOwner());
                payment.setStore(order.getStore());
                payment.setOrder(order);
                payment.setPaymentType(p.getType());
                payment.setName(p.getName());
                payment.setAmount(p.getAmount());
                paymentRepository.saveAndFlush(payment);
            }
        }

        return order;
    }

}


