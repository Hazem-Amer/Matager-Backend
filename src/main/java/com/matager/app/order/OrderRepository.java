/*
 * @Abdullah Sallam
 */

package com.matager.app.order;

import at.orderking.bossApp.repository.projection.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    // ----------------------- Widget Queries ----------------------- //
    boolean isSunday = true; // TODO: implement logic to determine the first day of the week based on user country.

    Optional<Order> findByStoreIdAndInvoiceNo(Long storeId, Long invoiceNo);

    boolean existsByStoreIdAndInvoiceNo(Long storeId, Long invoiceNo);

    @Modifying
    @Query(value = "DELETE FROM `order` o WHERE o.store_id = :storeId", nativeQuery = true)
    void deleteAllByStoreId(Long storeId);

    // Sales Totals
    @Query(value = "SELECT IFNULL(SUM(total), 0) FROM `order` WHERE created_at BETWEEN :fromDate AND :toDate AND owner_id = :ownerId", nativeQuery = true)
    //Assuming that there is a respective negative entry for each cancellation.
    Double getOrdersAmount(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId);

    @Query(value = "SELECT IFNULL(SUM(total), 0) FROM `order` WHERE created_at BETWEEN :fromDate AND :toDate AND owner_id = :ownerId AND store_id = :storeId", nativeQuery = true)
        //Assuming that there is a respective negative entry for each cancellation.
    Double getOrdersAmount(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId, Long storeId);

    @Query(value = "SELECT IFNULL(COUNT(id), 0) FROM `order` WHERE created_at BETWEEN :fromDate AND :toDate AND owner_id = :ownerId", nativeQuery = true)
    Integer getOrdersCount(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId);

    @Query(value = "SELECT IFNULL(COUNT(id), 0) FROM `order` WHERE created_at BETWEEN :fromDate AND :toDate AND owner_id = :ownerId AND store_id = :storeId", nativeQuery = true)
    Integer getOrdersCount(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId, Long storeId);

    // Levels Totals
    @Query(value = "SELECT IFNULL(COUNT(id), 0) FROM `order` WHERE created_at BETWEEN :fromDate AND :toDate AND owner_id = :ownerId AND level = :level", nativeQuery = true)
    Integer getOrdersCount(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId, String level);

    @Query(value = "SELECT IFNULL(COUNT(id), 0) FROM `order` WHERE created_at BETWEEN :fromDate AND :toDate AND owner_id = :ownerId AND store_id = :storeId AND level = :level", nativeQuery = true)
    Integer getOrdersCount(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId, Long storeId, String level);


    @Query(value = "SELECT " +
            "(CASE :timeUnit " +
            "WHEN 'DAY' THEN DATE_FORMAT(o.created_at - INTERVAL TIME_TO_SEC(:dayStartTime) SECOND, CONCAT('%Y-%m-%dT',:dayStartTime)) " +
            "WHEN 'WEEK' THEN DATE_FORMAT(DATE_SUB(o.created_at, INTERVAL IF(DAYOFWEEK(o.created_at) = 1, 6, DAYOFWEEK(o.created_at) - 2) DAY), CONCAT('%Y-%m-%dT',:dayStartTime))" +
            "WHEN 'MONTH' THEN DATE_FORMAT(o.created_at - INTERVAL TIME_TO_SEC(:dayStartTime) SECOND, CONCAT('%Y-%m-01T',:dayStartTime)) " +
            "WHEN 'YEAR' THEN DATE_FORMAT(o.created_at - INTERVAL TIME_TO_SEC(:dayStartTime) SECOND, CONCAT('%Y-01-01T',:dayStartTime)) END) as date," +
            "COUNT(id) AS count, " +
            "SUM(total) AS amount " +
            "FROM `order` o " +
            "WHERE created_at BETWEEN :fromDate AND :toDate AND is_cancelled = 0 AND owner_id = :ownerId AND store_id = :storeId " +
            "GROUP BY " +
            "date " +
            "ORDER BY date", nativeQuery = true)
    List<DateCountAmountPro> getOrdersSales(String dayStartTime, LocalDateTime fromDate, LocalDateTime toDate, Long ownerId, Long storeId, String timeUnit);


//    @Query(value = "SELECT DATE(created_at) AS date, COUNT(id) AS count, SUM(total) AS amount " +
//            "FROM `order` " +
//            "WHERE created_at BETWEEN :fromDate AND :toDate AND is_cancelled = 0 AND owner_id = :ownerId AND store_id = :storeId " +
//            "GROUP BY date " +
//            "ORDER BY date", nativeQuery = true)
//    List<DateCountAmountPro> getOrdersSalesByDay(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId, Long storeId);
//
//    @Query(value = "SELECT DATE_FORMAT(DATE_SUB(created_at, INTERVAL IF(DAYOFWEEK(created_at) = 1, 6, DAYOFWEEK(created_at) - 2) DAY), '%Y-%m-%d') AS date, " +
//            "COUNT(id) AS count, SUM(total) AS amount " +
//            "FROM `order` " +
//            "WHERE created_at BETWEEN :fromDate AND :toDate AND is_cancelled = 0 AND owner_id = :ownerId AND store_id = :storeId " +
//            "GROUP BY date " +
//            "ORDER BY date", nativeQuery = true)
//    List<DateCountAmountPro> getOrdersSalesByWeek(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId, Long storeId);
//
//    @Query(value = "SELECT DATE_FORMAT(created_at, '%Y-%m-01') AS date, COUNT(id) AS count, SUM(total) AS amount " +
//            "FROM `order` " +
//            "WHERE created_at BETWEEN :fromDate AND :toDate AND is_cancelled = 0 AND owner_id = :ownerId AND store_id = :storeId " +
//            "GROUP BY date " +
//            "ORDER BY date", nativeQuery = true)
//    List<DateCountAmountPro> getOrdersSalesByMonth(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId, Long storeId);
//
//    @Query(value = "SELECT DATE_FORMAT(created_at, '%Y-01-01') AS date, COUNT(id) AS count, SUM(total) AS amount " +
//            "FROM `order` " +
//            "WHERE created_at BETWEEN :fromDate AND :toDate AND is_cancelled = 0 AND owner_id = :ownerId AND store_id = :storeId " +
//            "GROUP BY date " +
//            "ORDER BY date", nativeQuery = true)
//    List<DateCountAmountPro> getOrdersSalesByYear(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId, Long storeId);


    // Cancellations Totals
    @Query(value = "SELECT IFNULL(SUM(total * -1), 0) FROM `order` WHERE created_at BETWEEN :fromDate AND :toDate AND owner_id = :ownerId AND is_cancelled = true", nativeQuery = true)
    Double getOrdersCancellationsAmount(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId);

    @Query(value = "SELECT IFNULL(SUM(total * -1), 0) FROM `order` WHERE created_at BETWEEN :fromDate AND :toDate AND owner_id = :ownerId AND is_cancelled = true AND store_id = :storeId", nativeQuery = true)
    Double getOrdersCancellationsAmount(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId, Long storeId);


    @Query(value = "SELECT " +
            "(CASE :timeUnit " +
            "WHEN 'DAY' THEN DATE_FORMAT(o.created_at - INTERVAL TIME_TO_SEC(:dayStartTime) SECOND, CONCAT('%Y-%m-%dT',:dayStartTime)) " +
            "WHEN 'WEEK' THEN DATE_FORMAT(DATE_SUB(o.created_at, INTERVAL IF(DAYOFWEEK(o.created_at) = 1, 6, DAYOFWEEK(o.created_at) - 2) DAY), CONCAT('%Y-%m-%dT',:dayStartTime))" +
            "WHEN 'MONTH' THEN DATE_FORMAT(o.created_at - INTERVAL TIME_TO_SEC(:dayStartTime) SECOND, CONCAT('%Y-%m-01T',:dayStartTime)) " +
            "WHEN 'YEAR' THEN DATE_FORMAT(o.created_at - INTERVAL TIME_TO_SEC(:dayStartTime) SECOND, CONCAT('%Y-01-01T',:dayStartTime)) END) as date," +
            "COUNT(id) AS 'count', IFNULL(SUM(total * -1),0) AS 'amount' " +
            "FROM `order` o " +
            "WHERE created_at BETWEEN :fromDate AND :toDate AND owner_id = :ownerId AND is_cancelled = 1 " +
            "GROUP BY " +
            "date "
            , nativeQuery = true)
    List<DateCountAmountPro> getOrdersCancellations(String dayStartTime, LocalDateTime fromDate, LocalDateTime toDate, Long ownerId, String timeUnit);

    @Query(value = "SELECT " +
            "(CASE :timeUnit " +
            "WHEN 'DAY' THEN DATE_FORMAT(o.created_at - INTERVAL TIME_TO_SEC(:dayStartTime) SECOND, CONCAT('%Y-%m-%dT',:dayStartTime)) " +
            "WHEN 'WEEK' THEN DATE_FORMAT(DATE_SUB(o.created_at, INTERVAL IF(DAYOFWEEK(o.created_at) = 1, 6, DAYOFWEEK(o.created_at) - 2) DAY), CONCAT('%Y-%m-%dT',:dayStartTime))" +
            "WHEN 'MONTH' THEN DATE_FORMAT(o.created_at - INTERVAL TIME_TO_SEC(:dayStartTime) SECOND, CONCAT('%Y-%m-01T',:dayStartTime)) " +
            "WHEN 'YEAR' THEN DATE_FORMAT(o.created_at - INTERVAL TIME_TO_SEC(:dayStartTime) SECOND, CONCAT('%Y-01-01T',:dayStartTime)) END) as date," +
            "COUNT(id) AS 'count', IFNULL(SUM(total * -1),0) AS 'amount' " +
            "FROM `order` o " +
            "WHERE created_at BETWEEN :fromDate AND :toDate AND owner_id = :ownerId AND store_id = :storeId " +
            "AND is_cancelled = 1 " +
            "GROUP BY " +
            "date ", nativeQuery = true)
    List<DateCountAmountPro> getOrdersCancellations(String dayStartTime, LocalDateTime fromDate, LocalDateTime toDate, Long ownerId, Long storeId, String timeUnit);

    @Query(value = "SELECT IFNULL(COUNT(id), 0) FROM `order` WHERE created_at BETWEEN :fromDate AND :toDate AND owner_id = :ownerId AND is_cancelled = true", nativeQuery = true)
    Integer getOrdersCancellationsCount(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId);

    @Query(value = "SELECT IFNULL(COUNT(id), 0) FROM `order` WHERE created_at BETWEEN :fromDate AND :toDate AND owner_id = :ownerId AND is_cancelled = true AND level = :level", nativeQuery = true)
    Integer getOrdersCancellationsCount(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId, String level);

    @Query(value = "SELECT IFNULL(COUNT(id), 0) FROM `order` WHERE created_at BETWEEN :fromDate AND :toDate AND owner_id = :ownerId AND store_id = :storeId AND is_cancelled = true", nativeQuery = true)
    Integer getOrdersCancellationsCount(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId, Long storeId);

    // Discounts Totals
    @Query(value = "SELECT IFNULL(COUNT(total_discounts), 0) AS total_discounts " +
            "FROM(SELECT COUNT(DISTINCT oi.discount) AS total_discounts FROM `order` o JOIN order_item oi ON o.id = oi.order_id " +
            "WHERE created_at BETWEEN :fromDate AND :toDate AND oi.owner_id = :ownerId AND oi.discount IS NOT NULL AND oi.discount > 0 GROUP BY o.id) sub", nativeQuery = true)
    Integer getOrdersDiscountsCount(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId);


    @Query(value = "SELECT IFNULL(COUNT(total_discounts), 0) AS total_discounts " +
            "FROM(SELECT COUNT(DISTINCT oi.discount) AS total_discounts FROM `order` o JOIN order_item oi ON o.id = oi.order_id " +
            "WHERE created_at BETWEEN :fromDate AND :toDate AND oi.owner_id = :ownerId AND oi.store_id = :storeId AND oi.discount IS NOT NULL AND oi.discount > 0 GROUP BY o.id) sub", nativeQuery = true)
    Integer getOrdersDiscountsCount(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId, Long storeId);

    @Query(value = "SELECT IFNULL(SUM(total_discounts),0) AS total_discounts " +
            "FROM(SELECT IFNULL(SUM(((oi.price * oi.count) / (1- IFNULL(oi.discount, 0))) - (oi.price * oi.count) ),0) AS total_discounts " +
            "FROM `order` o JOIN order_item oi ON o.id = oi.order_id WHERE o.created_at BETWEEN :fromDate AND :toDate " +
            "AND oi.owner_id = :ownerId AND oi.discount IS NOT NULL AND oi.discount > 0 GROUP BY o.id) sub", nativeQuery = true)
    Double getOrdersDiscountsAmount(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId);  //NOTE: Assuming that order_item.price is the price paid by customer after applying discount.

    @Query(value = "SELECT IFNULL(SUM(total_discounts),0) AS total_discounts " +
            "FROM(SELECT IFNULL(SUM(((oi.price * oi.count) / (1- IFNULL(oi.discount, 0))) - (oi.price * oi.count) ),0) AS total_discounts " +
            "FROM `order` o JOIN order_item oi ON o.id = oi.order_id WHERE o.created_at BETWEEN :fromDate AND :toDate " +
            "AND oi.owner_id = :ownerId AND o.store_id = :storeId AND oi.discount IS NOT NULL AND oi.discount > 0 GROUP BY o.id) sub", nativeQuery = true)
    Double getOrdersDiscountsAmount(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId, Long storeId);  //NOTE: Assuming that order_item.price is the price paid by customer after applying discount.

    @Query(value = "SELECT IFNULL(COUNT(o.id), 0) FROM `delivery_order` `do` " +
            "JOIN `order` o ON o.id = `do`.order_id " +
            "WHERE o.is_cancelled = 0 AND `do`.is_delivered = 0 AND o.owner_id = :ownerId AND o.created_at BETWEEN :fromDate AND :toDate", nativeQuery = true)
    Integer getOpenDeliveryOrders(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId);

    @Query(value = "SELECT IFNULL(COUNT(o.id), 0) FROM `delivery_order` `do` " +
            "JOIN `order` o ON o.id = `do`.order_id " +
            "WHERE o.owner_id = :ownerId AND o.store_id = :storeId AND o.is_cancelled = 0 AND `do`.is_delivered = 0 AND o.created_at BETWEEN :fromDate AND :toDate", nativeQuery = true)
    Integer getOpenDeliveryOrders(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId, Long storeId);

    @Query(value = "SELECT * FROM `order` WHERE owner_id = :ownerId AND created_at BETWEEN :fromDate AND :toDate", nativeQuery = true)
    List<Order> getOrders(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId);

    @Query(value = "SELECT DATE(created_at) as date, SUM(total) as amount " +
            "FROM `order` o JOIN store s ON o.owner_id = s.owner_id AND o.store_id = s.id " +
            "WHERE o.owner_id = :ownerId AND created_at BETWEEN :fromDate AND :toDate AND is_cancelled = 0 " +
            "GROUP BY date ORDER BY date ", nativeQuery = true)
    List<DateAmountPro> getOrdersGraph(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId);

    @Query(value = "SELECT s.`name` AS name , SUM(IFNULL(o.`total`, 0)) AS totalAmount " +
            "FROM `order` o RIGHT JOIN `store` s ON s.id = o.store_id " +
            "AND o.created_at BETWEEN :fromDate AND :toDate WHERE s.owner_id = :ownerId " +
            "GROUP BY s.`name` ", nativeQuery = true)
    List<NameAmountPro> getOrderStoresSales(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId);

    /**added*/
    /**
     * count number of discount related to store name
     */
    @Query(value = "SELECT s.`name` AS name, SUM(CASE WHEN IFNULL(oi.discount, 0) > 0 THEN 1 ELSE 0 END) AS 'totalCount' " +
            "FROM `order` o " +
            "JOIN `order_item` oi ON o.id = oi.order_id AND o.created_at BETWEEN :fromDate AND :toDate AND o.is_cancelled = 0 " +
            "RIGHT JOIN `store` s ON o.store_id = s.id " +
            "WHERE s.owner_id = :ownerId " +
            "GROUP BY s.`name` ", nativeQuery = true)
    List<NameCountPro> getDiscountStores(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId);

    @Query(value = "SELECT CASE WHEN (sub_level != '' AND sub_level IS NOT NULL) THEN CONCAT(level, ' (', sub_level, ')') ELSE level END AS name, IFNULL(SUM(total),0) as totalAmount FROM `order` " +
            "WHERE (level IN :sources OR sub_level IN :sources) AND created_at BETWEEN :fromDate AND :toDate AND owner_id = :ownerId AND is_cancelled = 0 GROUP BY name", nativeQuery = true)
    List<NameAmountPro> getSourceSales(LocalDateTime fromDate, LocalDateTime toDate, List<String> sources, Long ownerId);

    @Query(value = "SELECT CASE WHEN (sub_level != '' AND sub_level IS NOT NULL) THEN CONCAT(level, ' (', sub_level, ')') ELSE level END AS name, IFNULL(SUM(total),0) as totalAmount FROM `order` " +
            "WHERE created_at BETWEEN :fromDate AND :toDate AND owner_id = :ownerId AND is_cancelled = 0 GROUP BY name", nativeQuery = true)
    List<NameAmountPro> getSourceSales(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId);

    @Query(value = "SELECT " +
            "(CASE :timeUnit " +
            "WHEN 'DAY' THEN DATE_FORMAT(o.created_at - INTERVAL TIME_TO_SEC(:dayStartTime) SECOND, CONCAT('%Y-%m-%dT',:dayStartTime)) " +
            "WHEN 'WEEK' THEN DATE_FORMAT(DATE_SUB(o.created_at, INTERVAL IF(DAYOFWEEK(o.created_at) = 1, 6, DAYOFWEEK(o.created_at) - 2) DAY), CONCAT('%Y-%m-%dT',:dayStartTime))" +
            "WHEN 'MONTH' THEN DATE_FORMAT(o.created_at - INTERVAL TIME_TO_SEC(:dayStartTime) SECOND, CONCAT('%Y-%m-01T',:dayStartTime)) " +
            "WHEN 'YEAR' THEN DATE_FORMAT(o.created_at - INTERVAL TIME_TO_SEC(:dayStartTime) SECOND, CONCAT('%Y-01-01T',:dayStartTime)) END) as date," +
            "AVG(total) as amount " +
            "FROM `order` o " +
            "WHERE created_at BETWEEN :fromDate AND :toDate AND owner_id = :ownerId " +
            "AND is_cancelled = 0 " +
            "GROUP BY " +
            "date " +
            "ORDER BY date", nativeQuery = true)
    List<DateAmountPro> getAverageBasketSize(String dayStartTime, LocalDateTime fromDate, LocalDateTime toDate, Long ownerId, String timeUnit);

//    @Query(value = "SELECT DATE(created_at) as date, AVG(total) as amount FROM `order` " +
//            "WHERE created_at BETWEEN :fromDate AND :toDate AND owner_id = :ownerId " +
//            "AND is_cancelled = 0 " +
//            "GROUP BY date " +
//            "ORDER BY date", nativeQuery = true)
//    List<DateAmountPro> getAverageBasketSizeByDay(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId);
//
//    @Query(value = "SELECT DATE_FORMAT(DATE_SUB(created_at, INTERVAL IF(DAYOFWEEK(created_at) = 1, 6, DAYOFWEEK(created_at) - 2) DAY), '%Y-%m-%d') AS date, " +
//            "AVG(total) as amount FROM `order` WHERE created_at BETWEEN :fromDate AND :toDate AND owner_id = :ownerId " +
//            "AND is_cancelled = 0 " +
//            "GROUP BY date " +
//            "ORDER BY date", nativeQuery = true)
//    List<DateAmountPro> getAverageBasketSizeByWeek(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId);
//
//    @Query(value = "SELECT CASE WHEN DAY(created_at) != 1 THEN DATE_FORMAT(created_at, '%Y-%m-01') " +
//            "ELSE DATE(created_at) END AS date, AVG(total) as amount FROM `order` " +
//            "WHERE owner_id = :ownerId AND created_at BETWEEN :fromDate and :toDate " +
//            "AND is_cancelled = 0 " +
//            "GROUP BY CONCAT(MONTH(created_at),YEAR(created_at)) " +
//            "ORDER BY created_at", nativeQuery = true)
//    List<DateAmountPro> getAverageBasketSizeByMonth(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId);
//
//    @Query(value = "SELECT CASE WHEN (DAY(created_at) != 1 OR MONTH(created_at) != 1) THEN DATE_FORMAT(created_at, '%Y-01-01') " +
//            "ELSE DATE(created_at) END AS date, AVG(total) as amount FROM `order` " +
//            "WHERE created_at BETWEEN :fromDate and :toDate AND owner_id = :ownerId " +
//            "AND is_cancelled = 0 " +
//            "GROUP BY date " +
//            "ORDER BY date", nativeQuery = true)
//    List<DateAmountPro> getAverageBasketSizeByYear(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId);

    @Query(value = "SELECT " +
            "s.name AS store, " +
            "(CASE :timeUnit " +
            "WHEN 'DAY' THEN DATE_FORMAT(o.created_at - INTERVAL TIME_TO_SEC(:dayStartTime) SECOND, CONCAT('%Y-%m-%dT',:dayStartTime)) " +
            "WHEN 'WEEK' THEN DATE_FORMAT(DATE_SUB(o.created_at, INTERVAL IF(DAYOFWEEK(o.created_at) = 1, 6, DAYOFWEEK(o.created_at) - 2) DAY), CONCAT('%Y-%m-%dT',:dayStartTime))" +
            "WHEN 'MONTH' THEN DATE_FORMAT(o.created_at - INTERVAL TIME_TO_SEC(:dayStartTime) SECOND, CONCAT('%Y-%m-01T',:dayStartTime)) " +
            "WHEN 'YEAR' THEN DATE_FORMAT(o.created_at - INTERVAL TIME_TO_SEC(:dayStartTime) SECOND, CONCAT('%Y-01-01T',:dayStartTime)) END) as date," +
            "AVG(total) as amount  " +
            "FROM `order` o " +
            "JOIN store s ON o.store_id = s.id " +
            "WHERE o.created_at BETWEEN :fromDate AND :toDate AND o.owner_id = :ownerId AND o.store_id IN :storeIds " +
            "AND is_cancelled = 0 " +
            "GROUP BY store," +
            "date " +
            "ORDER BY date", nativeQuery = true)
    List<StoreDateAmountPro> getAverageBasketSize(String dayStartTime, LocalDateTime fromDate, LocalDateTime toDate, Long ownerId, List<Long> storeIds, String timeUnit);

//
//    @Query(value = "SELECT s.name AS store, DATE(o.created_at) as date, AVG(total) as amount  " +
//            "FROM `order` o " +
//            "JOIN store s ON o.store_id = s.id " +
//            "WHERE o.created_at BETWEEN :fromDate AND :toDate AND o.owner_id = :ownerId AND o.store_id IN :storeIds " +
//            "AND is_cancelled = 0 " +
//            "GROUP BY store, date " +
//            "ORDER BY date", nativeQuery = true)
//    List<StoreDateAmountPro> getAverageBasketSizeByDay(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId, List<Long> storeIds);
//
//    @Query(value = "SELECT " +
//            "s.name AS store, " +
//            "DATE_FORMAT(DATE_SUB(o.created_at, INTERVAL IF(DAYOFWEEK(created_at) = 1, 6, DAYOFWEEK(created_at) - 2) DAY), '%Y-%m-%d') AS date, " +
//            "AVG(o.total) AS amount " +
//            "FROM `order` o " +
//            "JOIN store s ON o.store_id = s.id " +
//            "WHERE o.created_at BETWEEN :fromDate AND :toDate AND o.owner_id = :ownerId AND o.store_id IN :storeIds " +
//            "AND is_cancelled = 0 " +
//            "GROUP BY store, date " +
//            "ORDER BY date", nativeQuery = true)
//    List<StoreDateAmountPro> getAverageBasketSizeByWeek(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId, List<Long> storeIds);
//
//    @Query(value = "SELECT " +
//            "s.name AS store, " +
//            "DATE_FORMAT(o.created_at, '%Y-%m-01') AS date, " +
//            "AVG(o.total) as amount " +
//            "FROM `order` o " +
//            "JOIN store s ON o.store_id = s.id " +
//            "WHERE o.owner_id = :ownerId AND o.created_at BETWEEN :fromDate and :toDate AND o.store_id IN :storeIds " +
//            "AND is_cancelled = 0 " +
//            "GROUP BY store, date " +
//            "ORDER BY date", nativeQuery = true)
//    List<StoreDateAmountPro> getAverageBasketSizeByMonth(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId, List<Long> storeIds);
//
//    @Query(value = "SELECT " +
//            "s.name AS store, " +
//            "DATE_FORMAT(o.created_at, '%Y-01-01') AS date, " +
//            "SUM(total) as amount " +
//            "FROM `order` o " +
//            "JOIN store s ON o.store_id = s.id " +
//            "WHERE o.created_at BETWEEN :fromDate and :toDate AND o.owner_id = :ownerId AND o.store_id IN :storeIds " +
//            "AND is_cancelled = 0 " +
//            "GROUP BY store, date " +
//            "ORDER BY date", nativeQuery = true)
//    List<StoreDateAmountPro> getAverageBasketSizeByYear(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId, List<Long> storeIds);


    @Query(value = "SELECT " +
            "(CASE :timeUnit " +
            "WHEN 'DAY' THEN DATE_FORMAT(o.created_at - INTERVAL TIME_TO_SEC(:dayStartTime) SECOND, CONCAT('%Y-%m-%dT',:dayStartTime)) " +
            "WHEN 'WEEK' THEN DATE_FORMAT(DATE_SUB(o.created_at, INTERVAL IF(DAYOFWEEK(o.created_at) = 1, 6, DAYOFWEEK(o.created_at) - 2) DAY), CONCAT('%Y-%m-%dT',:dayStartTime))" +
            "WHEN 'MONTH' THEN DATE_FORMAT(o.created_at - INTERVAL TIME_TO_SEC(:dayStartTime) SECOND, CONCAT('%Y-%m-01T',:dayStartTime)) " +
            "WHEN 'YEAR' THEN DATE_FORMAT(o.created_at - INTERVAL TIME_TO_SEC(:dayStartTime) SECOND, CONCAT('%Y-01-01T',:dayStartTime)) END) as date," +
            "AVG(o.total) AS amount " +
            "FROM `order` o " +
            "JOIN delivery_order do ON o.id = do.order_id " +
            "WHERE o.created_at BETWEEN :fromDate AND :toDate AND do.owner_id = :ownerId AND zone_name = :district " +
            "AND o.is_cancelled = 0  " +
            "GROUP BY " +
            "date " +
            "ORDER BY date", nativeQuery = true)
    List<DateAmountPro> getDeliveryAverageBasketSizeByDistrict(String dayStartTime, LocalDateTime fromDate, LocalDateTime toDate, Long ownerId, String district, String timeUnit);

//    @Query(value = "SELECT DATE(created_at) AS date, AVG(total) AS amount " +
//            "FROM `order` o " +
//            "JOIN delivery_order do ON o.id = do.order_id " +
//            "WHERE created_at BETWEEN :fromDate AND :toDate AND do.owner_id = :ownerId AND zone_name = :district " +
//            "AND do.is_cancelled = 0 AND is_delivered = 1 " +
//            "GROUP BY date " +
//            "ORDER BY created_at", nativeQuery = true)
//    List<DateAmountPro> getDeliveryAverageBasketSizeByDistrictByDay(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId, String district);
//
//
//    @Query(value = "SELECT DATE_FORMAT(DATE_SUB(o.created_at, INTERVAL IF(DAYOFWEEK(created_at) = 1, 6, DAYOFWEEK(created_at) - 2) DAY), '%Y-%m-%d') AS date, " +
//            "AVG(total) AS amount " +
//            "FROM `order` o " +
//            "JOIN delivery_order do ON o.id = do.order_id " +
//            "WHERE created_at BETWEEN :fromDate AND :toDate AND do.owner_id = :ownerId AND zone_name = :district " +
//            "AND do.is_cancelled = 0 AND is_delivered = 1 " +
//            "GROUP BY date " +
//            "ORDER BY created_at", nativeQuery = true)
//    List<DateAmountPro> getDeliveryAverageBasketSizeByDistrictByWeek(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId, String district);
//
//
//    @Query(value = "SELECT DATE_FORMAT(o.created_at, '%Y-%m-01') AS date, AVG(total) AS amount " +
//            "FROM `order` o " +
//            "JOIN delivery_order do ON o.id = do.order_id " +
//            "WHERE created_at BETWEEN :fromDate AND :toDate AND do.owner_id = :ownerId AND zone_name = :district " +
//            "AND do.is_cancelled = 0 AND is_delivered = 1 " +
//            "GROUP BY date " +
//            "ORDER BY created_at", nativeQuery = true)
//    List<DateAmountPro> getDeliveryAverageBasketSizeByDistrictByMonth(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId, String district);
//
//    @Query(value = "SELECT DATE_FORMAT(o.created_at, '%Y-01-01') AS date, AVG(total) AS amount " +
//            "FROM `order` o " +
//            "JOIN delivery_order do ON o.id = do.order_id " +
//            "WHERE created_at BETWEEN :fromDate AND :toDate AND do.owner_id = :ownerId AND zone_name = :district " +
//            "AND do.is_cancelled = 0 AND is_delivered = 1 " +
//            "GROUP BY date " +
//            "ORDER BY created_at", nativeQuery = true)
//    List<DateAmountPro> getDeliveryAverageBasketSizeByDistrictByYear(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId, String district);


    @Query(value = "SELECT " +
            "(CASE :timeUnit " +
            "WHEN 'DAY' THEN DATE_FORMAT(o.created_at - INTERVAL TIME_TO_SEC(:dayStartTime) SECOND, CONCAT('%Y-%m-%dT',:dayStartTime)) " +
            "WHEN 'WEEK' THEN DATE_FORMAT(DATE_SUB(o.created_at, INTERVAL IF(DAYOFWEEK(o.created_at) = 1, 6, DAYOFWEEK(o.created_at) - 2) DAY), CONCAT('%Y-%m-%dT',:dayStartTime))" +
            "WHEN 'MONTH' THEN DATE_FORMAT(o.created_at - INTERVAL TIME_TO_SEC(:dayStartTime) SECOND, CONCAT('%Y-%m-01T',:dayStartTime)) " +
            "WHEN 'YEAR' THEN DATE_FORMAT(o.created_at - INTERVAL TIME_TO_SEC(:dayStartTime) SECOND, CONCAT('%Y-01-01T',:dayStartTime)) END) as date," +
            " AVG(o.total) AS amount " +
            "FROM `order` o " +
            "JOIN delivery_order do ON o.id = do.order_id " +
            "WHERE o.created_at BETWEEN :fromDate AND :toDate AND do.owner_id = :ownerId " +
            "AND o.is_cancelled = 0 " +
            "GROUP BY " +
            "date " +
            "ORDER BY date", nativeQuery = true)
    List<DateAmountPro> getDeliveryAverageBasketSizeByDistrict(String dayStartTime, LocalDateTime fromDate, LocalDateTime toDate, Long ownerId, String timeUnit);
//
//
//    @Query(value = "SELECT DATE(created_at) as date, AVG(total) AS amount " +
//            "FROM `order` o " +
//            "JOIN delivery_order do ON o.id = do.order_id " +
//            "WHERE created_at BETWEEN :fromDate AND :toDate AND do.owner_id = :ownerId " +
//            "AND do.is_cancelled = 0 AND is_delivered = 1 " +
//            "GROUP BY date " +
//            "ORDER BY created_at", nativeQuery = true)
//    List<DateAmountPro> getDeliveryAverageBasketSizeByDistrictByDay(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId);
//
//
//    @Query(value = "SELECT DATE_FORMAT(DATE_SUB(o.created_at, INTERVAL IF(DAYOFWEEK(created_at) = 1, 6, DAYOFWEEK(created_at) - 2) DAY), '%Y-%m-%d') AS date, AVG(total) AS amount " +
//            "FROM `order` o " +
//            "JOIN delivery_order do ON o.id = do.order_id " +
//            "WHERE created_at BETWEEN :fromDate AND :toDate AND do.owner_id = :ownerId " +
//            "AND do.is_cancelled = 0 AND is_delivered = 1 " +
//            "GROUP BY date " +
//            "ORDER BY created_at", nativeQuery = true)
//    List<DateAmountPro> getDeliveryAverageBasketSizeByDistrictByWeek(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId);
//
//
//    @Query(value = "SELECT DATE_FORMAT(o.created_at, '%Y-%m-01') AS date, AVG(total) AS amount " +
//            "FROM `order` o " +
//            "JOIN delivery_order do ON o.id = do.order_id " +
//            "WHERE created_at BETWEEN :fromDate AND :toDate AND do.owner_id = :ownerId " +
//            "AND do.is_cancelled = 0 AND is_delivered = 1 " +
//            "GROUP BY date " +
//            "ORDER BY created_at", nativeQuery = true)
//    List<DateAmountPro> getDeliveryAverageBasketSizeByDistrictByMonth(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId);
//
//    @Query(value = "SELECT DATE_FORMAT(o.created_at, '%Y-01-01') AS date, AVG(total) AS amount " +
//            "FROM `order` o " +
//            "JOIN delivery_order do ON o.id = do.order_id " +
//            "WHERE created_at BETWEEN :fromDate AND :toDate AND do.owner_id = :ownerId " +
//            "AND do.is_cancelled = 0 AND is_delivered = 1 " +
//            "GROUP BY date " +
//            "ORDER BY created_at", nativeQuery = true)
//    List<DateAmountPro> getDeliveryAverageBasketSizeByDistrictByYear(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId);


    @Query(value = "SELECT DATE_FORMAT(o.created_at - INTERVAL TIME_TO_SEC(:dayStartTime) SECOND, CONCAT('%Y-%m-%dT',:dayStartTime)) AS date, SUM(total) AS amount " +
            "FROM `order` o JOIN store s ON o.owner_id = s.owner_id AND o.store_id= s.id " +
            "WHERE o.owner_id = :ownerId AND created_at BETWEEN :fromDate AND :toDate AND store_id IN :storeIds " +
            "GROUP BY date ORDER BY date", nativeQuery = true)
        // Will be grouped based on settings, or default to 6:00 AM
    List<DateAmountPro> getStoresDailySales(String dayStartTime, LocalDateTime fromDate, LocalDateTime toDate, List<Long> storeIds, Long ownerId);

    @Query(value = "SELECT " +
            "(CASE :timeUnit " +
            "WHEN 'DAY' THEN DATE_FORMAT(o.created_at - INTERVAL TIME_TO_SEC(:dayStartTime) SECOND, CONCAT('%Y-%m-%dT',:dayStartTime)) " +
            "WHEN 'WEEK' THEN DATE_FORMAT(DATE_SUB(o.created_at, INTERVAL IF(DAYOFWEEK(o.created_at) = 1, 6, DAYOFWEEK(o.created_at) - 2) DAY), CONCAT('%Y-%m-%dT',:dayStartTime))" +
            "WHEN 'MONTH' THEN DATE_FORMAT(o.created_at - INTERVAL TIME_TO_SEC(:dayStartTime) SECOND, CONCAT('%Y-%m-01T',:dayStartTime)) " +
            "WHEN 'YEAR' THEN DATE_FORMAT(o.created_at - INTERVAL TIME_TO_SEC(:dayStartTime) SECOND, CONCAT('%Y-01-01T',:dayStartTime)) END) as date," +
            "SUM(total) as totalAmount, " +
            "s.name " +
            "FROM `order` o JOIN store s ON o.owner_id = s.owner_id AND o.store_id= s.id " +
            "WHERE o.owner_id = :ownerId AND created_at BETWEEN :fromDate AND :toDate AND store_id IN :storeIds " +
            "GROUP BY date, store_id ORDER BY date", nativeQuery = true)
    List<StoreSalePro> getStoresSalesMulti(String dayStartTime, LocalDateTime fromDate, LocalDateTime toDate, List<Long> storeIds, Long ownerId, String timeUnit);


//
//    @Query(value = "SELECT DATE(created_at) as date, SUM(total) as totalAmount, s.name " +
//            "FROM `order` o JOIN store s ON o.owner_id = s.owner_id AND o.store_id= s.id " +
//            "WHERE o.owner_id = :ownerId AND created_at BETWEEN :fromDate AND :toDate AND store_id IN :storeIds " +
//            "GROUP BY date, store_id ORDER BY date", nativeQuery = true)
//    List<StoreSalePro> getStoresSalesMultiByDay(LocalDateTime fromDate, LocalDateTime toDate, List<Long> storeIds, Long ownerId);
//
//    @Query(value = "SELECT DATE_FORMAT(DATE_SUB(created_at, INTERVAL IF(DAYOFWEEK(created_at) = 1, 6, DAYOFWEEK(created_at) - 2) DAY), '%Y-%m-%d') AS date, " +
//            "SUM(total) as totalAmount, s.name " +
//            "FROM `order` o JOIN store s ON o.owner_id = s.owner_id AND o.store_id= s.id " +
//            "WHERE o.owner_id = :ownerId AND created_at BETWEEN :fromDate AND :toDate AND store_id IN :storeIds " +
//            "GROUP BY date, store_id ORDER BY date", nativeQuery = true)
//    List<StoreSalePro> getStoresSalesMultiByWeek(LocalDateTime fromDate, LocalDateTime toDate, List<Long> storeIds, Long ownerId);
//
//    @Query(value = "SELECT CASE WHEN DAY(created_at) != 1 THEN DATE_FORMAT(created_at, '%Y-%m-01') " +
//            "ELSE DATE(created_at) END AS date, SUM(total) as totalAmount, s.name " +
//            "FROM `order` o JOIN store s ON o.owner_id = s.owner_id AND o.store_id= s.id " +
//            "WHERE o.owner_id = :ownerId AND created_at BETWEEN :fromDate AND :toDate AND store_id IN :storeIds " +
//            "GROUP BY date, store_id ORDER BY date", nativeQuery = true)
//    List<StoreSalePro> getStoresSalesMultiByMonth(LocalDateTime fromDate, LocalDateTime toDate, List<Long> storeIds, Long ownerId);
//
//    @Query(value = "SELECT CASE WHEN (DAY(created_at) != 1 OR MONTH(created_at) != 1) THEN DATE_FORMAT(created_at, '%Y-01-01') " +
//            "ELSE DATE(created_at) END AS date, SUM(total) as totalAmount, s.name " +
//            "FROM `order` o JOIN store s ON o.owner_id = s.owner_id AND o.store_id= s.id " +
//            "WHERE o.owner_id = :ownerId AND created_at BETWEEN :fromDate AND :toDate AND store_id IN :storeIds " +
//            "GROUP BY date, store_id ORDER BY date", nativeQuery = true)
//    List<StoreSalePro> getStoresSalesMultiByYear(LocalDateTime fromDate, LocalDateTime toDate, List<Long> storeIds, Long ownerId);

    @Query(value = "SELECT " +
            "s.name AS store, " +
            "(CASE :timeUnit " +
            "WHEN 'DAY' THEN DATE_FORMAT(o.created_at - INTERVAL TIME_TO_SEC(:dayStartTime) SECOND, CONCAT('%Y-%m-%dT',:dayStartTime)) " +
            "WHEN 'WEEK' THEN DATE_FORMAT(DATE_SUB(o.created_at, INTERVAL IF(DAYOFWEEK(o.created_at) = 1, 6, DAYOFWEEK(o.created_at) - 2) DAY), CONCAT('%Y-%m-%dT',:dayStartTime))" +
            "WHEN 'MONTH' THEN DATE_FORMAT(o.created_at - INTERVAL TIME_TO_SEC(:dayStartTime) SECOND, CONCAT('%Y-%m-01T',:dayStartTime)) " +
            "WHEN 'YEAR' THEN DATE_FORMAT(o.created_at - INTERVAL TIME_TO_SEC(:dayStartTime) SECOND, CONCAT('%Y-01-01T',:dayStartTime)) END) as date," +
            "IFNULL(SUM(((oi.price * oi.count) / (1- IFNULL(oi.discount, 0))) - (oi.price * oi.count) ),0) AS amount " +
            "FROM `order` o JOIN order_item oi ON o.id = oi.order_id " +
            "JOIN store s ON o.store_id = s.id " +
            "WHERE oi.owner_id = :ownerId AND oi.store_id IN :storeIds AND o.created_at BETWEEN :fromDate AND :toDate AND o.is_cancelled = 0 " +
            "AND oi.discount IS NOT NULL AND oi.discount > 0 " +
            "GROUP BY store, date"
            , nativeQuery = true)
    List<StoreDateAmountPro> getStoresDiscounts(String dayStartTime, LocalDateTime fromDate, LocalDateTime toDate, List<Long> storeIds, Long ownerId, String timeUnit);

//
//    @Query(value = "SELECT " +
//            "s.name AS store, " +
//            "DATE(o.created_at) as date, " +
//            "IFNULL(SUM(((oi.price * oi.count) / (1- IFNULL(oi.discount, 0))) - (oi.price * oi.count) ),0) AS amount " +
//            "FROM `order` o JOIN order_item oi ON o.id = oi.order_id " +
//            "JOIN store s ON o.store_id = s.id " +
//            "WHERE oi.owner_id = :ownerId AND oi.store_id IN :storeIds AND o.created_at BETWEEN :fromDate AND :toDate AND o.is_cancelled = 0 " +
//            "AND oi.discount IS NOT NULL AND oi.discount > 0 " +
//            "GROUP BY store, date", nativeQuery = true)
//    List<StoreDateAmountPro> getStoresDiscountsByDay(LocalDateTime fromDate, LocalDateTime toDate, List<Long> storeIds, Long ownerId);
//
//    @Query(value = "SELECT " +
//            "s.name AS store," +
//            "DATE_FORMAT(DATE_SUB(o.created_at, INTERVAL IF(DAYOFWEEK(o.created_at) = 1, 6, DAYOFWEEK(o.created_at) - 2) DAY), '%Y-%m-%d') AS date, " +
//            "IFNULL(SUM(((oi.price * oi.count) / (1- IFNULL(oi.discount, 0))) - (oi.price * oi.count) ),0) AS amount " +
//            "FROM `order` o JOIN order_item oi ON o.id = oi.order_id " +
//            "JOIN store s ON o.store_id = s.id " +
//            "WHERE oi.owner_id = :ownerId AND oi.store_id IN :storeIds AND o.created_at BETWEEN :fromDate AND :toDate AND o.is_cancelled = 0 " +
//            "AND oi.discount IS NOT NULL AND oi.discount > 0 " +
//            "GROUP BY store, date", nativeQuery = true)
//    List<StoreDateAmountPro> getStoresDiscountsByWeek(LocalDateTime fromDate, LocalDateTime toDate, List<Long> storeIds, Long ownerId);
//
//    @Query(value = "SELECT " +
//            "s.name AS store, " +
//            "DATE_FORMAT(o.created_at, '%Y-%m-01') AS date, " +
//            "IFNULL(SUM(((oi.price * oi.count) / (1- IFNULL(oi.discount, 0))) - (oi.price * oi.count) ),0) AS amount " +
//            "FROM `order` o JOIN order_item oi ON o.id = oi.order_id " +
//            "JOIN store s ON o.store_id = s.id " +
//            "WHERE oi.owner_id = :ownerId AND oi.store_id IN :storeIds AND o.created_at BETWEEN :fromDate AND :toDate AND o.is_cancelled = 0 " +
//            "AND oi.discount IS NOT NULL AND oi.discount > 0 " +
//            "GROUP BY store, date ", nativeQuery = true)
//    List<StoreDateAmountPro> getStoresDiscountsByMonth(LocalDateTime fromDate, LocalDateTime toDate, List<Long> storeIds, Long ownerId);
//
//    @Query(value = "SELECT " +
//            "s.name AS store, " +
//            "DATE_FORMAT(o.created_at, '%Y-01-01') AS date, " +
//            "IFNULL(SUM(((oi.price * oi.count) / (1- IFNULL(oi.discount, 0))) - (oi.price * oi.count) ),0) AS amount " +
//            "FROM `order` o JOIN order_item oi ON o.id = oi.order_id " +
//            "JOIN store s ON o.store_id = s.id " +
//            "WHERE oi.owner_id = :ownerId AND oi.store_id IN :storeIds AND o.created_at BETWEEN :fromDate AND :toDate AND o.is_cancelled = 0 " +
//            "AND oi.discount IS NOT NULL AND oi.discount > 0 " +
//            "GROUP BY store, date", nativeQuery = true)
//    List<StoreDateAmountPro> getStoresDiscountsByYear(LocalDateTime fromDate, LocalDateTime toDate, List<Long> storeIds, Long ownerId);

    @Query(value = "SELECT cashier_name AS name, COUNT(id) AS count, SUM(total) AS amount " +
            "FROM `order` " +
            "WHERE owner_id = :ownerId AND store_id = :storeId AND created_at BETWEEN :fromDate AND :toDate " +
            "GROUP BY cashier_name", nativeQuery = true)
    List<NameCountAmountPro> getStoreSalesByUser(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId, Long storeId);
}
