/*
 * @Abdullah Sallam
 */

package com.matager.app.order;

import com.matager.app.cart.Cart;
import com.matager.app.order.model.OrderModel;
import com.matager.app.owner.Owner;
import com.matager.app.store.Store;
import com.matager.app.user.User;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface OrderService {

//    List<Order> syncOrders(User user, SyncOrdersModel ordersModel);
//
//    Order saveOrder(User user, OrderModel newOrder);
//
//    Order saveOrder(Owner owner, User user, Store store, Cart cart);
    Page<Order> getOrders(Long storeId,int page,int size);
    Order updateOrder(Long orderId,OrderModel orderModel);

    void deleteOrder(Long orderId);

    Map<String,Long> getOrdersInfo(Long orderId);

}
