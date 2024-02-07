package com.example.test.service;

import com.example.test.entity.ExchangeRateEntity;
import com.example.test.enums.Currency;
import com.example.test.model.ApiResponse;
import com.example.test.repository.ExchangeRateRepository;
import com.example.test.util.TwelveDataClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Service
public class ExchangeRateService {
    private final ExchangeRateRepository exchangeRateRepository;



    @Autowired
    public ExchangeRateService(ExchangeRateRepository exchangeRateRepository) {
        this.exchangeRateRepository = exchangeRateRepository;
    }

    @Value("${apikey}")
    private String apiKey;


    @Scheduled(cron = "0 43 13 * * ?", zone = "UTC")
    public void init() {
        TwelveDataClient twelveDataClient = new TwelveDataClient();
        List<String> currencyPairs = Currency.generateCurrencyPairs();
        for (String pair : currencyPairs) {
            String url = String.format("https://api.twelvedata.com/exchange_rate?symbol=%s&apikey=%s", pair, apiKey);
            ApiResponse response = twelveDataClient.getExchangeRate(url, ApiResponse.class);
            if(response != null) {
                ExchangeRateEntity entity = new ExchangeRateEntity();
                String[] currencies = pair.split("/");
                entity.setCurrencyFrom(Currency.valueOf(currencies[0]));
                entity.setCurrencyTo(Currency.valueOf(currencies[1]));
                entity.setRate(BigDecimal.valueOf(response.getRate()));
                entity.setDateOfUpdate(new Timestamp(System.currentTimeMillis()));
                exchangeRateRepository.save(entity);
            }
        }
    }

    public List<ExchangeRateEntity> getAll() {
        return exchangeRateRepository.findAll();
    }
}
