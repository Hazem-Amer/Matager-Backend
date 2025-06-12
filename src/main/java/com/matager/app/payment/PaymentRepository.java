/*
 * @Abdullah Sallam
 */

package com.matager.app.payment;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    @Modifying
    @Query(value = "DELETE FROM payment WHERE order_id = :orderId", nativeQuery = true)
    void deleteAllByOrderId(Long orderId);

    @Modifying
    @Query(value = "DELETE FROM payment WHERE store_id = :storeId", nativeQuery = true)
    void deleteAllByStoreId(Long storeId);


    List<Payment> findAllByOwnerIdAndStoreId(Long ownerId, Long storeId);
}
