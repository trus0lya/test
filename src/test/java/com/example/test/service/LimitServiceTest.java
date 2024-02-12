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

import static org.mockito.Mockito.when;

@SpringBootTest
public class LimitServiceTest {
    @Autowired
    private LimitService limitService;

    @MockBean
    private LimitRepository limitRepository;

    @Test
    void setLimitByExpenseCategoryAndAccountNumberTest() {
        Long accountNumber = 1L;
        BigDecimal limitAmount = new BigDecimal(1000);
        ExpenseCategory expenseCategory = ExpenseCategory.PRODUCT;

        when(limitRepository.findLatestByExpenseCategoryAndAccountNumber(anyString(), anyLong())).thenReturn(null);

        limitService.setLimitByExpenseCategoryAndAccountNumber(expenseCategory, accountNumber, limitAmount);

        verify(limitRepository).save(any(LimitsEntity.class));
    }

    @Test
    void setLimitByExpenseCategoryAndAccountNumberTest2() {
        Long accountNumber = 1L;
        BigDecimal existingLimitAmount = new BigDecimal(500);
        BigDecimal newLimitAmount = new BigDecimal(1000);


        ExpenseCategory expenseCategory = ExpenseCategory.SERVICE;
        LimitsEntity existingLimit = new LimitsEntity();
        existingLimit.setLimitUsd(existingLimitAmount);
        existingLimit.setRemainsBeforeExceed(new BigDecimal(400));
        existingLimit.setCreationDate(new Timestamp(System.currentTimeMillis()));

        when(limitRepository.findLatestByExpenseCategoryAndAccountNumber(expenseCategory.toString(), accountNumber)).thenReturn(existingLimit);


        limitService.setLimitByExpenseCategoryAndAccountNumber(expenseCategory, accountNumber, newLimitAmount);
        ArgumentCaptor<LimitsEntity> limitEntityCaptor = ArgumentCaptor.forClass(LimitsEntity.class);

        verify(limitRepository).save(limitEntityCaptor.capture());
        LimitsEntity savedEntity = limitEntityCaptor.getValue();
        assertEquals(new BigDecimal(900), savedEntity.getRemainsBeforeExceed());
    }


    @Test
    void setLimitByExpenseCategoryAndAccountNumberTest3() {
        Long accountNumber = 1L;
        BigDecimal existingLimitAmount = new BigDecimal(500);
        BigDecimal newLimitAmount = new BigDecimal(1000);
        ExpenseCategory expenseCategory = ExpenseCategory.SERVICE;
        LimitsEntity existingLimit = new LimitsEntity();
        existingLimit.setLimitUsd(existingLimitAmount);
        existingLimit.setRemainsBeforeExceed(new BigDecimal(400));
        existingLimit.setCreationDate(new Timestamp(1706605144));

        when(limitRepository.findLatestByExpenseCategoryAndAccountNumber(expenseCategory.toString(), accountNumber)).thenReturn(existingLimit);

        limitService.setLimitByExpenseCategoryAndAccountNumber(expenseCategory, accountNumber, newLimitAmount);
        ArgumentCaptor<LimitsEntity> limitEntityCaptor = ArgumentCaptor.forClass(LimitsEntity.class);

        verify(limitRepository).save(limitEntityCaptor.capture());
        LimitsEntity savedEntity = limitEntityCaptor.getValue();
        assertEquals(new BigDecimal(1000), savedEntity.getRemainsBeforeExceed());
    }
}