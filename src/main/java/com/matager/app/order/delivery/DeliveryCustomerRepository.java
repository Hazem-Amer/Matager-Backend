package com.matager.app.order.delivery;

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


}
