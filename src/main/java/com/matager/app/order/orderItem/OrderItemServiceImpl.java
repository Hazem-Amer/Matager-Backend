/*
 * @Abdullah Sallam
 */

package com.matager.app.order.orderItem;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;

    @Override
    public List<String> getProductGroupsNames(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate) {
        return orderItemRepository.findProductGroupsNames(fromDate, toDate, ownerId);
    }

    @Override
    public List<String> getProductGroupsNames(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Long storeId) {
        return orderItemRepository.findProductGroupsNames(fromDate, toDate, ownerId, storeId);
    }


}
