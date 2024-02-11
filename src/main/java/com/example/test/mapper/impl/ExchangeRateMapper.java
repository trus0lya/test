package com.example.test.mapper.impl;

import com.example.test.entity.ExchangeRateEntity;
import com.example.test.mapper.Mapper;
import com.example.test.model.exchangerate.ExchangeRate;
import org.springframework.stereotype.Component;


@Component
public class ExchangeRateMapper implements Mapper<ExchangeRate, ExchangeRateEntity> {
    @Override
    public ExchangeRate convertEntityToDTO(ExchangeRateEntity exchangeRateEntity) {
        return new ExchangeRate(exchangeRateEntity.getCurrencyFrom(),
                exchangeRateEntity.getCurrencyTo(),
                exchangeRateEntity.getRate(),
                exchangeRateEntity.getDateOfUpdate());
    }
}
