/*
 * @Abdullah Sallam
 */

package com.matager.app.order.orderItem;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderItemService {

    List<String> getProductGroupsNames(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate);

    List<String> getProductGroupsNames(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Long storeId);


}
