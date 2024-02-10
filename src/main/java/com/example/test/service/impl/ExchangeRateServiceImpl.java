package com.example.test.service.impl;

import com.example.test.entity.ExchangeRateEntity;
import com.example.test.enums.Currency;
import com.example.test.repository.ExchangeRateRepository;
import com.example.test.service.ExchangeRateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {
    private final ExchangeRateRepository exchangeRateRepository;

    @Autowired
    public ExchangeRateServiceImpl(ExchangeRateRepository exchangeRateRepository) {
        this.exchangeRateRepository = exchangeRateRepository;
    }

    @Override
    public List<ExchangeRateEntity> getAll() {
        return exchangeRateRepository.findAll();
    }

    @Override
    public BigDecimal getRateByCurrency(Currency currencyFrom, Currency currencyTo) {
        return exchangeRateRepository.getRateByCurrency(currencyFrom, currencyTo);
    }
}
