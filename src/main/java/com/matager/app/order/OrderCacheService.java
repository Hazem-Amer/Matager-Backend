/*
 * @Abdullah Sallam
 */

/*
 * @Abdullah Sallam
 */

package com.matager.app.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderCacheService {

    private final OrderRepository orderRepository;


    @Cacheable(value = "orders", key = "#ownerId")
    // use @CacheEvict(value="orders", key="#ownerId") to clear cache, if needed (e.g. after update, save, delete)
    public List<Order> getOrders(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate) {
        return orderRepository.getOrders(fromDate, toDate, ownerId);
    }

}
