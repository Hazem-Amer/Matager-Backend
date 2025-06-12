/*
 * @Abdullah Sallam
 */

package com.matager.app.order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, PagingAndSortingRepository<Order, Long> {

    Page<Order> findAllByStoreId(Long storeId, Pageable pageable);

    @Query(value = "SELECT * FROM `order` WHERE store_id = :storeId AND user_id = :userId", nativeQuery = true)
    Optional<List<Order>> findAllByStoreIdAndUserId(@Param("storeId") Long storeId, @Param("userId") Long userId);

//    Optional<Order> findByStoreIdAndInvoiceNo(Long storeId, Long invoiceNo);

    //    boolean existsByStoreIdAndInvoiceNo(Long storeId, Long invoiceNo);
    @Query(value = "SELECT COUNT(*) FROM `order` WHERE delivery_status = :deliveryStatus AND store_id = :storeId", nativeQuery = true)
    Long countByDeliveryStatus(@Param("storeId") Long storeId, @Param("deliveryStatus") String deliveryStatus);

    @Query(value = "SELECT COUNT(*) FROM `order` WHERE store_id = :storeId", nativeQuery = true)
    Long countAllOrders(@Param("storeId") Long storeId);


}
