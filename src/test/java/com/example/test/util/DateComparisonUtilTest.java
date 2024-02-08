package com.example.test.util;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class DateComparisonUtilTest {

    @Test
    void sameDay() {
        LocalDateTime now = LocalDateTime.now();
        Timestamp timestamp1 = Timestamp.valueOf(now);
        Timestamp timestamp2 = Timestamp.valueOf(now);
        boolean result = DateComparisonUtil.areTheMonthsDifferent(timestamp1, timestamp2);
        Assertions.assertFalse(result);
    }

    @Test
    void differentMonth() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime lastMonth = now.minusMonths(1);
        Timestamp timestamp1 = Timestamp.valueOf(now);
        Timestamp timestamp2 = Timestamp.valueOf(lastMonth);
        boolean result = DateComparisonUtil.areTheMonthsDifferent(timestamp1, timestamp2);
        Assertions.assertTrue(result);
    }

    @Test
    void differentYear() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime lastYear = now.minusYears(1);
        Timestamp timestamp1 = Timestamp.valueOf(now);
        Timestamp timestamp2 = Timestamp.valueOf(lastYear);
        boolean result = DateComparisonUtil.areTheMonthsDifferent(timestamp1, timestamp2);
        Assertions.assertTrue(result);
    }
}
