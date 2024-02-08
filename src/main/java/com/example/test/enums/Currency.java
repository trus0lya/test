package com.example.test.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.ArrayList;
import java.util.List;

public enum Currency {
    RUB("RUB"),
    KZT("KZT"),
    USD("USD");

    private final String currency;

    Currency(String currency) {
        this.currency = currency;
    }

    public String getCurrency() {
        return currency;
    }

    public static List<String> generateCurrencyPairs() {
        Currency[] currencies = Currency.values();
        List<String> pairsList = new ArrayList<>();
        for (int i = 0; i < currencies.length; i++) {
            for (int j = 0; j < currencies.length; j++) {
                if (i != j) {
                    String pair = currencies[i].getCurrency() + "/" + currencies[j].getCurrency();
                    pairsList.add(pair);
                }
            }
        }
        return pairsList;
    }

    @JsonCreator
    public static Currency forValue(String value) {
        for (Currency currency : Currency.values()) {
            if (currency.currency.equalsIgnoreCase(value)) {
                return currency;
            }
        }
        throw new IllegalArgumentException("Unknown currency: " + value);
    }
}
