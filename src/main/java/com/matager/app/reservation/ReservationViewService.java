package com.matager.app.reservation;

import at.orderking.bossApp.common.helper.general.TimeUnit;
import at.orderking.bossApp.repository.dto.general.DateNameCountDto;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationViewService {

    Integer getReservationsCount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate);

    Integer getReservationsCount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Long storeId);

    Integer getGuestsCount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate);

    Integer getGuestsCount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Long storeId);

    ////    List<StoreReservation> getReservationsCount(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId);

    List<DateNameCountDto> getReservationsCount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, TimeUnit timeUnit, List<Long> storeId);
}
