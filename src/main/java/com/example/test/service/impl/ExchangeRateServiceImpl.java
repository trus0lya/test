package com.example.test.service.impl;

import com.example.test.entity.ExchangeRateEntity;
import com.example.test.enums.Currency;
import com.example.test.model.ExchangeRateResponse;
import com.example.test.repository.ExchangeRateRepository;
import com.example.test.service.ExchangeRateService;
import com.example.test.util.TwelveDataUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {
    private final ExchangeRateRepository exchangeRateRepository;

    @Autowired
    public ExchangeRateServiceImpl(ExchangeRateRepository exchangeRateRepository) {
        this.exchangeRateRepository = exchangeRateRepository;
    }

    @Value("${apikey}")
    private String apiKey;

    @Override
    @Scheduled(cron = "${cron}", zone = "UTC")
    public void init() {
        List<String> currencyPairs = Currency.generateCurrencyPairs();
        for (String pair : currencyPairs) {
            String url = String.format("https://api.twelvedata.com/exchange_rate?symbol=%s&apikey=%s", pair, apiKey);
            ExchangeRateResponse response = TwelveDataUtil.getExchangeRate(url);
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
    public List<ExchangeRateEntity> getAll() {
        return exchangeRateRepository.findAll();
    }
}
