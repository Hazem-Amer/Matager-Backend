package com.matager.app.common.helper.general;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateHelper {

    public static String getDateFromDateTime(String datetime, String format) {
        LocalDateTime localDateTime = LocalDateTime.parse(datetime);
        return localDateTime.format(DateTimeFormatter.ofPattern(format));
    }

    public static String getDateFromDateTime(String datetime) {
        return getDateFromDateTime(datetime, "yyyy-MM-dd");
    }
}
