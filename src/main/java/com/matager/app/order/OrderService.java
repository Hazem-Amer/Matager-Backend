/*
 * @Abdullah Sallam
 */

package com.matager.app.order;

import com.matager.app.order.model.OrderModel;
import com.matager.app.order.model.SyncOrdersModel;
import com.matager.app.owner.Owner;
import com.matager.app.store.Store;
import com.matager.app.user.User;

import java.util.List;

public interface OrderService {

    List<Order> syncOrders(User user, SyncOrdersModel ordersModel);

    Order saveOrder(User user, OrderModel newOrder);

    Order saveOrder(Owner owner, User user, Store store, OrderModel newOrder);

    Order updateOrder(Owner owner, User user, Store store, OrderModel orderModel);

}
