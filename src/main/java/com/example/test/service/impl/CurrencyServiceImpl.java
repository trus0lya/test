package com.example.test.service.impl;

import com.example.test.enums.Currency;
import com.example.test.repository.ExchangeRateRepository;
import com.example.test.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CurrencyServiceImpl implements CurrencyService {
    private final ExchangeRateRepository exchangeRateRepository;
    @Autowired
    public CurrencyServiceImpl(ExchangeRateRepository exchangeRateRepository) {
        this.exchangeRateRepository = exchangeRateRepository;
    }
    @Override
    public BigDecimal convert(BigDecimal amount, Currency currencyFrom, Currency currencyTo) {
        return currencyFrom.equals(currencyTo)
                ? amount :
                exchangeRateRepository.getRateByCurrency(currencyFrom, currencyTo).multiply(amount);
    }
}
