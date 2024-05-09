package com.matager.app.reservation;

import com.matager.app.common.statistics.dto.general.DateNameCountDto;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationViewService {

    Integer getReservationsCount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate);

    Integer getReservationsCount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Long storeId);

    Integer getGuestsCount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate);

    Integer getGuestsCount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Long storeId);

    ////    List<StoreReservation> getReservationsCount(LocalDateTime fromDate, LocalDateTime toDate, Long ownerId);

}
