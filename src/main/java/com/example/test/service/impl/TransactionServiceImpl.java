package com.example.test.service.impl;

import com.example.test.entity.LimitsEntity;
import com.example.test.entity.TransactionsEntity;
import com.example.test.enums.Currency;
import com.example.test.enums.ExpenseCategory;
import com.example.test.repository.ExchangeRateRepository;
import com.example.test.repository.LimitRepository;
import com.example.test.repository.TransactionRepository;
import com.example.test.service.TransactionService;
import com.example.test.util.DateComparisonUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final ExchangeRateRepository exchangeRateRepository;
    private final LimitRepository limitRepository;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository,
                                  ExchangeRateRepository exchangeRateRepository,
                                  LimitRepository limitRepository) {
        this.transactionRepository = transactionRepository;
        this.exchangeRateRepository = exchangeRateRepository;
        this.limitRepository = limitRepository;
    }

    @Override
    public List<TransactionsEntity> getAll() {
        return transactionRepository.findAll();
    }

    @Override
    public List<TransactionsEntity> getExceededLimits(Long accountNumber) {
        return transactionRepository.getByAccountNumberWithExceededLimit(accountNumber);
    }

    @Override
    @Transactional
    public void transaction(
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

        BigDecimal amountUSD = currency.getCurrency().equals("USD") ? amount
                : exchangeRateRepository.getRateByCurrency(currency, Currency.USD).multiply(amount);
        LimitsEntity limit = limitRepository.findLatestByExpenseCategoryAndAccountNumber(category.getCategory(), accountFrom);

        if (limit != null) {
            Timestamp updateDateTimestamp = limit.getUpdateDate();
            Timestamp nowTimestamp = new Timestamp(System.currentTimeMillis());
            BigDecimal newRemainsBeforeExceed;
            if (DateComparisonUtil.areTheMonthsDifferent(updateDateTimestamp, nowTimestamp)) {
                newRemainsBeforeExceed = limit.getLimitUsd().subtract(amountUSD);
                limit.setUpdateDate(new Timestamp(System.currentTimeMillis()));
            } else {
                newRemainsBeforeExceed = limit.getRemainsBeforeExceed().subtract(amountUSD);
            }
            limit.setRemainsBeforeExceed(newRemainsBeforeExceed);
            if (newRemainsBeforeExceed.compareTo(BigDecimal.ZERO) < 0) {
                transactionsEntity.setLimitsEntity(limit);
            }
            limitRepository.save(limit);
        }
        transactionRepository.save(transactionsEntity);
    }
}
