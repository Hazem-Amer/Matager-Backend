/*
 * @Abdullah Sallam
 */

package com.matager.app.order;

import at.orderking.bossApp.order.model.OrderModel;
import at.orderking.bossApp.order.model.SyncOrdersModel;
import at.orderking.bossApp.owner.Owner;
import at.orderking.bossApp.store.Store;
import at.orderking.bossApp.user.User;

import java.util.List;

public interface OrderService {

    List<Order> syncOrders(User user, SyncOrdersModel ordersModel);

    Order saveOrder(User user, OrderModel newOrder);

    Order saveOrder(Owner owner, User user, Store store, OrderModel newOrder);

    Order updateOrder(Owner owner, User user, Store store, OrderModel orderModel);

}
