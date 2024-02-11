package com.example.test.service;

import com.example.test.model.exchangerate.ExchangeRateResponse;

public interface CurrencyIntegrationService {
    ExchangeRateResponse getExchangeRate(String url);
}
