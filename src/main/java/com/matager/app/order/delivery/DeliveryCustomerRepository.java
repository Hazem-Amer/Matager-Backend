package com.matager.app.order.delivery;

import at.orderking.bossApp.repository.projection.NameAmountPro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface DeliveryCustomerRepository extends JpaRepository<DeliveryCustomer, Long> {

    Optional<DeliveryCustomer> findByStoreIdAndCustomerNo(Long storeId, Long customerNo);

    boolean existsByStoreIdAndCustomerNo(Long storeId, Long customerNo);

    @Query(value = "SELECT name, SUM(total) as totalAmount FROM `order` o JOIN delivery_order `do` " +
            "ON o.id = do.order_id JOIN delivery_customer dc ON dc.id = delivery_customer_id " +
            "WHERE o.created_at BETWEEN :fromDate AND :toDate AND o.owner_id = :ownerId AND o.is_cancelled = 0 " +
            "GROUP BY dc.id ORDER BY totalAmount DESC LIMIT 10", nativeQuery = true)
    List<NameAmountPro> getTop10Customers(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId);

    @Query(value = "SELECT name, SUM(total) as totalAmount FROM `order` o JOIN delivery_order `do` " +
            "ON o.id = do.order_id JOIN delivery_customer dc ON dc.id = delivery_customer_id " +
            "WHERE o.created_at BETWEEN :fromDate AND :toDate AND o.owner_id = :ownerId AND o.store_id IN :storeIds AND o.is_cancelled = 0 " +
            "GROUP BY dc.id ORDER BY totalAmount DESC LIMIT 10", nativeQuery = true)
    List<NameAmountPro> getTop10Customers(LocalDateTime fromDate, LocalDateTime toDate, List<Long> storeIds, Long ownerId);


}
