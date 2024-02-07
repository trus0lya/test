package com.example.test.util;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class DateComparison {

    public DateComparison() {
    }

    public boolean areTheMonthsDifferent(Timestamp timestamp1, Timestamp timestamp2) {
        LocalDateTime dateTime1 = timestamp1.toLocalDateTime();
        LocalDateTime dateTime2 = timestamp2.toLocalDateTime();
        int year1 = dateTime1.getYear();
        int month1 = dateTime1.getMonthValue();
        int year2 = dateTime2.getYear();
        int month2 = dateTime2.getMonthValue();
        return year1 != year2 || month1 != month2;
    }
}
