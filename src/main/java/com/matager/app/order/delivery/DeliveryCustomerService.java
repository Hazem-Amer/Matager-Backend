package com.matager.app.order.delivery;

import com.matager.app.owner.Owner;
import com.matager.app.store.Store;
import com.matager.app.user.User;

import java.util.List;

public interface DeliveryCustomerService {


    List<DeliveryCustomer> syncDeliveryCustomers(User user, SyncDeliveryCustomerModel deliveryCustomersModel);

    DeliveryCustomer saveDeliveryCustomer(User user, DeliveryCustomerModel newDeliveryCustomer);

    DeliveryCustomer saveDeliveryCustomer(Owner owner, User user, Store store, DeliveryCustomerModel newDeliveryCustomer);

    DeliveryCustomer updateDeliveryCustomer(Owner owner, User user, Store store, DeliveryCustomerModel deliveryCustomerModel);
}
