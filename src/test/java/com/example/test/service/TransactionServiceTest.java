package com.example.test.service;

import com.example.test.entity.LimitsEntity;
import com.example.test.entity.TransactionsEntity;
import com.example.test.enums.Currency;
import com.example.test.enums.ExpenseCategory;
import com.example.test.repository.LimitRepository;
import com.example.test.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TransactionServiceTest {
    @Autowired
    TransactionService transactionService;
    @MockBean
    private  LimitService limitService;
    @MockBean
    private  TransactionRepository transactionRepository;
    @MockBean
    private  ExchangeRateService exchangeRateService;
    @MockBean
    private  LimitRepository limitRepository;


    @Test
    void addTransactionTest1() {
        Long accountFrom = 1L;
        Long accountTo = 2L;
        ExpenseCategory category = ExpenseCategory.service;
        BigDecimal amount = new BigDecimal("600");
        Currency currency = Currency.USD;
        when(limitRepository.existsByAccountNumberAndExpenseCategory(accountFrom, category)).thenReturn(true);

        TransactionsEntity transactionsEntity = new TransactionsEntity();
        transactionsEntity.setAccountNumFrom(accountFrom);
        transactionsEntity.setAccountNumTo(accountTo);
        transactionsEntity.setExpenseCategory(category);
        transactionsEntity.setAmount(amount);
        transactionsEntity.setCurrency(currency);
        transactionsEntity.setDateTime(new Timestamp(System.currentTimeMillis()));

        when(exchangeRateService.convert(amount, currency, Currency.USD)).thenReturn(new BigDecimal(600));

        LimitsEntity limit = new LimitsEntity();
        limit.setAccountNumber(accountFrom);
        limit.setExpenseCategory(category);
        limit.setLimitUsd(new BigDecimal("1000"));
        limit.setCreationDate(new Timestamp(System.currentTimeMillis()));
        limit.setRemainsBeforeExceed(new BigDecimal("1000"));
        limit.setUpdateDate(new Timestamp(System.currentTimeMillis()));

        when(limitRepository.findLatestByExpenseCategoryAndAccountNumber(category.toString(), accountFrom)).thenReturn(limit);

        transactionService.addTransaction(accountFrom, accountTo, category, amount, currency);
        ArgumentCaptor<LimitsEntity> limitEntityCaptor = ArgumentCaptor.forClass(LimitsEntity.class);
        ArgumentCaptor<TransactionsEntity> transactionsCaptor = ArgumentCaptor.forClass(TransactionsEntity.class);

        verify(limitRepository).save(limitEntityCaptor.capture());
        LimitsEntity savedEntity = limitEntityCaptor.getValue();
        assertEquals(new BigDecimal("400"), savedEntity.getRemainsBeforeExceed());
        verify(transactionRepository).save(transactionsCaptor.capture());
        TransactionsEntity savedTransactionEntity = transactionsCaptor.getValue();
        assertNull(savedTransactionEntity.getLimitsEntity());
    }

    @Test
    void addTransactionTest2() {
        Long accountFrom = 1L;
        Long accountTo = 2L;
        ExpenseCategory category = ExpenseCategory.service;
        BigDecimal amount = new BigDecimal("600");
        Currency currency = Currency.USD;
        when(limitRepository.existsByAccountNumberAndExpenseCategory(accountFrom, category)).thenReturn(true);

        TransactionsEntity transactionsEntity = new TransactionsEntity();
        transactionsEntity.setAccountNumFrom(accountFrom);
        transactionsEntity.setAccountNumTo(accountTo);
        transactionsEntity.setExpenseCategory(category);
        transactionsEntity.setAmount(amount);
        transactionsEntity.setCurrency(currency);
        transactionsEntity.setDateTime(new Timestamp(System.currentTimeMillis()));

        when(exchangeRateService.convert(amount, currency, Currency.USD)).thenReturn(new BigDecimal(600));

        LimitsEntity limit = new LimitsEntity();
        limit.setAccountNumber(accountFrom);
        limit.setExpenseCategory(category);
        limit.setLimitUsd(new BigDecimal("1000"));
        limit.setUpdateDate(new Timestamp(1706605144));
        limit.setRemainsBeforeExceed(new BigDecimal("500"));
        limit.setCreationDate(new Timestamp(1706605144));

        when(limitRepository.findLatestByExpenseCategoryAndAccountNumber(category.toString(), accountFrom)).thenReturn(limit);

        transactionService.addTransaction(accountFrom, accountTo, category, amount, currency);
        ArgumentCaptor<LimitsEntity> limitEntityCaptor = ArgumentCaptor.forClass(LimitsEntity.class);
        ArgumentCaptor<TransactionsEntity> transactionsCaptor = ArgumentCaptor.forClass(TransactionsEntity.class);

        verify(limitRepository).save(limitEntityCaptor.capture());
        LimitsEntity savedEntity = limitEntityCaptor.getValue();
        assertEquals(new BigDecimal("400"), savedEntity.getRemainsBeforeExceed());
        verify(transactionRepository).save(transactionsCaptor.capture());
        TransactionsEntity savedTransactionEntity = transactionsCaptor.getValue();
        assertNull(savedTransactionEntity.getLimitsEntity());
    }

    @Test
    void addTransactionTest3() {
        Long accountFrom = 1L;
        Long accountTo = 2L;
        ExpenseCategory category = ExpenseCategory.service;
        BigDecimal amount = new BigDecimal("600");
        Currency currency = Currency.USD;
        when(limitRepository.existsByAccountNumberAndExpenseCategory(accountFrom, category)).thenReturn(true);

        TransactionsEntity transactionsEntity = new TransactionsEntity();
        transactionsEntity.setAccountNumFrom(accountFrom);
        transactionsEntity.setAccountNumTo(accountTo);
        transactionsEntity.setExpenseCategory(category);
        transactionsEntity.setAmount(amount);
        transactionsEntity.setCurrency(currency);
        transactionsEntity.setDateTime(new Timestamp(System.currentTimeMillis()));

        when(exchangeRateService.convert(amount, currency, Currency.USD)).thenReturn(new BigDecimal(600));

        LimitsEntity limit = new LimitsEntity();
        limit.setAccountNumber(accountFrom);
        limit.setExpenseCategory(category);
        limit.setLimitUsd(new BigDecimal("1000"));
        limit.setCreationDate(new Timestamp(System.currentTimeMillis()));
        limit.setRemainsBeforeExceed(new BigDecimal("500"));
        limit.setUpdateDate(new Timestamp(System.currentTimeMillis()));

        when(limitRepository.findLatestByExpenseCategoryAndAccountNumber(category.toString(), accountFrom)).thenReturn(limit);

        transactionService.addTransaction(accountFrom, accountTo, category, amount, currency);
        ArgumentCaptor<LimitsEntity> limitEntityCaptor = ArgumentCaptor.forClass(LimitsEntity.class);
        ArgumentCaptor<TransactionsEntity> transactionsCaptor = ArgumentCaptor.forClass(TransactionsEntity.class);

        verify(limitRepository).save(limitEntityCaptor.capture());
        LimitsEntity savedEntity = limitEntityCaptor.getValue();
        assertEquals(new BigDecimal("-100"), savedEntity.getRemainsBeforeExceed());
        verify(transactionRepository).save(transactionsCaptor.capture());
        TransactionsEntity savedTransactionEntity = transactionsCaptor.getValue();
        assertEquals(limit, savedTransactionEntity.getLimitsEntity());
    }
}