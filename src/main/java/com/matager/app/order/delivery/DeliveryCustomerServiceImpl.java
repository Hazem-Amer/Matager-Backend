package com.matager.app.order.delivery;

import at.orderking.bossApp.owner.Owner;
import at.orderking.bossApp.store.Store;
import at.orderking.bossApp.store.StoreRepository;
import at.orderking.bossApp.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class DeliveryCustomerServiceImpl implements DeliveryCustomerService {

    private final DeliveryCustomerRepository deliveryCustomerRepository;
    private final StoreRepository storeRepository;

    @Override
    public List<DeliveryCustomer> syncDeliveryCustomers(User user, SyncDeliveryCustomerModel deliveryCustomersModel) {
        Owner owner = user.getOwner();

        Store store;

        if (deliveryCustomersModel.getStoreUuid() != null) {
            store = storeRepository.findByOwnerIdAndUuid(owner.getId(), deliveryCustomersModel.getStoreUuid()).orElseThrow(() -> new RuntimeException("Store not found."));
        } else {
            if (user.getDefaultStore() == null)
                throw new RuntimeException("No default store found, please specify store uuid.");
            store = user.getDefaultStore();
        }

        List<DeliveryCustomer> deliveryCustomers = new ArrayList<>();

        for (DeliveryCustomerModel deliveryCustomerModel : deliveryCustomersModel.getCustomers()) {
            if (deliveryCustomerRepository.existsByStoreIdAndCustomerNo(store.getId(), deliveryCustomerModel.getCustomerNo())) {
                deliveryCustomers.add(updateDeliveryCustomer(owner, user, store, deliveryCustomerModel));
            } else {
                deliveryCustomers.add(saveDeliveryCustomer(owner, user, store, deliveryCustomerModel));
            }

        }

        return deliveryCustomers;
    }

    @Override
    public DeliveryCustomer saveDeliveryCustomer(User user, DeliveryCustomerModel newDeliveryCustomer) {
        // Not implemented
        throw new RuntimeException("Not implemented");
    }

    @Override
    public DeliveryCustomer saveDeliveryCustomer(Owner owner, User user, Store store, DeliveryCustomerModel newDeliveryCustomer) {
        DeliveryCustomer deliveryCustomer = new DeliveryCustomer();

        deliveryCustomer.setOwner(owner);
        deliveryCustomer.setStore(store);
        deliveryCustomer.setCustomerNo(newDeliveryCustomer.getCustomerNo());
        deliveryCustomer.setName(newDeliveryCustomer.getName());
        deliveryCustomer.setAddress(newDeliveryCustomer.getAddress());


        deliveryCustomer = deliveryCustomerRepository.save(deliveryCustomer);
        deliveryCustomerRepository.flush();

        return deliveryCustomer;
    }

    @Override
    public DeliveryCustomer updateDeliveryCustomer(Owner owner, User user, Store store, DeliveryCustomerModel deliveryCustomerModel) {
        DeliveryCustomer deliveryCustomer = deliveryCustomerRepository.findByStoreIdAndCustomerNo(store.getId(), deliveryCustomerModel.getCustomerNo()).orElseThrow(() -> new RuntimeException("Delivery Customer not found."));

        if (deliveryCustomerModel.getName() != null) deliveryCustomer.setName(deliveryCustomerModel.getName());
        if (deliveryCustomerModel.getAddress() != null) deliveryCustomer.setAddress(deliveryCustomerModel.getAddress());


        deliveryCustomer = deliveryCustomerRepository.save(deliveryCustomer);
        deliveryCustomerRepository.flush();

        return deliveryCustomer;
    }
}
