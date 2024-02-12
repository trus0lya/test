package com.example.test.service.impl;

import com.example.test.entity.ExchangeRateEntity;
import com.example.test.enums.Currency;
import com.example.test.model.exchangerate.ExchangeRateResponse;
import com.example.test.repository.ExchangeRateRepository;
import com.example.test.service.CurrencyIntegrationService;
import com.example.test.service.CurrencyRateUpdateService;
import com.example.test.util.CurrencyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class CurrencyRateUpdateServiceImpl implements CurrencyRateUpdateService {

    private final CurrencyIntegrationService currencyIntegrationService;

    private final ExchangeRateRepository exchangeRateRepository;

    @Autowired
    private CurrencyRateUpdateServiceImpl(CurrencyIntegrationService currencyIntegrationService,
                                      ExchangeRateRepository exchangeRateRepository) {
        this.currencyIntegrationService = currencyIntegrationService;
        this.exchangeRateRepository = exchangeRateRepository;
    }

    @Override
    @Scheduled(cron = "${dailyCurrencyUpdateCron}", zone = "UTC")
    public void updateDailyExchangeRates() {
        List<String> currencyPairs = CurrencyUtil.generateCurrencyPairs();
        for (String pair : currencyPairs) {
            ExchangeRateResponse response = currencyIntegrationService.getExchangeRate(pair);
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
}
