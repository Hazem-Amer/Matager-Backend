package com.matager.app.order.delivery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface DeliveryOrderRepository extends JpaRepository<DeliveryOrder, Long> {

    Optional<DeliveryOrder> findByOrderId(Long orderId);

    @Modifying
    @Query(value = "DELETE FROM delivery_order oi WHERE oi.store_id = :storeId", nativeQuery = true)
    void deleteAllByStoreId(Long storeId);
}
