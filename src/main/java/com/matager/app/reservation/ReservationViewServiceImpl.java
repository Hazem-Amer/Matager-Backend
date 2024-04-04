package com.matager.app.reservation;

import at.orderking.bossApp.common.helper.general.DateHelper;
import at.orderking.bossApp.common.helper.general.TimeUnit;
import at.orderking.bossApp.common.query_helper.QueryHelperService;
import at.orderking.bossApp.repository.dto.general.DateNameCountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationViewServiceImpl implements ReservationViewService {

    private final ReservationRepository reservationRepository;
    private final QueryHelperService queryHelperService;


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

    @Override
    public List<DateNameCountDto> getReservationsCount(Long ownerId, LocalDateTime fromDate, LocalDateTime toDate, TimeUnit timeUnit, List<Long> storeId) {
        return reservationRepository.getReservationsCount(queryHelperService.getDayStartTime(), fromDate, toDate, storeId, ownerId, timeUnit.name()).stream()
                .map(r -> new DateNameCountDto(DateHelper.getDateFromDateTime(r.getDate()), r.getName(), r.getTotalCount())).collect(Collectors.toList());
    }

}
