package com.example.test.controller;

import com.example.test.entity.ExchangeRateEntity;
import com.example.test.mapper.impl.ExchangeRateMapper;
import com.example.test.model.ExchangeRate;
import com.example.test.service.ExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/rates")
public class ExchangeRateController {
    private final ExchangeRateService exchangeRateService;

    private final ExchangeRateMapper exchangeRateMapper;

    @Autowired
    public ExchangeRateController(ExchangeRateService exchangeRateService,
                                  ExchangeRateMapper exchangeRateMapper) {
        this.exchangeRateService = exchangeRateService;
        this.exchangeRateMapper = exchangeRateMapper;
    }

    @GetMapping
    public ResponseEntity<List<ExchangeRate>> getAll() {
        List<ExchangeRateEntity> entities = exchangeRateService.getAll();
        List<ExchangeRate> list = entities.stream()
                .map(exchangeRateMapper::convertEntityToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
