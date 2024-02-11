package com.example.test.service.impl;

import com.example.test.enums.Currency;
import com.example.test.mapper.impl.ExchangeRateMapper;
import com.example.test.model.exchangerate.ExchangeRate;
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

    private final ExchangeRateMapper exchangeRateMapper;

    @Autowired
    public ExchangeRateServiceImpl(ExchangeRateRepository exchangeRateRepository,
                                   ExchangeRateMapper exchangeRateMapper) {
        this.exchangeRateRepository = exchangeRateRepository;
        this.exchangeRateMapper = exchangeRateMapper;
    }

    @Override
    public List<ExchangeRate> getAll() {
        return exchangeRateRepository.findAll().stream()
                .map(exchangeRateMapper::convertEntityToDTO)
                .toList();
    }

    @Override
    public BigDecimal getRateByCurrency(Currency currencyFrom, Currency currencyTo) {
        return exchangeRateRepository.getRateByCurrency(currencyFrom, currencyTo);
    }
}
