package com.matager.app.order.orderhistory;

import com.matager.app.auth.AuthenticationFacade;
import com.matager.app.order.Order;
import com.matager.app.order.OrderRepository;
import com.matager.app.store.Store;
import com.matager.app.store.StoreRepository;
import com.matager.app.user.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderHistoryServiceImpl implements OrderHistoryService {
    private final AuthenticationFacade authenticationFacade;
    private final StoreRepository storeRepository;
    private final OrderRepository orderRepository;

    public List<OrderHistoryModel> getOrders(Long storeId, String deliveryStatus) {
        User user = authenticationFacade.getAuthenticatedUser();
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new RuntimeException("Store not found"));
        List<Order> orders = orderRepository.findAllByStoreIdAndUserId(store.getId(), user.getId())
                .orElseThrow(() -> new RuntimeException("This user has no order history yet"));

        return orders.stream().map(order -> {
            OrderHistoryModel orderHistoryModel = new OrderHistoryModel();
            orderHistoryModel.setId(order.getId());
            orderHistoryModel.setUserName(order.getUser().getName());
            orderHistoryModel.setPaymentType(order.getPaymentType());
            orderHistoryModel.setDeliveryStatus(order.getDeliveryStatus());
            orderHistoryModel.setIsPaid(order.getIsPaid());
            orderHistoryModel.setCreatedAt(order.getCreatedAt());
            orderHistoryModel.setDeliveredAt(order.getDeliveredAt());
            orderHistoryModel.setTotal(order.getTotal());
            orderHistoryModel.setItemImages(order.getItems().stream()
                    .flatMap(orderItem -> orderItem.getItem().getItemImages().stream())
                    .collect(Collectors.toList()));
            return orderHistoryModel;
        }).collect(Collectors.toList());
    }
}
