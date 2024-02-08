package com.example.test.service.impl;

import com.example.test.entity.LimitsEntity;
import com.example.test.enums.ExpenseCategory;
import com.example.test.repository.LimitRepository;
import com.example.test.service.LimitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Service
public class LimitServiceImpl implements LimitService {
    private final LimitRepository limitRepository;

    @Autowired
    public LimitServiceImpl(LimitRepository limitRepository) {
        this.limitRepository = limitRepository;
    }

    @Override
    public List<LimitsEntity> getAll() {
        return limitRepository.findAll();
    }

    @Override
    public List<LimitsEntity> getLimitsByAccountNumber(Long accountNumber) {
        return limitRepository.findByAccountNumber(accountNumber);
    }

    @Override
    public void setLimitByExpenseCategoryAndAccountNumber(ExpenseCategory expenseCategory, Long accountNumber, BigDecimal limit) {
        LimitsEntity existingLimit = limitRepository.findLatestByExpenseCategoryAndAccountNumber(expenseCategory.getCategory(), accountNumber);
        LimitsEntity limitEntity = new LimitsEntity();
        limitEntity.setAccountNumber(accountNumber);
        limitEntity.setExpenseCategory(expenseCategory);
        limitEntity.setCreationDate(new Timestamp(System.currentTimeMillis()));
        limitEntity.setLimitUsd(limit);
        limitEntity.setUpdateDate(new Timestamp(System.currentTimeMillis()));
        if (existingLimit != null) {
            LimitsEntity temp = existingLimit;
            BigDecimal currentRemainsBeforeExceed = temp.getRemainsBeforeExceed();
            BigDecimal newRemainsBeforeExceed = limit.add(currentRemainsBeforeExceed.subtract(temp.getLimitUsd()));
            limitEntity.setRemainsBeforeExceed(newRemainsBeforeExceed);
        } else {
            limitEntity.setRemainsBeforeExceed(limit);
        }
        limitRepository.save(limitEntity);
    }
}
