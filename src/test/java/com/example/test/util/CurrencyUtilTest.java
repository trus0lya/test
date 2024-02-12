package com.example.test.util;

import com.example.test.enums.Currency;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CurrencyUtilTest {
    @Test
    public void testGenerateCurrencyPairs() {
        List<String> pairsList = CurrencyUtil.generateCurrencyPairs();

        int length = Currency.values().length * (Currency.values().length - 1);

        assertEquals(length, pairsList.size());

        assertTrue(pairsList.contains("RUB/KZT"));
        assertTrue(pairsList.contains("RUB/USD"));
        assertTrue(pairsList.contains("KZT/RUB"));
        assertTrue(pairsList.contains("KZT/USD"));
        assertTrue(pairsList.contains("USD/RUB"));
        assertTrue(pairsList.contains("USD/KZT"));

        assertFalse(pairsList.contains("USD/USD"));
        assertFalse(pairsList.contains("RUB/RUB"));
        assertFalse(pairsList.contains("KZT/KZT"));
    }
}
