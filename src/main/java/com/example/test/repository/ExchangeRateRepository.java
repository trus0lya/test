package com.example.test.repository;

import com.example.test.entity.ExchangeRateEntity;
import com.example.test.entity.ExchangeRateEntityPK;
import com.example.test.enums.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRateEntity, ExchangeRateEntityPK> {
     @Query("SELECT e.rate FROM ExchangeRateEntity e WHERE e.currencyFrom = ?1 AND e.currencyTo = ?2")
     BigDecimal getRateByCurrency(Currency currencyFrom, Currency currencyTo);
}
