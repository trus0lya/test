package com.example.test.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.test.entity.LimitsEntity;
import com.example.test.enums.ExpenseCategory;
import com.example.test.repository.LimitRepository;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
public class LimitServiceTest {
    @Autowired
    private LimitService limitService;

    @MockBean
    private LimitRepository limitRepository;

    @Test
    void getAllLimitsTest() {
        LimitsEntity limit1 = new LimitsEntity(1L, 1L, ExpenseCategory.PRODUCT, new BigDecimal("1000.000"), new Timestamp(System.currentTimeMillis()), new BigDecimal("934.00"), new Timestamp(System.currentTimeMillis()));
        LimitsEntity limit2 = new LimitsEntity(2L, 2L, ExpenseCategory.SERVICE, new BigDecimal("1000.000"), new Timestamp(System.currentTimeMillis()), new BigDecimal("934.00"), new Timestamp(System.currentTimeMillis()));
        List<LimitsEntity> expectedLimits = Arrays.asList(limit1, limit2);
        when(limitRepository.findAll()).thenReturn(expectedLimits);
        List<LimitsEntity> actualLimits = limitService.getAll();
        assertEquals(expectedLimits, actualLimits);
        verify(limitRepository).findAll();
    }

    @Test
    void getAllLimitsTest2() {
        List<LimitsEntity> expectedLimits = Collections.emptyList();
        when(limitRepository.findAll()).thenReturn(expectedLimits);
        List<LimitsEntity> actualLimits = limitService.getAll();
        assertEquals(expectedLimits, actualLimits);
        verify(limitRepository).findAll();
    }


    @Test
    void getLimitsByAccountNumberTest() {
        Long accountNumber = 1L;
        LimitsEntity limit1 = new LimitsEntity(1L, 1L, ExpenseCategory.PRODUCT, new BigDecimal("1000.000"), new Timestamp(System.currentTimeMillis()), new BigDecimal("934.00"), new Timestamp(System.currentTimeMillis()));
        LimitsEntity limit3 = new LimitsEntity(3L, 1L, ExpenseCategory.SERVICE, new BigDecimal("1000.000"), new Timestamp(System.currentTimeMillis()), new BigDecimal("934.00"), new Timestamp(System.currentTimeMillis()));

        List<LimitsEntity> expectedLimits = Arrays.asList(limit1, limit3);
        when(limitRepository.findByAccountNumber(accountNumber)).thenReturn(expectedLimits);

        List<LimitsEntity> actualLimits = limitService.getLimitsByAccountNumber(accountNumber);

        assertEquals(expectedLimits, actualLimits);

        verify(limitRepository).findByAccountNumber(accountNumber);
    }

    @Test
    void getLimitsByAccountNumberTest2() {
        Long accountNumber = 3L;
        List<LimitsEntity> expectedLimits = Collections.emptyList();
        when(limitRepository.findByAccountNumber(accountNumber)).thenReturn(expectedLimits);

        List<LimitsEntity> actualLimits = limitService.getLimitsByAccountNumber(accountNumber);

        assertEquals(expectedLimits, actualLimits);

        verify(limitRepository).findByAccountNumber(accountNumber);
    }

    @Test
    void getLimitsByAccountNumberTest3() {
        Long accountNumber = 1L;
        LimitsEntity limit1 = new LimitsEntity(1L, 1L, ExpenseCategory.PRODUCT, new BigDecimal("1000.000"), new Timestamp(System.currentTimeMillis()), new BigDecimal("934.00"), new Timestamp(System.currentTimeMillis()));
        LimitsEntity limit2 = new LimitsEntity(2L, 1L, ExpenseCategory.SERVICE, new BigDecimal("1000.000"), new Timestamp(System.currentTimeMillis()), new BigDecimal("934.00"), new Timestamp(System.currentTimeMillis()));

        List<LimitsEntity> expectedLimits = Arrays.asList(limit1, limit2);
        when(limitRepository.findByAccountNumber(accountNumber)).thenReturn(expectedLimits);

        List<LimitsEntity> actualLimits = limitService.getLimitsByAccountNumber(accountNumber);

        assertEquals(expectedLimits, actualLimits);

        verify(limitRepository).findByAccountNumber(accountNumber);
    }

    @Test
    void setLimitByExpenseCategoryAndAccountNumberTest() {
        Long accountNumber = 1L;
        BigDecimal limitAmount = new BigDecimal("1000.00");
        ExpenseCategory expenseCategory = ExpenseCategory.PRODUCT;

        when(limitRepository.findLatestByExpenseCategoryAndAccountNumber(anyString(), anyLong())).thenReturn(null);

        limitService.setLimitByExpenseCategoryAndAccountNumber(expenseCategory, accountNumber, limitAmount);

        verify(limitRepository).save(any(LimitsEntity.class));
    }

    @Test
    void setLimitByExpenseCategoryAndAccountNumberTest2() {
        Long accountNumber = 1L;
        BigDecimal existingLimitAmount = new BigDecimal("500.00");
        BigDecimal newLimitAmount = new BigDecimal("1000.00");
        ExpenseCategory expenseCategory = ExpenseCategory.SERVICE;
        LimitsEntity existingLimit = new LimitsEntity();
        existingLimit.setLimitUsd(existingLimitAmount);
        existingLimit.setRemainsBeforeExceed(new BigDecimal("400.00"));

        when(limitRepository.findLatestByExpenseCategoryAndAccountNumber(expenseCategory.getCategory(), accountNumber)).thenReturn(existingLimit);

        limitService.setLimitByExpenseCategoryAndAccountNumber(expenseCategory, accountNumber, newLimitAmount);
        ArgumentCaptor<LimitsEntity> limitEntityCaptor = ArgumentCaptor.forClass(LimitsEntity.class);

        verify(limitRepository).save(limitEntityCaptor.capture());
        LimitsEntity savedEntity = limitEntityCaptor.getValue();
        assertEquals(new BigDecimal("900.00"), savedEntity.getRemainsBeforeExceed());
    }

    @Test
    void createLimitIfNotExistTest() {
        Long accountNumber = 123L;
        ExpenseCategory expenseCategory = ExpenseCategory.PRODUCT;

        when(limitRepository.existsByAccountNumberAndExpenseCategory(accountNumber, expenseCategory)).thenReturn(false);
        limitService.createLimitIfNotExist(accountNumber, expenseCategory);
        verify(limitRepository, times(1)).save(any(LimitsEntity.class));
    }

    @Test
    void createLimitIfNotExistTest2() {
        Long accountNumber = 123L;
        ExpenseCategory expenseCategory = ExpenseCategory.SERVICE;

        when(limitRepository.existsByAccountNumberAndExpenseCategory(accountNumber, expenseCategory)).thenReturn(true);

        limitService.createLimitIfNotExist(accountNumber, expenseCategory);

        verify(limitRepository, times(0)).save(any(LimitsEntity.class));
    }
}
