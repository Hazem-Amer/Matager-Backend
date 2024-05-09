package com.matager.app.reservation;

import com.matager.app.common.helper.general.DateHelper;

import com.matager.app.common.statistics.dto.general.DateNameCountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationViewServiceImpl implements ReservationViewService {

    private final ReservationRepository reservationRepository;



    @Override
    public Integer getReservationsCount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate) {
        return reservationRepository.getReservationsCount(fromDate, toDate, ownerId);
    }

    @Override
    public Integer getReservationsCount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Long storeId) {
        return reservationRepository.getReservationsCount(fromDate, toDate, ownerId, storeId);
    }

    @Override
    public Integer getGuestsCount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate) {
        return reservationRepository.getPersonsCount(fromDate, toDate, ownerId);
    }

    @Override
    public Integer getGuestsCount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, Long storeId) {
        return reservationRepository.getReservationsCount(fromDate, toDate, ownerId, storeId);
    }

}
