package com.matager.app.order.delivery;

import at.orderking.bossApp.repository.projection.DateAmountPro;
import at.orderking.bossApp.repository.projection.DateCountPro;
import at.orderking.bossApp.repository.projection.DistrictDateAmountPro;
import at.orderking.bossApp.repository.projection.NameCountPro;
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

    @Query(value = "SELECT driver_name as name, COUNT(*) as totalCount FROM delivery_order do " +
            "JOIN `order` o ON o.id = do.order_id " +
            "WHERE o.owner_id = :ownerId AND o.is_cancelled = 0 AND o.created_at BETWEEN :fromDate AND :toDate " +
            "GROUP BY driver_name", nativeQuery = true)
    List<NameCountPro> getDriverDeliveries(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId);

    @Query(value = "SELECT driver_name as name, COUNT(*) as totalCount FROM delivery_order do " +
            "JOIN `order` o ON o.id = do.order_id " +
            "WHERE o.owner_id = :ownerId AND o.store_id = :storeId AND o.is_cancelled = 0 AND o.created_at BETWEEN :fromDate AND :toDate " +
            "GROUP BY driver_name", nativeQuery = true)
    List<NameCountPro> getDriverDeliveries(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId, Long storeId);

    @Query(value = "SELECT zone_name FROM delivery_order do " +
            "JOIN `order` o ON o.id = do.order_id " +
            "WHERE o.owner_id = :ownerId AND o.is_cancelled = 0 AND o.created_at BETWEEN :fromDate AND :toDate " +
            "GROUP BY zone_name ORDER BY zone_name ", nativeQuery = true)
    List<String> getDistrictDeliveriesNames(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId);

    @Query(value = "SELECT zone_name as name, COUNT(*) as totalCount FROM delivery_order do " +
            "JOIN `order` o ON o.id = do.order_id " +
            "WHERE o.owner_id = :ownerId AND o.is_cancelled = 0 AND o.created_at BETWEEN :fromDate AND :toDate " +
            "GROUP BY zone_name", nativeQuery = true)
    List<NameCountPro> getDistrictDeliveries(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId);


    @Query(value = "SELECT " +
            "(CASE :timeUnit " +
            "WHEN 'DAY' THEN DATE_FORMAT(o.created_at - INTERVAL TIME_TO_SEC(:dayStartTime) SECOND, CONCAT('%Y-%m-%dT',:dayStartTime)) " +
            "WHEN 'WEEK' THEN DATE_FORMAT(DATE_SUB(o.created_at, INTERVAL IF(DAYOFWEEK(o.created_at) = 1, 6, DAYOFWEEK(o.created_at) - 2) DAY), CONCAT('%Y-%m-%dT',:dayStartTime))" +
            "WHEN 'MONTH' THEN DATE_FORMAT(o.created_at - INTERVAL TIME_TO_SEC(:dayStartTime) SECOND, CONCAT('%Y-%m-01T',:dayStartTime)) " +
            "WHEN 'YEAR' THEN DATE_FORMAT(o.created_at - INTERVAL TIME_TO_SEC(:dayStartTime) SECOND, CONCAT('%Y-01-01T',:dayStartTime)) END) as date," +
            "COUNT(*) as count " +
            "FROM delivery_order do " +
            "JOIN `order` o ON o.id = do.order_id " +
            "WHERE o.owner_id = :ownerId " +
            "AND o.created_at BETWEEN :fromDate AND :toDate AND zone_name = :district AND o.is_cancelled = 0  " +
            "GROUP BY " +
            "date," +
            "zone_name " +
            "ORDER BY date ", nativeQuery = true)
    List<DateCountPro> getDistrictDeliveries(String dayStartTime, LocalDateTime fromDate, LocalDateTime toDate, String district, Long ownerId, String timeUnit);


//    @Query(value = "SELECT DATE(delivery_time) as date, COUNT(*) as count FROM delivery_order do " +
//            "JOIN `order` o ON o.id = do.order_id " +
//            "WHERE owner_id = :ownerId " +
//            "AND o.created_at BETWEEN :fromDate AND :toDate AND zone_name = :district AND o.is_cancelled = 0  " +
//            "GROUP BY date, zone_name " +
//            "ORDER BY date ", nativeQuery = true)
//    List<DateCountPro> getDistrictDeliveriesByDay(LocalDateTime fromDate, LocalDateTime toDate, String district, Long ownerId);

//    @Query(value = "SELECT CASE WHEN DAYOFWEEK(delivery_time) != 2 THEN DATE(DATE_SUB(delivery_time, INTERVAL (DAYOFWEEK(delivery_time) - 2) DAY)) " +
//            "ELSE DATE(delivery_time) END AS date, COUNT(*) AS count FROM delivery_order " +
//            "WHERE owner_id = :ownerId AND delivery_time BETWEEN :fromDate AND :toDate AND zone_name = :district " +
//            "AND is_delivered = 1  " +
//            "GROUP BY date, zone_name " +
//            "ORDER BY date", nativeQuery = true)
//    List<DateCountPro> getDistrictDeliveriesByWeek(LocalDateTime fromDate, LocalDateTime toDate, String district, Long ownerId);
//
//    @Query(value = "SELECT CASE WHEN DAY(delivery_time) != 1 THEN DATE_FORMAT(delivery_time, '%Y-%m-01') " +
//            "ELSE DATE(delivery_time) END AS date, COUNT(*) as count FROM delivery_order " +
//            "WHERE owner_id = :ownerId AND delivery_time BETWEEN :fromDate AND :toDate AND zone_name = :district " +
//            "AND is_delivered = 1  " +
//            "GROUP BY CONCAT(MONTH(delivery_time), YEAR(delivery_time)), zone_name " +
//            "ORDER BY delivery_time", nativeQuery = true)
//    List<DateCountPro> getDistrictDeliveriesByMonth(LocalDateTime fromDate, LocalDateTime toDate, String district, Long ownerId);
//
//    @Query(value = "SELECT CASE WHEN (DAY(delivery_time) != 1 OR MONTH(delivery_time) != 1) THEN DATE_FORMAT(delivery_time, '%Y-01-01') " +
//            "ELSE DATE(delivery_time) END AS date, COUNT(*) as count FROM delivery_order " +
//            "WHERE owner_id = :ownerId AND delivery_time BETWEEN :fromDate AND :toDate AND zone_name = :district " +
//            "AND is_delivered = 1  " +
//            "GROUP BY date, zone_name " +
//            "ORDER BY date", nativeQuery = true)
//    List<DateCountPro> getDistrictDeliveriesByYear(LocalDateTime fromDate, LocalDateTime toDate, String district, Long ownerId);
//


    @Query(value = "SELECT " +
            "(CASE :timeUnit " +
            "WHEN 'DAY' THEN DATE_FORMAT(o.created_at - INTERVAL TIME_TO_SEC(:dayStartTime) SECOND, CONCAT('%Y-%m-%dT',:dayStartTime)) " +
            "WHEN 'WEEK' THEN DATE_FORMAT(DATE_SUB(o.created_at, INTERVAL IF(DAYOFWEEK(o.created_at) = 1, 6, DAYOFWEEK(o.created_at) - 2) DAY), CONCAT('%Y-%m-%dT',:dayStartTime))" +
            "WHEN 'MONTH' THEN DATE_FORMAT(o.created_at - INTERVAL TIME_TO_SEC(:dayStartTime) SECOND, CONCAT('%Y-%m-01T',:dayStartTime)) " +
            "WHEN 'YEAR' THEN DATE_FORMAT(o.created_at - INTERVAL TIME_TO_SEC(:dayStartTime) SECOND, CONCAT('%Y-01-01T',:dayStartTime)) END) as date," +
            "COUNT(*) as count " +
            "FROM delivery_order do " +
            "JOIN `order` o ON o.id = do.order_id " +
            "WHERE o.owner_id = :ownerId " +
            "AND o.created_at BETWEEN :fromDate AND :toDate AND o.is_cancelled = 0  " +
            "GROUP BY " +
            "date " +
            "ORDER BY date ", nativeQuery = true)
    List<DateCountPro> getDistrictDeliveries(String dayStartTime, LocalDateTime fromDate, LocalDateTime toDate, Long ownerId, String timeUnit);


//    @Query(value = "SELECT DATE(delivery_time) AS date, COUNT(*) as count FROM delivery_order where owner_id = :ownerId " +
//            "AND delivery_time BETWEEN :fromDate AND :toDate AND is_delivered = 1  " +
//            "GROUP BY date ORDER BY date ", nativeQuery = true)
//    List<DateCountPro> getDistrictDeliveriesByDay(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId);
//
//
//    @Query(value = "SELECT CASE WHEN DAYOFWEEK(delivery_time) != 2 THEN DATE(DATE_SUB(delivery_time, INTERVAL (DAYOFWEEK(delivery_time) - 2) DAY)) " +
//            "ELSE DATE(delivery_time) END AS date, COUNT(*) AS count FROM delivery_order " +
//            "WHERE owner_id = :ownerId AND delivery_time BETWEEN :fromDate AND :toDate " +
//            "AND is_delivered = 1  " +
//            "GROUP BY date ORDER BY date", nativeQuery = true)
//    List<DateCountPro> getDistrictDeliveriesByWeek(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId);
//
//
//
//    @Query(value = "SELECT CASE WHEN DAY(delivery_time) != 1 THEN DATE_FORMAT(delivery_time, '%Y-%m-01') " +
//            "ELSE DATE(delivery_time) END AS date, COUNT(*) as count FROM delivery_order " +
//            "WHERE owner_id = :ownerId AND delivery_time BETWEEN :fromDate AND :toDate " +
//            "AND is_delivered = 1  " +
//            "GROUP BY date ORDER BY date", nativeQuery = true)
//    List<DateCountPro> getDistrictDeliveriesByMonth(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId);
//
//
//
//    @Query(value = "SELECT CASE WHEN (DAY(delivery_time) != 1 OR MONTH(delivery_time) != 1) THEN DATE_FORMAT(delivery_time, '%Y-01-01') " +
//            "ELSE DATE(delivery_time) END AS date, COUNT(*) AS count FROM delivery_order " +
//            "WHERE owner_id = :ownerId AND delivery_time BETWEEN :fromDate AND :toDate " +
//            "AND is_delivered = 1  " +
//            "GROUP BY date ORDER BY date", nativeQuery = true)
//    List<DateCountPro> getDistrictDeliveriesByYear(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId);


    @Query(value = "SELECT " +
            "(CASE :timeUnit " +
            "WHEN 'DAY' THEN DATE_FORMAT(o.created_at - INTERVAL TIME_TO_SEC(:dayStartTime) SECOND, CONCAT('%Y-%m-%dT',:dayStartTime)) " +
            "WHEN 'WEEK' THEN DATE_FORMAT(DATE_SUB(o.created_at, INTERVAL IF(DAYOFWEEK(o.created_at) = 1, 6, DAYOFWEEK(o.created_at) - 2) DAY), CONCAT('%Y-%m-%dT',:dayStartTime))" +
            "WHEN 'MONTH' THEN DATE_FORMAT(o.created_at - INTERVAL TIME_TO_SEC(:dayStartTime) SECOND, CONCAT('%Y-%m-01T',:dayStartTime)) " +
            "WHEN 'YEAR' THEN DATE_FORMAT(o.created_at - INTERVAL TIME_TO_SEC(:dayStartTime) SECOND, CONCAT('%Y-01-01T',:dayStartTime)) END) as date," +
            "AVG(TIMESTAMPDIFF(MINUTE, o.created_at, do.delivery_time)) AS amount " +
            "FROM delivery_order `do` " +
            "JOIN `order` o ON do.order_id = o.id " +
            "WHERE o.owner_id = :ownerId AND o.created_at BETWEEN :fromDate AND :toDate AND is_delivered = 1  " +
            "GROUP BY " +
            "date " +
            "ORDER BY date", nativeQuery = true)
    List<DateAmountPro> getAverageDeliveryTime(String dayStartTime, LocalDateTime fromDate, LocalDateTime toDate, Long ownerId, String timeUnit);

//    @Query(value = "SELECT DATE(delivery_time) AS date, AVG(TIMESTAMPDIFF(MINUTE, o.created_at, do.delivery_time)) AS amount " +
//            "FROM delivery_order `do` JOIN `order` o ON do.order_id = o.id " +
//            "WHERE do.owner_id = :ownerId AND o.created_at BETWEEN :fromDate AND :toDate AND is_delivered = 1  " +
//            "GROUP BY DATE(delivery_time) ORDER BY delivery_time", nativeQuery = true)
//    List<DateAmountPro> getAverageDeliveryTimeByDay(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId);
//
//    @Query(value = "SELECT DATE_FORMAT(DATE_SUB(delivery_time, INTERVAL IF(DAYOFWEEK(delivery_time) = 1, 6, DAYOFWEEK(delivery_time) - 2) DAY), '%Y-%m-%d') AS date, " +
//            "AVG(TIMESTAMPDIFF(MINUTE, o.created_at, do.delivery_time)) AS amount " +
//            "FROM delivery_order `do` JOIN `order` o ON `do`.order_id = o.id " +
//            "WHERE do.owner_id = :ownerId AND o.created_at BETWEEN :fromDate AND :toDate AND is_delivered = 1  " +
//            "GROUP BY date ORDER BY delivery_time", nativeQuery = true)
//    List<DateAmountPro> getAverageDeliveryTimeByWeek(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId);
//
//    @Query(value = "SELECT CASE WHEN DAY(delivery_time) != 1 THEN DATE_FORMAT(delivery_time, '%Y-%m-01') " +
//            "ELSE DATE(delivery_time) END AS date, AVG(TIMESTAMPDIFF(MINUTE, o.created_at, do.delivery_time)) AS amount " +
//            "FROM delivery_order `do` JOIN `order` o ON do.order_id = o.id " +
//            "WHERE do.owner_id = :ownerId AND o.created_at BETWEEN :fromDate AND :toDate AND is_delivered = 1  " +
//            "GROUP BY CONCAT(MONTH(delivery_time), YEAR(delivery_time)) ORDER BY delivery_time", nativeQuery = true)
//    List<DateAmountPro> getAverageDeliveryTimeByMonth(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId);
//
//    @Query(value = "SELECT CASE WHEN (DAY(delivery_time) != 1 OR MONTH(delivery_time) != 1) THEN DATE_FORMAT(delivery_time, '%Y-01-01') " +
//            "ELSE DATE(delivery_time) END AS date, AVG(TIMESTAMPDIFF(MINUTE, o.created_at, do.delivery_time)) AS amount " +
//            "FROM delivery_order `do` JOIN `order` o ON do.order_id = o.id " +
//            "WHERE do.owner_id = :ownerId AND o.created_at BETWEEN :fromDate AND :toDate AND is_delivered = 1  " +
//            "GROUP BY YEAR(delivery_time) ORDER BY delivery_time", nativeQuery = true)
//    List<DateAmountPro> getAverageDeliveryTimeByYear(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId);


    @Query(value = "SELECT zone_name AS district, " +
            "(CASE :timeUnit " +
            "WHEN 'DAY' THEN DATE_FORMAT(o.created_at - INTERVAL TIME_TO_SEC(:dayStartTime) SECOND, CONCAT('%Y-%m-%dT',:dayStartTime)) " +
            "WHEN 'WEEK' THEN DATE_FORMAT(DATE_SUB(o.created_at, INTERVAL IF(DAYOFWEEK(o.created_at) = 1, 6, DAYOFWEEK(o.created_at) - 2) DAY), CONCAT('%Y-%m-%dT',:dayStartTime))" +
            "WHEN 'MONTH' THEN DATE_FORMAT(o.created_at - INTERVAL TIME_TO_SEC(:dayStartTime) SECOND, CONCAT('%Y-%m-01T',:dayStartTime)) " +
            "WHEN 'YEAR' THEN DATE_FORMAT(o.created_at - INTERVAL TIME_TO_SEC(:dayStartTime) SECOND, CONCAT('%Y-01-01T',:dayStartTime)) END) as date," +
            "SUM(o.total) AS amount " +
            "FROM delivery_order `do` JOIN `order` o ON do.order_id = o.id " +
            "WHERE o.owner_id = :ownerId AND o.created_at BETWEEN :fromDate AND :toDate " +
            "AND o.is_cancelled = 0 " +
            "GROUP BY " +
            "date " +
            "ORDER BY date", nativeQuery = true)
    List<DistrictDateAmountPro> getDistrictDeliveryRevenue(String dayStartTime, LocalDateTime fromDate, LocalDateTime toDate, Long ownerId, String timeUnit);

//    @Query(value = "SELECT zone_name AS district, " +
//            "DATE(delivery_time) AS date, " +
//            "SUM(o.total) AS amount " +
//            "FROM delivery_order `do` JOIN `order` o ON do.order_id = o.id " +
//            "WHERE do.owner_id = :ownerId AND delivery_time BETWEEN :fromDate AND :toDate " +
//            "AND is_delivered = 1 " +
//            "GROUP BY date " +
//            "ORDER BY date", nativeQuery = true)
//    List<DistrictDateAmountPro> getDistrictDeliveryRevenueByDay(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId);
//
//    @Query(value = "SELECT zone_name AS district, DATE_FORMAT(DATE_SUB(delivery_time, INTERVAL IF(DAYOFWEEK(delivery_time) = 1, 6, DAYOFWEEK(delivery_time) - 2) DAY), '%Y-%m-%d') AS date, " +
//            "SUM(o.total) AS amount FROM delivery_order `do` JOIN `order` o ON do.order_id = o.id " +
//            "WHERE do.owner_id = :ownerId AND delivery_time BETWEEN :fromDate AND :toDate " +
//            "AND is_delivered = 1  " +
//            "GROUP BY date ORDER BY date ", nativeQuery = true)
//    List<DistrictDateAmountPro> getDistrictDeliveryRevenueByWeek(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId);
//
//    @Query(value = "SELECT zone_name AS district, DATE_FORMAT(delivery_time, '%Y-%m-01') AS date, SUM(o.total) AS amount " +
//            "FROM delivery_order `do` JOIN `order` o ON do.order_id = o.id " +
//            "WHERE do.owner_id = :ownerId AND delivery_time BETWEEN :fromDate AND :toDate " +
//            "AND is_delivered = 1 " +
//            "GROUP BY date ORDER BY date ", nativeQuery = true)
//    List<DistrictDateAmountPro> getDistrictDeliveryRevenueByMonth(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId);
//
//    @Query(value = "SELECT zone_name AS district, DATE_FORMAT(delivery_time, '%Y-01-01') AS date, SUM(o.total) AS amount " +
//            "FROM delivery_order `do` JOIN `order` o ON do.order_id = o.id " +
//            "WHERE do.owner_id = :ownerId AND delivery_time BETWEEN :fromDate AND :toDate " +
//            "AND is_delivered = 1  " +
//            "GROUP BY date ORDER BY date ", nativeQuery = true)
//    List<DistrictDateAmountPro> getDistrictDeliveryRevenueByYear(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId);

    @Query(value = "SELECT zone_name AS district," +
            "(CASE :timeUnit " +
            "WHEN 'DAY' THEN DATE_FORMAT(o.created_at - INTERVAL TIME_TO_SEC(:dayStartTime) SECOND, CONCAT('%Y-%m-%dT',:dayStartTime)) " +
            "WHEN 'WEEK' THEN DATE_FORMAT(DATE_SUB(o.created_at, INTERVAL IF(DAYOFWEEK(o.created_at) = 1, 6, DAYOFWEEK(o.created_at) - 2) DAY), CONCAT('%Y-%m-%dT',:dayStartTime))" +
            "WHEN 'MONTH' THEN DATE_FORMAT(o.created_at - INTERVAL TIME_TO_SEC(:dayStartTime) SECOND, CONCAT('%Y-%m-01T',:dayStartTime)) " +
            "WHEN 'YEAR' THEN DATE_FORMAT(o.created_at - INTERVAL TIME_TO_SEC(:dayStartTime) SECOND, CONCAT('%Y-01-01T',:dayStartTime)) END) as date," +
            "SUM(o.total) AS amount " +
            "FROM delivery_order `do` " +
            "JOIN `order` o ON do.order_id = o.id WHERE o.owner_id = :ownerId " +
            "AND o.created_at BETWEEN :fromDate AND :toDate AND zone_name IN :district AND o.is_cancelled = 0  " +
            "GROUP BY " +
            "date," +
            "zone_name " +
            "ORDER BY date ", nativeQuery = true)
    List<DistrictDateAmountPro> getDistrictDeliveryRevenue(String dayStartTime, LocalDateTime fromDate, LocalDateTime toDate, List<String> district, Long ownerId, String timeUnit);

//
//    @Query(value = "SELECT zone_name AS district, DATE(delivery_time) as date, SUM(o.total) AS amount " +
//            "FROM delivery_order `do` " +
//            "JOIN `order` o ON do.order_id = o.id WHERE do.owner_id = :ownerId " +
//            "AND delivery_time BETWEEN :fromDate AND :toDate AND zone_name IN :district AND is_delivered = 1  " +
//            "GROUP BY DATE(delivery_time), zone_name " +
//            "ORDER BY delivery_time ", nativeQuery = true)
//    List<DistrictDateAmountPro> getDistrictDeliveryRevenueByDay(LocalDateTime fromDate, LocalDateTime toDate, List<String> district, Long ownerId);
//
//    @Query(value = "SELECT zone_name AS district, DATE_FORMAT(DATE_SUB(delivery_time, INTERVAL IF(DAYOFWEEK(delivery_time) = 1, 6, DAYOFWEEK(delivery_time) - 2) DAY), '%Y-%m-%d') AS date, " +
//            "SUM(o.total) AS amount FROM delivery_order `do` JOIN `order` o ON do.order_id = o.id " +
//            "WHERE do.owner_id = :ownerId AND delivery_time BETWEEN :fromDate AND :toDate AND zone_name IN :district " +
//            "AND is_delivered = 1  " +
//            "GROUP BY date, zone_name " +
//            "ORDER BY delivery_time", nativeQuery = true)
//    List<DistrictDateAmountPro> getDistrictDeliveryRevenueByWeek(LocalDateTime fromDate, LocalDateTime toDate, List<String> district, Long ownerId);
//
//    @Query(value = "SELECT zone_name AS district, DATE_FORMAT(delivery_time, '%Y-%m-01') AS date, " +
//            "SUM(o.total) AS amount FROM delivery_order `do` JOIN `order` o ON do.order_id = o.id " +
//            "WHERE do.owner_id = :ownerId AND delivery_time BETWEEN :fromDate AND :toDate AND zone_name IN :district " +
//            "AND is_delivered = 1  " +
//            "GROUP BY CONCAT(MONTH(delivery_time), YEAR(delivery_time)), zone_name " +
//            "ORDER BY delivery_time", nativeQuery = true)
//    List<DistrictDateAmountPro> getDistrictDeliveryRevenueByMonth(LocalDateTime fromDate, LocalDateTime toDate, List<String> district, Long ownerId);
//
//    @Query(value = "SELECT zone_name AS district, DATE_FORMAT(delivery_time, '%Y-01-01') AS date, " +
//            "SUM(o.total) AS amount FROM delivery_order `do` JOIN `order` o ON do.order_id = o.id " +
//            "WHERE do.owner_id = :ownerId AND delivery_time BETWEEN :fromDate AND :toDate AND zone_name IN :district " +
//            "AND is_delivered = 1  " +
//            "GROUP BY YEAR(delivery_time), zone_name " +
//            "ORDER BY delivery_time", nativeQuery = true)
//    List<DistrictDateAmountPro> getDistrictDeliveryRevenueByYear(LocalDateTime fromDate, LocalDateTime toDate, List<String> district, Long ownerId);


    @Modifying
    @Query(value = "DELETE FROM delivery_order oi WHERE oi.store_id = :storeId", nativeQuery = true)
    void deleteAllByStoreId(Long storeId);
}
