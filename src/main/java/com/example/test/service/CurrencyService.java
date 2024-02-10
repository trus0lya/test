package com.example.test.service;

import com.example.test.enums.Currency;

import java.math.BigDecimal;

public interface CurrencyService {
    BigDecimal convert(BigDecimal amount, Currency currencyFrom, Currency currencyTo);
}
