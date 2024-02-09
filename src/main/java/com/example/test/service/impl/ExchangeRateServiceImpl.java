package com.example.test.service.impl;

import com.example.test.entity.ExchangeRateEntity;
import com.example.test.enums.Currency;
import com.example.test.model.ExchangeRateResponse;
import com.example.test.repository.ExchangeRateRepository;
import com.example.test.service.ExchangeRateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {
    @Value("${apikey}")
    private String apiKey;
    private final ExchangeRateRepository exchangeRateRepository;

    private final RestTemplate restTemplate;

    @Autowired
    public ExchangeRateServiceImpl(ExchangeRateRepository exchangeRateRepository, RestTemplate restTemplate) {
        this.exchangeRateRepository = exchangeRateRepository;
        this.restTemplate = restTemplate;
    }

    private List<String> generateCurrencyPairs() {
        Currency[] currencies = Currency.values();
        List<String> pairsList = new ArrayList<>();
        for (int i = 0; i < currencies.length; i++) {
            for (int j = 0; j < currencies.length; j++) {
                if (i != j) {
                    String pair = currencies[i].toString() + "/" + currencies[j].toString();
                    pairsList.add(pair);
                }
            }
        }
        return pairsList;
    }

    private ExchangeRateResponse getExchangeRate(String url) {
        try {
            String jsonString = restTemplate.getForObject(url, String.class);
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(jsonString, ExchangeRateResponse.class);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    @Scheduled(cron = "${cron}", zone = "UTC")
    public void init() {
        List<String> currencyPairs = generateCurrencyPairs();
        for (String pair : currencyPairs) {
            String url = String.format("https://api.twelvedata.com/exchange_rate?symbol=%s&apikey=%s", pair, apiKey);
            ExchangeRateResponse response = getExchangeRate(url);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String timestamp = dateFormat.format(new Date());
            if (response != null) {
                ExchangeRateEntity entity = new ExchangeRateEntity();
                String[] currencies = pair.split("/");
                entity.setCurrencyFrom(Currency.valueOf(currencies[0]));
                entity.setCurrencyTo(Currency.valueOf(currencies[1]));
                entity.setRate(BigDecimal.valueOf(response.getRate()));
                entity.setDateOfUpdate(new Timestamp(System.currentTimeMillis()));
                exchangeRateRepository.save(entity);
                log.info("{} the exchange rate was successfully received {} -> {}: {}", timestamp, currencies[0], currencies[1], response.getRate());
            } else {
                log.warn("{} the exchange rate for the currency pair could not be obtained {}", timestamp, pair);
            }
        }

    }
    @Override
    public BigDecimal convert(BigDecimal amount, Currency currencyFrom, Currency currencyTo) {
        return currencyFrom.equals(currencyTo) ?
                amount : exchangeRateRepository.getRateByCurrency(currencyFrom, currencyTo).multiply(amount);
    }
    @Override
    public List<ExchangeRateEntity> getAll() {
        return exchangeRateRepository.findAll();
    }
}
