package com.matager.app.common.query_helper;

import at.orderking.bossApp.common.helper.general.StartOfWeek;
import at.orderking.bossApp.common.helper.general.TimeUnit;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface QueryHelperService {

    List<String> generateDates(LocalDateTime fromDate, LocalDateTime toDate, TimeUnit timeUnit, StartOfWeek startOfWeek);

    String getDayStartTime();

    LocalDateTime getDateAtStartTime(LocalDate date);

    LocalDateTime getDateAtEndTime(LocalDate date);

    LocalDateTime getDateAtStartTime(LocalDateTime datetime);

    LocalDateTime getDateAtEndTime(LocalDateTime datetime);
}
