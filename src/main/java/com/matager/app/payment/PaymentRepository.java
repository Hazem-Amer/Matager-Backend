/*
 * @Abdullah Sallam
 */

package com.matager.app.payment;

import com.matager.app.common.statistics.projection.PaymentDateCountAmountPro;
import com.matager.app.common.statistics.projection.PaymentTypeSalePro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    @Query(value = "SELECT SUM(p.amount) AS totalAmount, p.name, p.payment_type AS paymentType FROM payment p "
            + "LEFT JOIN `order` o ON p.order_id = o.id "
            + "WHERE (o.created_at BETWEEN :fromDate AND :toDate) AND o.is_cancelled = 0 AND p.owner_id = :ownerId GROUP BY p.name, p.payment_type", nativeQuery = true)
    List<PaymentTypeSalePro> getSalesByPaymentType(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId);


    @Query(value = "SELECT SUM(p.amount) AS totalAmount, p.name, p.payment_type AS paymentType FROM payment p "
            + "LEFT JOIN `order` o ON p.order_id = o.id "
            + "WHERE (o.created_at BETWEEN :fromDate AND :toDate) AND o.is_cancelled = 0 AND p.owner_id = :ownerId AND p.store_id = :storeId GROUP BY p.name, p.payment_type", nativeQuery = true)
    List<PaymentTypeSalePro> getSalesByPaymentType(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId, Long storeId);


    @Query(value = "SELECT CONCAT(p.payment_type, ' (', p.name, ')') AS payment, " +
            "(CASE :timeUnit " +
            "WHEN 'DAY' THEN DATE_FORMAT(o.created_at - INTERVAL TIME_TO_SEC(:dayStartTime) SECOND, CONCAT('%Y-%m-%dT',:dayStartTime)) " +
            "WHEN 'WEEK' THEN DATE_FORMAT(DATE_SUB(o.created_at, INTERVAL IF(DAYOFWEEK(o.created_at) = 1, 6, DAYOFWEEK(o.created_at) - 2) DAY), CONCAT('%Y-%m-%dT',:dayStartTime))" +
            "WHEN 'MONTH' THEN DATE_FORMAT(o.created_at - INTERVAL TIME_TO_SEC(:dayStartTime) SECOND, CONCAT('%Y-%m-01T',:dayStartTime)) " +
            "WHEN 'YEAR' THEN DATE_FORMAT(o.created_at - INTERVAL TIME_TO_SEC(:dayStartTime) SECOND, CONCAT('%Y-01-01T',:dayStartTime)) END) as date," +
            "COUNT(*) AS count, " +
            "SUM(p.amount) AS amount " +
            "FROM payment p " +
            "LEFT JOIN `order` o ON p.order_id = o.id " +
            "WHERE (o.created_at BETWEEN :fromDate AND :toDate) AND o.is_cancelled = 0 AND p.owner_id = :ownerId AND o.store_id = :storeId " +
            "GROUP BY payment, date ORDER BY date", nativeQuery = true)
    List<PaymentDateCountAmountPro> getSalesByPaymentType(String dayStartTime, LocalDateTime fromDate, LocalDateTime toDate, Long ownerId, Long storeId, String timeUnit);

//    @Query(value = "SELECT CONCAT(p.payment_type, ' (', p.name, ')') AS payment, DATE(created_at) AS date, COUNT(*) AS count, SUM(p.amount) AS amount "
//            + "FROM payment p "
//            + "LEFT JOIN `order` o ON p.order_id = o.id "
//            + "WHERE (o.created_at BETWEEN :fromDate AND :toDate) AND o.is_cancelled = 0 AND p.owner_id = :ownerId AND o.store_id = :storeId " +
//            "GROUP BY payment, date " +
//            "ORDER BY date", nativeQuery = true)
//    List<PaymentDateCountAmountPro> getSalesByPaymentTypeByDay(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId, Long storeId);
//
//    @Query(value = "SELECT CONCAT(p.payment_type, ' (', p.name, ')') AS payment, DATE_FORMAT(DATE_SUB(delivery_time, INTERVAL IF(DAYOFWEEK(delivery_time) = 1, 6, DAYOFWEEK(delivery_time) - 2) DAY), '%Y-%m-%d') AS date, "
//            + "COUNT(*) AS count, SUM(p.amount) AS amount "
//            + "FROM payment p "
//            + "LEFT JOIN `order` o ON p.order_id = o.id "
//            + "WHERE (o.created_at BETWEEN :fromDate AND :toDate) AND o.is_cancelled = 0 AND p.owner_id = :ownerId AND o.store_id = :storeId "
//            + "GROUP BY payment, date "
//            + "ORDER BY date", nativeQuery = true)
//    List<PaymentDateCountAmountPro> getSalesByPaymentTypeByWeek(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId, Long storeId);
//
//    @Query(value = "SELECT CONCAT(p.payment_type, ' (', p.name, ')') AS payment, DATE_FORMAT(delivery_time, '%Y-%m-01') AS date, "
//            + "COUNT(*) AS count, SUM(p.amount) AS amount "
//            + "FROM payment p "
//            + "LEFT JOIN `order` o ON p.order_id = o.id "
//            + "WHERE (o.created_at BETWEEN :fromDate AND :toDate) AND o.is_cancelled = 0 AND p.owner_id = :ownerId AND o.store_id = :storeId "
//            + "GROUP BY payment, date "
//            + "ORDER BY date", nativeQuery = true)
//    List<PaymentDateCountAmountPro> getSalesByPaymentTypeByMonth(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId, Long storeId);
//
//    @Query(value = "SELECT CONCAT(p.payment_type, ' (', p.name, ')') AS payment, DATE_FORMAT(delivery_time, '%Y-01-01') AS date, "
//            + "COUNT(*) AS count, SUM(p.amount) AS amount "
//            + "FROM payment p "
//            + "LEFT JOIN `order` o ON p.order_id = o.id "
//            + "WHERE (o.created_at BETWEEN :fromDate AND :toDate) AND o.is_cancelled = 0 AND p.owner_id = :ownerId AND o.store_id = :storeId "
//            + "GROUP BY payment, date "
//            + "ORDER BY date", nativeQuery = true)
//    List<PaymentDateCountAmountPro> getSalesByPaymentTypeByYear(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId, Long storeId);


    @Modifying
    @Query(value = "DELETE FROM payment WHERE order_id = :orderId", nativeQuery = true)
    void deleteAllByOrderId(Long orderId);

    @Modifying
    @Query(value = "DELETE FROM payment WHERE store_id = :storeId", nativeQuery = true)
    void deleteAllByStoreId(Long storeId);

    /**
     * Hossam Edit
     */
    List<Payment> findAllByOwnerIdAndStoreId(Long ownerId, Long storeId);
}
