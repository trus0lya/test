package com.example.test.service;

import com.example.test.entity.ExchangeRateEntity;

import java.util.List;

public interface ExchangeRateService {
    List<ExchangeRateEntity> getAll();
    void init();
}
