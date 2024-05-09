/*
 * @Abdullah Sallam
 */

package com.matager.app.order.orderItem;

import com.matager.app.common.statistics.projection.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    @Query(value = "SELECT oi.item_name AS name, SUM(oi.count) AS salesCount, SUM(oi.price * oi.count) AS salesAmount " +
            "FROM order_item oi JOIN `order` o ON o.id = oi.order_id " +
            "WHERE oi.owner_id = :ownerId AND o.is_cancelled = 0 " +
            "AND o.created_at BETWEEN :fromDate AND :toDate GROUP BY oi.item_name ORDER BY salesCount DESC LIMIT 10", nativeQuery = true)
    List<ItemStatsPro> findTop10ItemsOrderedByCount(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId);

    @Query(value = "SELECT oi.item_name AS name, SUM(oi.count) AS salesCount, SUM(oi.price * oi.count) AS salesAmount " +
            "FROM order_item oi JOIN `order` o ON o.id = oi.order_id " +
            "WHERE oi.owner_id = :ownerId AND o.is_cancelled = 0  " +
            "AND o.created_at BETWEEN :fromDate AND :toDate GROUP BY oi.item_name ORDER BY salesAmount DESC LIMIT 10 ", nativeQuery = true)
    List<ItemStatsPro> findTop10ItemsOrderedByAmount(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId);

    /**added*/
    /**
     * to be checked and validate with abdullah
     */
    @Query(value = "SELECT s.name AS name, SUM(oi.count) AS salesCount, SUM(oi.price * oi.count) AS salesAmount  " +
            "FROM order_item oi JOIN `order` o ON o.id = oi.order_id JOIN store s ON  s.id = oi.store_id " +
            "WHERE oi.owner_id = :ownerId AND o.is_cancelled = 0 AND oi.item_name = :itemName " +
            "AND o.created_at BETWEEN :fromDate AND :toDate  " +
            "GROUP BY s.name ", nativeQuery = true)
    List<ItemStatsPro> findItemStoresComparison(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId, String itemName);

    /**
     * added
     */
    @Query(value = "SELECT s.name AS name, SUM(oi.count) AS salesCount, SUM(oi.price * oi.count) AS salesAmount  " +
            "FROM order_item oi JOIN `order` o ON o.id = oi.order_id JOIN store s ON  s.id = oi.store_id " +
            "WHERE oi.owner_id = :ownerId AND o.is_cancelled = 0 " +
            "AND o.created_at BETWEEN :fromDate AND :toDate  " +
            "GROUP BY s.name ORDER BY salesCount DESC LIMIT 10 ", nativeQuery = true)
    List<ItemStatsPro> findTop10ItemsStoresOrderedByAmount(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId);


    @Query(value = "SELECT oi.item_id AS id, oi.item_name AS name, SUM(oi.count) AS salesCount, SUM(oi.price * oi.count) AS salesAmount " +
            "FROM order_item oi JOIN `order` o ON o.id = oi.order_id " +
            "WHERE oi.owner_id = :ownerId AND o.is_cancelled = 0 " +
            "AND o.created_at BETWEEN :fromDate AND :toDate AND oi.store_id = :storeId GROUP BY oi.item_id, oi.item_name ORDER BY salesAmount DESC LIMIT 10", nativeQuery = true)
    List<ItemStatsPro> findTop10ItemsOrderedByAmount(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId, Long storeId);


    @Query(value = "SELECT oi.item_id AS id, oi.item_name AS name, SUM(oi.count) AS salesCount, SUM(oi.price * oi.count) AS salesAmount " +
            "FROM order_item oi JOIN `order` o ON o.id = oi.order_id " +
            "WHERE oi.owner_id = :ownerId AND o.is_cancelled = 0 " +
            "AND o.created_at BETWEEN :fromDate AND :toDate GROUP BY oi.item_id, oi.item_name ORDER BY salesAmount ASC LIMIT 10", nativeQuery = true)
    List<ItemStatsPro> findLeastItemsOrderedByAmount(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId);


    @Query(value = "SELECT oi.item_id AS id, oi.item_name AS name, SUM(oi.count) AS salesCount, SUM(oi.price * oi.count) AS salesAmount " +
            "FROM order_item oi JOIN `order` o ON o.id = oi.order_id " +
            "WHERE oi.owner_id = :ownerId AND o.is_cancelled = 0 " +
            "AND o.created_at BETWEEN :fromDate AND :toDate AND oi.store_id = :storeId GROUP BY oi.item_id, oi.item_name ORDER BY salesAmount ASC LIMIT 10", nativeQuery = true)
    List<ItemStatsPro> findLeastItemsOrderedByAmount(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId, Long storeId);


    @Query(value = "SELECT s.name AS store, oi.item_id AS id, oi.item_name AS name, SUM(oi.count) AS salesCount, SUM(oi.price * oi.count) AS salesAmount " +
            "FROM order_item oi " +
            "JOIN `order` o ON o.id = oi.order_id " +
            "JOIN store s ON s.id = o.store_id " +
            "WHERE oi.owner_id = :ownerId AND o.is_cancelled = 0 " +
            "AND o.created_at BETWEEN :fromDate AND :toDate " +
            "GROUP BY store, oi.item_id, oi.item_name " +
            "ORDER BY salesCount DESC LIMIT 10", nativeQuery = true)
    List<TopItemPerStorePro> findTop10ItemsPerStoreOrderedByCount(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId);

    @Query(value = "SELECT s.name AS store, oi.item_id AS id, oi.item_name AS name, SUM(oi.count) AS salesCount, SUM(oi.price * oi.count) AS salesAmount " +
            "FROM order_item oi " +
            "JOIN `order` o ON o.id = oi.order_id " +
            "JOIN store s ON s.id = o.store_id " +
            "WHERE oi.owner_id = :ownerId AND o.is_cancelled = 0 " +
            "AND o.created_at BETWEEN :fromDate AND :toDate " +
            "GROUP BY store, oi.item_id, oi.item_name " +
            "ORDER BY salesAmount DESC LIMIT 10", nativeQuery = true)
    List<TopItemPerStorePro> findTop10ItemsPerStoreOrderedByAmount(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId);

    @Query(value = "SELECT s.name AS store, oi.item_id AS id, oi.item_name AS name, SUM(oi.count) AS salesCount, SUM(oi.price * oi.count) AS salesAmount " +
            "FROM order_item oi " +
            "JOIN `order` o ON o.id = oi.order_id " +
            "JOIN store s ON s.id = o.store_id " +
            "WHERE oi.owner_id = :ownerId AND o.is_cancelled = 0 " +
            "AND o.created_at BETWEEN :fromDate AND :toDate AND o.store_id = :storeId " +
            "GROUP BY store, oi.item_id, oi.item_name " +
            "ORDER BY salesAmount DESC LIMIT 10", nativeQuery = true)
    List<TopItemPerStorePro> findTop10ItemsPerStoreOrderedByAmount(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId, Long storeId);

    @Query(value = "SELECT oi.item_id as id, oi.item_name as name, SUM(oi.price * oi.count) as amount, COUNT(*) AS count " +
            "FROM order_item oi JOIN `order` o ON oi.order_id = o.id  " +
            "WHERE o.created_at BETWEEN :fromDate AND :toDate AND oi.owner_id = :ownerId AND o.is_cancelled = 0 " +
            "GROUP BY id, name ORDER BY amount DESC LIMIT 5", nativeQuery = true)
    List<ItemSalePro> findTop5ItemsSales(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate);
/*
    @Query(value = "SELECT oi.item_id as id, oi.item_name as name, SUM(oi.price * oi.count) as totalSales " +
            "FROM order_item oi JOIN `order` o ON oi.order_id = o.id  " +
            "WHERE o.created_at BETWEEN :fromDate AND :toDate " +
            "AND oi.owner_id = :ownerId AND oi.item_id IN :ids AND o.is_cancelled = 0 " +
            "GROUP BY id ORDER BY totalSales DESC LIMIT 5", nativeQuery = true)
    List<ItemSalePro> findTop5ItemsSales(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, List<Long> ids);
    */

    @Query(value = "SELECT i.id as id, i.item_name as name, IFNULL(SUM(oi.price * oi.count),0) as amount, COUNT(*) AS count " +
            "FROM (select i.id, i.item_name from item i where i.id IN :ids) as i " +
            "LEFT JOIN (select oi.item_id, oi.price, oi.count from `order` o join order_item oi on o.id = oi.order_id WHERE o.owner_id = :ownerId AND o.created_at BETWEEN :fromDate AND :toDate AND o.is_cancelled = 0) as oi " +
            "on i.id = oi.item_id group by id", nativeQuery = true)
    List<ItemSalePro> findItemsSales(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, List<Long> ids);

    @Query(value = "SELECT oi.item_id as id, oi.item_name as name, SUM(oi.price * oi.count) as amount, COUNT(*) AS count " +
            "FROM order_item oi JOIN `order` o ON oi.order_id = o.id  " +
            "WHERE o.created_at BETWEEN :fromDate AND :toDate AND oi.owner_id = :ownerId AND oi.store_id = :storeId AND o.is_cancelled = 0 " +
            "GROUP BY id, name ORDER BY amount DESC LIMIT 5", nativeQuery = true)
    List<ItemSalePro> findTop5ItemsSales(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Long storeId);

//    @Query(value = "SELECT oi.item.id as id, oi.itemName as name, SUM(oi.price * oi.count) as totalSales FROM OrderItem oi WHERE oi.item.id IN :ids AND " +
//            "oi.order.createdAt BETWEEN :fromDate AND :toDate AND oi.owner.id = :ownerId AND oi.store.id = :storeId GROUP BY oi.item.id, oi.itemName")
//    List<ItemSalePro> findItemsSales(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Long storeId, List<Long> ids);

    @Query(value = "SELECT oi.item_id as id, oi.item_name as name, SUM(oi.price * oi.count) as amount, COUNT(*) AS count " +
            "FROM order_item oi JOIN `order` o ON o.id = oi.order_id " +
            "WHERE oi.product_group IN :productGroups AND " +
            "o.created_at BETWEEN :fromDate AND :toDate AND " +
            "oi.owner_id = :ownerId AND oi.store_id = :storeId AND o.is_cancelled = 0 " +
            "GROUP BY oi.item_id, oi.item_name " +
            "ORDER BY amount DESC LIMIT 5", nativeQuery = true)
        // TODO: add limit 5 later abdullah
    List<ItemSalePro> findTop5ItemsSales(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Long storeId, List<String> productGroups);

    @Query(value = "SELECT oi.productGroup as name, SUM(oi.price * oi.count) as totalAmount " +
            "FROM OrderItem oi " +
            "WHERE oi.productGroup IN :productGroups AND oi.order.createdAt BETWEEN :fromDate AND :toDate AND oi.owner.id = :ownerId AND oi.order.isCancelled = false " +
            "GROUP BY oi.productGroup")
    List<NameAmountPro> findProductGroupsSales(LocalDateTime fromDate, LocalDateTime toDate, List<String> productGroups, Long ownerId);


    @Query(value = "SELECT oi.item.category as name, SUM(oi.price * oi.count) as totalAmount " +
            "FROM OrderItem oi " +
            "WHERE oi.item.category IN :categories AND oi.order.createdAt BETWEEN :fromDate AND :toDate AND oi.owner.id = :ownerId AND oi.order.isCancelled = false " +
            "GROUP BY oi.item.category")
    List<NameAmountPro> findCategoriesSales(LocalDateTime fromDate, LocalDateTime toDate, List<String> categories, Long ownerId);


    @Query(value = "SELECT oi.item.subcategory as name, SUM(oi.price * oi.count) as totalAmount " +
            "FROM OrderItem oi " +
            "WHERE oi.item.subcategory IN :subcategories AND oi.order.createdAt BETWEEN :fromDate AND :toDate AND oi.owner.id = :ownerId AND oi.order.isCancelled = false " +
            "GROUP BY oi.item.subcategory")
    List<NameAmountPro> findSubcategoriesSales(LocalDateTime fromDate, LocalDateTime toDate, List<String> subcategories, Long ownerId);


    @Query(value = "SELECT oi.product_group as name, SUM(oi.price * oi.count) as totalAmount " +
            "FROM order_item oi JOIN `order` o ON o.id = oi.order_id " +
            "WHERE o.created_at BETWEEN :fromDate AND :toDate AND oi.owner_id = :ownerId AND o.is_cancelled = 0 " +
            "GROUP BY name ORDER BY totalAmount DESC ", nativeQuery = true)
    List<NameAmountPro> findProductGroupsSales(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId);


    @Query(value = "SELECT s.name as name, SUM(oi.price * oi.count) as totalAmount " +
            "FROM order_item oi " +
            "JOIN `order` o ON o.id = oi.order_id " +
            "JOIN store s ON s.id = oi.store_id " +
            "WHERE o.created_at BETWEEN :fromDate AND :toDate " +
            "AND oi.owner_id = :ownerId AND oi.product_group = :productGroup AND oi.store_id IN :storeIds AND o.is_cancelled = 0 " +
            "GROUP BY name ORDER BY totalAmount DESC ", nativeQuery = true)
    List<NameAmountPro> findProductGroupsSales(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId, String productGroup, List<Long> storeIds);

    @Query(value = "SELECT s.name as name, SUM(oi.price * oi.count) as totalAmount " +
            "FROM order_item oi " +
            "JOIN `order` o ON o.id = oi.order_id " +
            "JOIN store s ON s.id = oi.store_id " +
            "JOIN item i ON i.id = oi.item_id " +
            "WHERE o.created_at BETWEEN :fromDate AND :toDate " +
            "AND oi.owner_id = :ownerId AND i.category = :category AND oi.store_id IN :storeIds AND o.is_cancelled = 0 " +
            "GROUP BY name ORDER BY totalAmount DESC ", nativeQuery = true
    )
    List<NameAmountPro> findCategorySales(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId, String category, List<Long> storeIds);

    @Query(value = "SELECT s.name as name, SUM(oi.price * oi.count) as totalAmount " +
            "FROM order_item oi " +
            "JOIN `order` o ON o.id = oi.order_id " +
            "JOIN store s ON s.id = oi.store_id " +
            "JOIN item i ON i.id = oi.item_id " +
            "WHERE o.created_at BETWEEN :fromDate AND :toDate " +
            "AND oi.owner_id = :ownerId AND i.subcategory = :subcategory AND oi.store_id IN :storeIds AND o.is_cancelled = 0 " +
            "GROUP BY name ORDER BY totalAmount DESC LIMIT 10", nativeQuery = true
    )
    List<NameAmountPro> findSubcategorySales(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId, String subcategory, List<Long> storeIds);

    @Query(value = "SELECT oi.product_group as name, SUM(oi.price * oi.count) as totalAmount " +
            "FROM order_item oi JOIN `order` o ON o.id = oi.order_id " +
            "WHERE o.created_at BETWEEN :fromDate AND :toDate AND oi.owner_id = :ownerId AND oi.store_id = :storeId AND o.is_cancelled = 0 " +
            "GROUP BY name ORDER BY totalAmount DESC LIMIT 10", nativeQuery = true)
    List<NameAmountPro> findProductGroupsSales(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId, Long storeId);

    @Query(value = "SELECT oi.product_group as name, SUM(oi.price * oi.count) as totalAmount " +
            "FROM order_item oi JOIN `order` o ON o.id = oi.order_id " +
            "WHERE o.created_at BETWEEN :fromDate AND :toDate AND oi.owner_id = :ownerId AND oi.store_id = :storeId AND oi.product_group IN :productGroups AND o.is_cancelled = 0 " +
            "GROUP BY name ORDER BY totalAmount DESC LIMIT 10", nativeQuery = true)
    List<NameAmountPro> findProductGroupsSales(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId, Long storeId, List<String> productGroups);

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


    @Query(value = "SELECT " +
            "(CASE :timeUnit " +
            "WHEN 'DAY' THEN DATE_FORMAT(o.created_at - INTERVAL TIME_TO_SEC(:dayStartTime) SECOND, CONCAT('%Y-%m-%dT',:dayStartTime)) " +
            "WHEN 'WEEK' THEN DATE_FORMAT(DATE_SUB(o.created_at, INTERVAL IF(DAYOFWEEK(o.created_at) = 1, 6, DAYOFWEEK(o.created_at) - 2) DAY), CONCAT('%Y-%m-%dT',:dayStartTime))" +
            "WHEN 'MONTH' THEN DATE_FORMAT(o.created_at - INTERVAL TIME_TO_SEC(:dayStartTime) SECOND, CONCAT('%Y-%m-01T',:dayStartTime)) " +
            "WHEN 'YEAR' THEN DATE_FORMAT(o.created_at - INTERVAL TIME_TO_SEC(:dayStartTime) SECOND, CONCAT('%Y-01-01T',:dayStartTime)) END) as date," +
            "COUNT(*) AS 'count', " +
            "SUM(total) AS 'amount' " +
            "FROM order_item oi JOIN `order` o ON o.id = oi.order_id " +
            "WHERE o.created_at BETWEEN :fromDate AND :toDate AND oi.owner_id = :ownerId AND oi.store_id = :storeId AND product_group = :productGroup AND o.is_cancelled = 0 " +
            "GROUP BY " +
            "date " +
            "ORDER BY date", nativeQuery = true)
    List<DateCountAmountPro> findProductGroupsSales(String dayStartTime, LocalDateTime fromDate, LocalDateTime toDate, Long ownerId, Long storeId, String productGroup, String timeUnit);

    @Query(value = "SELECT " +
            "(CASE :timeUnit " +
            "WHEN 'DAY' THEN DATE_FORMAT(o.created_at - INTERVAL TIME_TO_SEC(:dayStartTime) SECOND, CONCAT('%Y-%m-%dT',:dayStartTime)) " +
            "WHEN 'WEEK' THEN DATE_FORMAT(DATE_SUB(o.created_at, INTERVAL IF(DAYOFWEEK(o.created_at) = 1, 6, DAYOFWEEK(o.created_at) - 2) DAY), CONCAT('%Y-%m-%dT',:dayStartTime))" +
            "WHEN 'MONTH' THEN DATE_FORMAT(o.created_at - INTERVAL TIME_TO_SEC(:dayStartTime) SECOND, CONCAT('%Y-%m-01T',:dayStartTime)) " +
            "WHEN 'YEAR' THEN DATE_FORMAT(o.created_at - INTERVAL TIME_TO_SEC(:dayStartTime) SECOND, CONCAT('%Y-01-01T',:dayStartTime)) END) as date," +
            "COUNT(*) AS 'count', " +
            "SUM(total) AS 'amount' " +
            "FROM order_item oi JOIN `order` o ON o.id = oi.order_id " +
            "WHERE o.created_at BETWEEN :fromDate AND :toDate AND oi.owner_id = :ownerId AND oi.store_id = :storeId AND product_group = :productGroup AND o.is_cancelled = 0 " +
            "GROUP BY " +
            "date " +
            "ORDER BY date", nativeQuery = true)
    List<DateCountAmountPro> findCategoriesSales(String dayStartTime, LocalDateTime fromDate, LocalDateTime toDate, Long ownerId, Long storeId, String productGroup, String timeUnit);

    @Query(value = "SELECT " +
            "(CASE :timeUnit " +
            "WHEN 'DAY' THEN DATE_FORMAT(o.created_at - INTERVAL TIME_TO_SEC(:dayStartTime) SECOND, CONCAT('%Y-%m-%dT',:dayStartTime)) " +
            "WHEN 'WEEK' THEN DATE_FORMAT(DATE_SUB(o.created_at, INTERVAL IF(DAYOFWEEK(o.created_at) = 1, 6, DAYOFWEEK(o.created_at) - 2) DAY), CONCAT('%Y-%m-%dT',:dayStartTime))" +
            "WHEN 'MONTH' THEN DATE_FORMAT(o.created_at - INTERVAL TIME_TO_SEC(:dayStartTime) SECOND, CONCAT('%Y-%m-01T',:dayStartTime)) " +
            "WHEN 'YEAR' THEN DATE_FORMAT(o.created_at - INTERVAL TIME_TO_SEC(:dayStartTime) SECOND, CONCAT('%Y-01-01T',:dayStartTime)) END) as date," +
            "COUNT(*) AS 'count', " +
            "SUM(total) AS 'amount' " +
            "FROM order_item oi JOIN `order` o ON o.id = oi.order_id " +
            "WHERE o.created_at BETWEEN :fromDate AND :toDate AND oi.owner_id = :ownerId AND oi.store_id = :storeId AND product_group = :productGroup AND o.is_cancelled = 0 " +
            "GROUP BY " +
            "date " +
            "ORDER BY date", nativeQuery = true)
    List<DateCountAmountPro> findSubcategoriesSales(String dayStartTime, LocalDateTime fromDate, LocalDateTime toDate, Long ownerId, Long storeId, String productGroup, String timeUnit);

//
//    @Query(value = "SELECT DATE(created_at) as 'date', COUNT(*) AS 'count', SUM(total) AS 'amount' " +
//            "FROM order_item oi JOIN `order` o ON o.id = oi.order_id " +
//            "WHERE o.created_at BETWEEN :fromDate AND :toDate AND oi.owner_id = :ownerId AND oi.store_id = :storeId AND product_group = :productGroup AND o.is_cancelled = 0 " +
//            "GROUP BY date " +
//            "ORDER BY date", nativeQuery = true)
//    List<DateCountAmountPro> findProductGroupsSalesByDay(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId, Long storeId, String productGroup);
//
//    @Query(value = "SELECT DATE_FORMAT(DATE_SUB(created_at, INTERVAL IF(DAYOFWEEK(created_at) = 1, 6, DAYOFWEEK(created_at) - 2) DAY), '%Y-%m-%d') AS `date`, " +
//            "COUNT(*) AS 'count', SUM(total) AS 'amount' " +
//            "FROM order_item oi JOIN `order` o ON o.id = oi.order_id " +
//            "WHERE o.created_at BETWEEN :fromDate AND :toDate AND oi.owner_id = :ownerId AND oi.store_id = :storeId AND product_group = :productGroup AND o.is_cancelled = 0 " +
//            "GROUP BY date " +
//            "ORDER BY date", nativeQuery = true)
//    List<DateCountAmountPro> findProductGroupsSalesByWeek(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId, Long storeId, String productGroup);
//
//    @Query(value = "SELECT DATE_FORMAT(created_at, '%Y-%m-01') AS 'date', COUNT(*) AS 'count', SUM(total) AS 'amount' " +
//            "FROM order_item oi JOIN `order` o ON o.id = oi.order_id " +
//            "WHERE o.created_at BETWEEN :fromDate AND :toDate AND oi.owner_id = :ownerId AND oi.store_id = :storeId AND product_group = :productGroup AND o.is_cancelled = 0 " +
//            "GROUP BY date " +
//            "ORDER BY date", nativeQuery = true)
//    List<DateCountAmountPro> findProductGroupsSalesByMonth(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId, Long storeId, String productGroup);
//
//    @Query(value = "SELECT DATE_FORMAT(created_at, '%Y-01-01') AS 'date', COUNT(*) AS 'count', SUM(total) AS 'amount' " +
//            "FROM order_item oi JOIN `order` o ON o.id = oi.order_id " +
//            "WHERE o.created_at BETWEEN :fromDate AND :toDate AND oi.owner_id = :ownerId AND oi.store_id = :storeId AND product_group = :productGroup AND o.is_cancelled = 0 " +
//            "GROUP BY date " +
//            "ORDER BY date", nativeQuery = true)
//    List<DateCountAmountPro> findProductGroupsSalesByYear(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId, Long storeId, String productGroup);


    /**
     * Hossam Query findProductGroupSales TimeUnit Switch Case
     */


    @Query(value = "SELECT " +
            "(CASE :timeUnit " +
            "WHEN 'DAY' THEN DATE_FORMAT(o.created_at - INTERVAL TIME_TO_SEC(:dayStartTime) SECOND, CONCAT('%Y-%m-%dT',:dayStartTime)) " +
            "WHEN 'WEEK' THEN DATE_FORMAT(DATE_SUB(o.created_at, INTERVAL IF(DAYOFWEEK(o.created_at) = 1, 6, DAYOFWEEK(o.created_at) - 2) DAY), CONCAT('%Y-%m-%dT',:dayStartTime))" +
            "WHEN 'MONTH' THEN DATE_FORMAT(o.created_at - INTERVAL TIME_TO_SEC(:dayStartTime) SECOND, CONCAT('%Y-%m-01T',:dayStartTime)) " +
            "WHEN 'YEAR' THEN DATE_FORMAT(o.created_at - INTERVAL TIME_TO_SEC(:dayStartTime) SECOND, CONCAT('%Y-01-01T',:dayStartTime)) END) as date," +
            "SUM(oi.price * oi.count) AS 'amount' " +
            "FROM order_item oi JOIN `order` o ON o.id = oi.order_id " +
            "WHERE o.created_at BETWEEN :fromDate AND :toDate AND oi.owner_id = :ownerId AND product_group = :productGroup AND o.is_cancelled = 0 " +
            "GROUP BY " +
            "date " +
            "ORDER BY date", nativeQuery = true)
    List<DateAmountPro> findProductGroupsSalesByTimeUnit(String dayStartTime, LocalDateTime fromDate, LocalDateTime toDate, Long ownerId, String productGroup, String timeUnit);


    @Query(value = "SELECT " +
            "(CASE :timeUnit " +
            "WHEN 'DAY' THEN DATE_FORMAT(o.created_at - INTERVAL TIME_TO_SEC(:dayStartTime) SECOND, CONCAT('%Y-%m-%dT',:dayStartTime)) " +
            "WHEN 'WEEK' THEN DATE_FORMAT(DATE_SUB(o.created_at, INTERVAL IF(DAYOFWEEK(o.created_at) = 1, 6, DAYOFWEEK(o.created_at) - 2) DAY), CONCAT('%Y-%m-%dT',:dayStartTime))" +
            "WHEN 'MONTH' THEN DATE_FORMAT(o.created_at - INTERVAL TIME_TO_SEC(:dayStartTime) SECOND, CONCAT('%Y-%m-01T',:dayStartTime)) " +
            "WHEN 'YEAR' THEN DATE_FORMAT(o.created_at - INTERVAL TIME_TO_SEC(:dayStartTime) SECOND, CONCAT('%Y-01-01T',:dayStartTime)) END) as date," +
            "SUM(oi.price * oi.count) AS 'amount' " +
            "FROM order_item oi JOIN `order` o ON o.id = oi.order_id " +
            "JOIN item i on i.id = oi.item_id " +
            "WHERE o.created_at BETWEEN :fromDate AND :toDate AND oi.owner_id = :ownerId AND i.category = :category AND o.is_cancelled = 0 " +
            "GROUP BY " +
            "date " +
            "ORDER BY date", nativeQuery = true)
    List<DateAmountPro> findCategorySalesByTimeUnit(String dayStartTime, LocalDateTime fromDate, LocalDateTime toDate, Long ownerId, String category, String timeUnit);

    @Query(value = "SELECT " +
            "(CASE :timeUnit " +
            "WHEN 'DAY' THEN DATE_FORMAT(o.created_at - INTERVAL TIME_TO_SEC(:dayStartTime) SECOND, CONCAT('%Y-%m-%dT',:dayStartTime)) " +
            "WHEN 'WEEK' THEN DATE_FORMAT(DATE_SUB(o.created_at, INTERVAL IF(DAYOFWEEK(o.created_at) = 1, 6, DAYOFWEEK(o.created_at) - 2) DAY), CONCAT('%Y-%m-%dT',:dayStartTime))" +
            "WHEN 'MONTH' THEN DATE_FORMAT(o.created_at - INTERVAL TIME_TO_SEC(:dayStartTime) SECOND, CONCAT('%Y-%m-01T',:dayStartTime)) " +
            "WHEN 'YEAR' THEN DATE_FORMAT(o.created_at - INTERVAL TIME_TO_SEC(:dayStartTime) SECOND, CONCAT('%Y-01-01T',:dayStartTime)) END) as date," +
            "SUM(oi.price * oi.count) AS 'amount' " +
            "FROM order_item oi JOIN `order` o ON o.id = oi.order_id " +
            "JOIN item i on i.id = oi.item_id " +
            "WHERE o.created_at BETWEEN :fromDate AND :toDate AND oi.owner_id = :ownerId AND i.subcategory = :subcategory AND o.is_cancelled = 0 " +
            "GROUP BY " +
            "date " +
            "ORDER BY date", nativeQuery = true)
    List<DateAmountPro> findSubcategorySalesByTimeUnit(String dayStartTime, LocalDateTime fromDate, LocalDateTime toDate, Long ownerId, String subcategory, String timeUnit);

//    @Query(value = "SELECT DATE(created_at) as 'date', SUM(total) AS 'amount' " +
//            "FROM order_item oi JOIN `order` o ON o.id = oi.order_id " +
//            "WHERE o.created_at BETWEEN :fromDate AND :toDate AND oi.owner_id = :ownerId AND product_group = :productGroup AND o.is_cancelled = 0 " +
//            "GROUP BY date " +
//            "ORDER BY date", nativeQuery = true)
//    List<DateAmountPro> findProductGroupsSalesByDay(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId, String productGroup);
//
//    @Query(value = "SELECT DATE_FORMAT(DATE_SUB(created_at, INTERVAL IF(DAYOFWEEK(created_at) = 1, 6, DAYOFWEEK(created_at) - 2) DAY), '%Y-%m-%d') AS `date`, " +
//            "SUM(total) AS 'amount' " +
//            "FROM order_item oi JOIN `order` o ON o.id = oi.order_id " +
//            "WHERE o.created_at BETWEEN :fromDate AND :toDate AND oi.owner_id = :ownerId AND product_group = :productGroup AND o.is_cancelled = 0 " +
//            "GROUP BY date " +
//            "ORDER BY date", nativeQuery = true)
//    List<DateAmountPro> findProductGroupsSalesByWeek(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId, String productGroup);
//
//    @Query(value = "SELECT DATE_FORMAT(created_at, '%Y-%m-01') AS 'date',SUM(total) AS 'amount' " +
//            "FROM order_item oi JOIN `order` o ON o.id = oi.order_id " +
//            "WHERE o.created_at BETWEEN :fromDate AND :toDate AND oi.owner_id = :ownerId AND product_group = :productGroup AND o.is_cancelled = 0 " +
//            "GROUP BY date " +
//            "ORDER BY date", nativeQuery = true)
//    List<DateAmountPro> findProductGroupsSalesByMonth(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId, String productGroup);
//
//    @Query(value = "SELECT DATE_FORMAT(created_at, '%Y-01-01') AS 'date', SUM(total) AS 'amount' " +
//            "FROM order_item oi JOIN `order` o ON o.id = oi.order_id " +
//            "WHERE o.created_at BETWEEN :fromDate AND :toDate AND oi.owner_id = :ownerId AND product_group = :productGroup AND o.is_cancelled = 0 " +
//            "GROUP BY date " +
//            "ORDER BY date", nativeQuery = true)
//    List<DateAmountPro> findProductGroupsSalesByYear(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId, String productGroup);

    /***/


    @Query(value = "SELECT oi.product_group AS 'group', oi.item_name AS itemName, SUM(oi.price * oi.count) AS amount " +
            "FROM order_item oi JOIN `order` o ON o.id = oi.order_id " +
            "WHERE o.created_at BETWEEN :fromDate AND :toDate AND oi.owner_id = :ownerId AND oi.product_group = :productGroup AND o.is_cancelled = 0 " +
            "GROUP BY `group`, itemName ORDER BY amount DESC LIMIT 5", nativeQuery = true)
    List<GroupItemAmountPro> findProductGroupsSalesItems(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId, String productGroup);


    @Query(value = "select i.category AS 'group', oi.item_name AS itemName, SUM(oi.price * oi.count) AS amount " +
            "FROM order_item oi JOIN `order` o ON o.id = oi.order_id JOIN item i on  oi.item_id = i.id " +
            "WHERE o.created_at BETWEEN :fromDate AND :toDate AND oi.owner_id = :ownerId AND i.category = :category AND o.is_cancelled = 0 " +
            "GROUP BY 'group', itemName ORDER BY amount DESC LIMIT 5", nativeQuery = true)
    List<GroupItemAmountPro> findCategoriesSalesItems(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId, String category);


    @Query(value = "select i.subcategory as 'group', oi.item_name as itemName, Sum(oi.price * oi.count) as amount " +
            "FROM order_item oi JOIN `order` o ON o.id = oi.order_id JOIN item i on  oi.item_id = i.id " +
            "WHERE o.created_at BETWEEN :fromDate AND :toDate AND oi.owner_id = :ownerId AND i.subcategory = :subcategory AND o.is_cancelled = 0 " +
            "group by 'group', itemName ORDER BY amount DESC LIMIT 5", nativeQuery = true)
    List<GroupItemAmountPro> findSubcategoriesSalesItems(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId, String subcategory);


    @Modifying
    @Query(value = "DELETE FROM order_item WHERE order_id = :orderId ", nativeQuery = true)
    void deleteAllByOrderId(Long orderId);

    @Modifying
    @Query(value = "DELETE FROM order_item WHERE store_id = :storeId ", nativeQuery = true)
    void deleteAllByStoreId(Long storeId);

}
