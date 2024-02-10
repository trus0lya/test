package com.example.test.util;

import com.example.test.enums.Currency;

import java.util.ArrayList;
import java.util.List;

public class CurrencyUtils {
    public static List<String> generateCurrencyPairs() {
        Currency[] currencies = Currency.values();
        List<String> pairsList = new ArrayList<>();
        for (int i = 0; i < currencies.length; i++) {
            for (int j = 0; j < currencies.length; j++) {
                if (i != j) {
                    String pair = currencies[i].toString() + "/" + currencies[j].toString();
                    pairsList.add(pair);
                }
            }
        }
        return pairsList;
    }
}
