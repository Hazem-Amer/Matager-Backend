package com.matager.app.common.helper.page;

import at.orderking.bossApp.common.helper.general.TimeUnit;

import java.text.DecimalFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PageHelper {

    static DecimalFormat decimalFormatter = getDecimalFormat(2);

    public static DecimalFormat getDecimalFormat(int decimalPlaces) {
        StringBuilder pattern = new StringBuilder("0.");
        for (int i = 0; i < decimalPlaces; i++) {
            pattern.append("0");
        }
        return new DecimalFormat(pattern.toString());
    }

    public static Double format(Double value) { // TODO: check case when value thousand separator is comma
        return Double.parseDouble(decimalFormatter.format(value).replace(",", ".").replace("٫", ".")); // Replace comma with dot
    }

    private static String getFullDate(String date) {
        if (date.contains("T") || date.contains(":")) {
            return date;
        }

        return date + "T00:00:00";
    }

    public static String getLabel(String date, TimeUnit timeUnit) {

        date = getFullDate(date);

        switch (timeUnit) {
            case DAY:
                return (String.valueOf(LocalDateTime.parse(date).getDayOfWeek())).substring(0, 3);
            case MONTH:
                return (String.valueOf(LocalDateTime.parse(date).getMonth())).substring(0, 3);
            case YEAR:
                return String.valueOf(LocalDateTime.parse(date).getYear());
            default:
                throw new IllegalArgumentException("Unsupported time unit");
        }
    }

    public static String getLabel(LocalDate from, String date, TimeUnit timeUnit) {

        date = getFullDate(date);

        long weeks = ChronoUnit.WEEKS.between(from.with(DayOfWeek.MONDAY), LocalDateTime.parse(date));

        switch (timeUnit) {
            case WEEK:
                return "Week" + (weeks + 1); // Adding 1 to start from Week 1
            default:
                return getLabel(date, timeUnit);
        }
    }

    public static Map<String, Object> getDatesMap(List<String> dates) {

        Map<String, Object> data = new LinkedHashMap<>();

        dates.forEach((date) -> {
            data.put(date, null);
        });

        return data;
    }

    public static TimeUnit getDefaultTimeUnit(LocalDate fromDate, LocalDate toDate) {
        Period period = Period.between(fromDate, toDate);
        if (period.getYears() > 0) return TimeUnit.YEAR;
        else if (period.getMonths() > 2) return TimeUnit.MONTH;
        else if (period.getMonths() > 1) return TimeUnit.WEEK;
        else if (period.getDays() > 0) return TimeUnit.DAY;

        return TimeUnit.DAY;
    }
}
