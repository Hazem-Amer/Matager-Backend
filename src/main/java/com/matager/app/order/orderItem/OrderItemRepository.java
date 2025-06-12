/*
 * @Abdullah Sallam
 */

package com.matager.app.order.orderItem;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    @Query(value = "SELECT oi.product_group AS name " +
            "FROM order_item oi JOIN `order` o ON o.id = oi.order_id " +
            "WHERE o.created_at BETWEEN :fromDate AND :toDate AND oi.owner_id = :ownerId AND o.is_cancelled = 0 " +
            "GROUP BY name", nativeQuery = true)
    List<String> findProductGroupsNames(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId);

    @Query(value = "SELECT oi.product_group AS name " +
            "FROM order_item oi JOIN `order` o ON o.id = oi.order_id " +
            "WHERE o.created_at BETWEEN :fromDate AND :toDate AND oi.owner_id = :ownerId AND oi.store_id = :storeId AND o.is_cancelled = 0 " +
            "GROUP BY name", nativeQuery = true)
    List<String> findProductGroupsNames(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId, Long storeId);
}
