package com.example.test.util;

import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class DateComparisonUtil {
    public static boolean isMonthsDifferent(Timestamp first, Timestamp second) {
        LocalDateTime dateTime1 = first.toLocalDateTime();
        LocalDateTime dateTime2 = second.toLocalDateTime();
        int year1 = dateTime1.getYear();
        int month1 = dateTime1.getMonthValue();
        int year2 = dateTime2.getYear();
        int month2 = dateTime2.getMonthValue();
        return year1 != year2 || month1 != month2;
    }
}
