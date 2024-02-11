package com.example.test.service.impl;

import com.example.test.entity.LimitsEntity;
import com.example.test.entity.TransactionsEntity;
import com.example.test.enums.Currency;
import com.example.test.enums.ExpenseCategory;
import com.example.test.mapper.impl.TransactionMapper;
import com.example.test.model.transaction.TransactionRequest;
import com.example.test.model.transaction.TransactionResponse;
import com.example.test.repository.LimitRepository;
import com.example.test.repository.TransactionRepository;
import com.example.test.service.CurrencyService;
import com.example.test.service.LimitService;
import com.example.test.service.TransactionService;
import com.example.test.util.DateComparisonUtil;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final LimitRepository limitRepository;
    private final LimitService limitService;
    private final CurrencyService currencyService;
    private final TransactionMapper transactionMapper;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository,
                                  LimitRepository limitRepository,
                                  LimitService limitService,
                                  CurrencyService currencyService,
                                  TransactionMapper transactionMapper) {
        this.transactionRepository = transactionRepository;
        this.limitRepository = limitRepository;
        this.limitService = limitService;
        this.currencyService = currencyService;
        this.transactionMapper = transactionMapper;
    }
    @Override
    public List<TransactionResponse> getExceededLimits(Long accountNumber) {
        return transactionRepository.getByAccountNumberWithExceededLimit(accountNumber).
                stream().map(transactionMapper::convertEntityToDTO).toList();
    }

    @Override
    @Transactional
    public void addTransaction(TransactionRequest transactionRequest) {
        Long accountFrom = transactionRequest.getAccountNumFrom();
        Long accountTo = transactionRequest.getAccountNumTo();
        ExpenseCategory category = transactionRequest.getExpenseCategory();
        BigDecimal amount = transactionRequest.getAmount();
        Currency currency = transactionRequest.getCurrency();
        if(!limitRepository.existsByAccountNumberAndExpenseCategory(accountFrom, category)) {
            limitService.setLimitByExpenseCategoryAndAccountNumber(category, accountFrom, new BigDecimal("1000"));
        }

        TransactionsEntity transactionsEntity = new TransactionsEntity();
        transactionsEntity.setAccountNumFrom(accountFrom);
        transactionsEntity.setAccountNumTo(accountTo);
        transactionsEntity.setExpenseCategory(category);
        transactionsEntity.setAmount(amount);
        transactionsEntity.setCurrency(currency);
        transactionsEntity.setDateTime(new Timestamp(System.currentTimeMillis()));

        BigDecimal amountUSD = currencyService.convert(amount, currency, Currency.USD);
        LimitsEntity limit = limitRepository.findLatestByExpenseCategoryAndAccountNumber(category.toString(), accountFrom);

        Timestamp updateDateTimestamp = limit.getUpdateDate();
        Timestamp nowTimestamp = new Timestamp(System.currentTimeMillis());
        BigDecimal newRemainsBeforeExceed;
        if (DateComparisonUtil.isMonthsDifferent(updateDateTimestamp, nowTimestamp)) {
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

        transactionRepository.save(transactionsEntity);

        log.info("A transaction has been completed. {} {} were transferred from account number {} to account number {} in the {} category.",
                amount, currency.toString(), accountFrom, accountTo, category);
    }
}
