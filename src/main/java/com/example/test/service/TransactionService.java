package com.example.test.service;

import com.example.test.entity.LimitsEntity;
import com.example.test.entity.TransactionsEntity;
import com.example.test.enums.Currency;
import com.example.test.enums.ExpenseCategory;
import com.example.test.repository.ExchangeRateRepository;
import com.example.test.repository.LimitRepository;
import com.example.test.repository.TransactionRepository;
import com.example.test.util.DateComparison;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final ExchangeRateRepository exchangeRateRepository;
    private final LimitRepository limitRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository,
                              ExchangeRateRepository exchangeRateRepository,
                              LimitRepository limitRepository) {
        this.transactionRepository = transactionRepository;
        this.exchangeRateRepository = exchangeRateRepository;
        this.limitRepository = limitRepository;
    }

    public List<TransactionsEntity> getAll() {
        return transactionRepository.findAll();
    }


    public List<TransactionsEntity> getExceededLimits(Long accountNumber) {
        return transactionRepository.getByAccountNumberWithExceededLimit(accountNumber);
    }

    @Transactional
    public void transaction (
            Long accountFrom, Long accountTo,
            ExpenseCategory category, BigDecimal amount,
            Currency currency
    ) {
        TransactionsEntity transactionsEntity = new TransactionsEntity();
        transactionsEntity.setAccountNumFrom(accountFrom);
        transactionsEntity.setAccountNumTo(accountTo);
        transactionsEntity.setExpenseCategory(category);
        transactionsEntity.setAmount(amount);
        transactionsEntity.setCurrency(currency);
        transactionsEntity.setDateTime(new Timestamp(System.currentTimeMillis()));
        BigDecimal amountUSD = !currency.getCurrency().equals("USD")
                ? exchangeRateRepository.getRateByCurrency(currency, Currency.USD).multiply(amount)
                : amount;
        List<LimitsEntity> limits = limitRepository.findLatestByExpenseCategoryAndAccountNumberForUpdate(category, accountFrom);
        if (!limits.isEmpty()) {
            LimitsEntity limit = limits.get(0);
            Timestamp updateDateTimestamp = limit.getUpdateDate();
            Timestamp nowTimestamp = new Timestamp(System.currentTimeMillis());
            DateComparison dateComparison = new DateComparison();
            if (dateComparison.areTheMonthsDifferent(updateDateTimestamp, nowTimestamp)) {
                limit.setRemainsBeforeExceed(limit.getLimitUsd().subtract(amountUSD));
                limit.setUpdateDate(new Timestamp(System.currentTimeMillis()));
            } else {
                BigDecimal newRemainsBeforeExceed = limit.getRemainsBeforeExceed().subtract(amountUSD);
                limit.setRemainsBeforeExceed(newRemainsBeforeExceed);
                if (newRemainsBeforeExceed.compareTo(BigDecimal.ZERO) < 0) {
                    transactionsEntity.setLimitsEntity(limit);
                }
            }
            limitRepository.save(limit);
        }
        transactionRepository.save(transactionsEntity);
    }
}
