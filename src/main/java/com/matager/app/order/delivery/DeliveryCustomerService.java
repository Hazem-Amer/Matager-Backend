package com.matager.app.order.delivery;

import at.orderking.bossApp.owner.Owner;
import at.orderking.bossApp.store.Store;
import at.orderking.bossApp.user.User;

import java.util.List;

public interface DeliveryCustomerService {


    List<DeliveryCustomer> syncDeliveryCustomers(User user, SyncDeliveryCustomerModel deliveryCustomersModel);

    DeliveryCustomer saveDeliveryCustomer(User user, DeliveryCustomerModel newDeliveryCustomer);

    DeliveryCustomer saveDeliveryCustomer(Owner owner, User user, Store store, DeliveryCustomerModel newDeliveryCustomer);

    DeliveryCustomer updateDeliveryCustomer(Owner owner, User user, Store store, DeliveryCustomerModel deliveryCustomerModel);
}
