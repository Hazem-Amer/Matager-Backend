/*
 * @Abdullah Sallam
 */

package com.matager.app.order.orderItem;

import com.matager.app.common.helper.general.DateHelper;
import com.matager.app.common.statistics.dto.GroupItemAmountDto;
import com.matager.app.common.statistics.dto.ItemSaleDto;
import com.matager.app.common.statistics.dto.StoreItemSaleDto;
import com.matager.app.common.statistics.dto.general.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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
