package com.example.test.service;

import com.example.test.entity.ExchangeRateEntity;
import com.example.test.enums.Currency;

import java.math.BigDecimal;
import java.util.List;

public interface ExchangeRateService {
    List<ExchangeRateEntity> getAll();
    BigDecimal convert(BigDecimal amount, Currency currencyFrom, Currency currencyTo);
    void init();
}
