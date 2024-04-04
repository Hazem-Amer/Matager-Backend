package com.matager.app.common.query_helper;

import at.orderking.bossApp.auth.AuthenticationFacade;
import at.orderking.bossApp.common.helper.general.StartOfWeek;
import at.orderking.bossApp.common.helper.general.TimeUnit;
import at.orderking.bossApp.owner.settings.OwnerSettingService;
import at.orderking.bossApp.owner.settings.OwnerSettings;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class QueryHelperServiceImpl implements QueryHelperService {

    private final AuthenticationFacade authenticationFacade;
    private final JdbcTemplate jdbc;
    private final OwnerSettingService ownerSettingService;

    @Override
    public List<String> generateDates(LocalDateTime fromDate, LocalDateTime toDate, TimeUnit timeUnit, StartOfWeek startOfWeek) {
        log.info("Generating dates from {} to {} with time unit {} and start of week {}", fromDate, toDate, timeUnit, startOfWeek);
        return jdbc.queryForList("CALL App_Date_GenerateDates(?, ?, ?, ?)", String.class, fromDate, toDate, timeUnit.name(), startOfWeek.name());
    }

    @Override
    public String getDayStartTime() {
        Long ownerId = authenticationFacade.getAuthenticatedUser().getOwner().getId();
        return ownerSettingService.getSettingValue(ownerId, OwnerSettings.DAY_START_TIME).orElse("06:00:00");
    }

    @Override
    public LocalDateTime getDateAtStartTime(LocalDate datetime) {
        String dayStartTime = getDayStartTime();
        return LocalDateTime.of(datetime, LocalTime.parse(dayStartTime));
    }

    @Override
    public LocalDateTime getDateAtEndTime(LocalDate datetime) {
        String dayStartTime = getDayStartTime();
        return LocalDateTime.of(datetime, LocalTime.parse(dayStartTime)).plusDays(1);
    }


    @Override
    public LocalDateTime getDateAtStartTime(LocalDateTime datetime) {
        return getDateAtStartTime(datetime.toLocalDate());
    }

    @Override
    public LocalDateTime getDateAtEndTime(LocalDateTime datetime) {
        return getDateAtEndTime(datetime.toLocalDate());
    }
}
