package com.example.test.service;

import com.example.test.enums.Currency;
import com.example.test.model.exchangerate.ExchangeRate;

import java.math.BigDecimal;
import java.util.List;

public interface ExchangeRateService {
    List<ExchangeRate> getAll();
    BigDecimal getRateByCurrency(Currency currencyFrom, Currency currencyTo);
}
