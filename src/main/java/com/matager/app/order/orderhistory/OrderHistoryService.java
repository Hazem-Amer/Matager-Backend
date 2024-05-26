package com.matager.app.order.orderhistory;

import com.matager.app.order.DeliveryStatus;
import com.matager.app.order.Order;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrderHistoryService {
    List<OrderHistoryModel> getOrders(Long storeId,String deliveryStatus);
}
