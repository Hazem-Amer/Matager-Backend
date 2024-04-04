/*
 * @Omar Elbeltagui
 */

package com.matager.app.reservation;

import at.orderking.bossApp.repository.projection.StoreReservationPro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Optional<Reservation> findByStoreIdAndReservationNo(Long storeId, Long reservationNo);

    boolean existsByStoreIdAndReservationNo(Long storeId, Long reservationNo);

    @Query(value = "SELECT IFNULL(COUNT(*),0) FROM reservation WHERE owner_id = :ownerId AND ((`from` BETWEEN :fromDate AND :toDate) OR (`to` BETWEEN :fromDate AND :toDate))", nativeQuery = true)
    Integer getReservationsCount(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId);

    @Query(value = "SELECT IFNULL(COUNT(*),0) FROM reservation WHERE owner_id = :ownerId AND ((`from` BETWEEN :fromDate AND :toDate) OR (`to` BETWEEN :fromDate AND :toDate)) AND store_id = :storeId", nativeQuery = true)
    Integer getReservationsCount(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId, Long storeId);


    @Query(value = "SELECT IFNULL(SUM(persons),0) FROM reservation WHERE owner_id = :ownerId AND ((`from` BETWEEN :fromDate AND :toDate) OR (`to` BETWEEN :fromDate AND :toDate))", nativeQuery = true)
    Integer getPersonsCount(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId);

    @Query(value = "SELECT IFNULL(SUM(persons),0) FROM reservation WHERE owner_id = :ownerId AND ((`from` BETWEEN :fromDate AND :toDate) OR (`to` BETWEEN :fromDate AND :toDate)) AND store_id = :storeId", nativeQuery = true)
    Integer getPersonsCount(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId, Long storeId);


    @Query(value = "SELECT " +
            "(CASE :timeUnit " +
            "WHEN 'DAY' THEN DATE_FORMAT(`from` - INTERVAL TIME_TO_SEC(:dayStartTime) SECOND, CONCAT('%Y-%m-%dT',:dayStartTime)) " +
            "WHEN 'WEEK' THEN DATE_FORMAT(DATE_SUB(`from`, INTERVAL IF(DAYOFWEEK(`from`) = 1, 6, DAYOFWEEK(`from`) - 2) DAY), CONCAT('%Y-%m-%dT',:dayStartTime))" +
            "WHEN 'MONTH' THEN DATE_FORMAT(`from` - INTERVAL TIME_TO_SEC(:dayStartTime) SECOND, CONCAT('%Y-%m-01T',:dayStartTime)) " +
            "WHEN 'YEAR' THEN DATE_FORMAT(`from` - INTERVAL TIME_TO_SEC(:dayStartTime) SECOND, CONCAT('%Y-01-01T',:dayStartTime)) END) as date," +
            "s.name, COUNT(*) as totalCount FROM reservation r " +
            "JOIN store s ON r.store_id = s.id AND r.owner_id = s.owner_id " +
            "WHERE r.owner_id = :ownerId AND r.store_id IN :storeIds AND ((`from` BETWEEN :fromDate AND :toDate) OR (`to` BETWEEN :fromDate AND :toDate)) " +
            "GROUP BY date, s.name ORDER BY date", nativeQuery = true)
    List<StoreReservationPro> getReservationsCount(String dayStartTime, LocalDateTime fromDate, LocalDateTime toDate, List<Long> storeIds, Long ownerId, String timeUnit);
//
//    @Query(value = "SELECT DATE(`from`) as date, s.name, COUNT(*) as totalCount FROM reservation r " +
//            "JOIN store s ON r.store_id = s.id AND r.owner_id = s.owner_id " +
//            "WHERE r.owner_id = :ownerId AND r.store_id IN :storeIds AND ((`from` BETWEEN :fromDate AND :toDate) OR (`to` BETWEEN :fromDate AND :toDate)) " +
//            "GROUP BY date, s.name ORDER BY date", nativeQuery = true)
//    List<StoreReservationPro> getReservationsCountByDay(LocalDateTime fromDate, LocalDateTime toDate, List<Long> storeIds, Long ownerId);
//
//
//    @Query(value = "SELECT DATE_FORMAT(DATE_SUB(`from`, INTERVAL IF(DAYOFWEEK(`from`) = 1, 6, DAYOFWEEK(`from`) - 2) DAY), '%Y-%m-%d') AS date, " +
//            "s.name, COUNT(*) as totalCount FROM reservation r " +
//            "JOIN store s ON r.store_id = s.id AND r.owner_id = s.owner_id " +
//            "WHERE r.owner_id = :ownerId AND r.store_id IN :storeIds AND ((`from` BETWEEN :fromDate AND :toDate) OR (`to` BETWEEN :fromDate AND :toDate)) " +
//            "GROUP BY date, s.name ORDER BY date", nativeQuery = true)
//    List<StoreReservationPro> getReservationsCountByWeek(LocalDateTime fromDate, LocalDateTime toDate, List<Long> storeIds, Long ownerId);
//
//
//    @Query(value = "SELECT CASE WHEN DAY(`from`) != 1 THEN DATE_FORMAT(`from`, '%Y-%m-01') " +
//            "ELSE DATE(`from`) END AS date, s.name, COUNT(*) as totalCount FROM reservation r " +
//            "JOIN store s ON r.store_id = s.id AND r.owner_id = s.owner_id " +
//            "WHERE r.owner_id = :ownerId AND r.store_id IN :storeIds AND ((`from` BETWEEN :fromDate AND :toDate) OR (`to` BETWEEN :fromDate AND :toDate)) " +
//            "GROUP BY date, s.name ORDER BY date", nativeQuery = true)
//    List<StoreReservationPro> getReservationsCountByMonth(LocalDateTime fromDate, LocalDateTime toDate, List<Long> storeIds, Long ownerId);
//
//
//    @Query(value = "SELECT CASE WHEN (DAY(`from`) != 1 OR MONTH(`from`) != 1) THEN DATE_FORMAT(`from`, '%Y-01-01') " +
//            "ELSE DATE(`from`) END AS date, s.name, COUNT(*) as totalCount FROM reservation r " +
//            "JOIN store s ON r.store_id = s.id AND r.owner_id = s.owner_id " +
//            "WHERE r.owner_id = :ownerId AND r.store_id IN :storeIds AND ((`from` BETWEEN :fromDate AND :toDate) OR (`to` BETWEEN :fromDate AND :toDate)) " +
//            "GROUP BY date, s.name ORDER BY date", nativeQuery = true)
//    List<StoreReservationPro> getReservationsCountByYear(LocalDateTime fromDate, LocalDateTime toDate, List<Long> storeIds, Long ownerId);


}
