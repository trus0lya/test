package com.example.test.service.impl;

import com.example.test.entity.LimitsEntity;
import com.example.test.enums.ExpenseCategory;
import com.example.test.repository.LimitRepository;
import com.example.test.service.LimitService;
import com.example.test.util.DateComparisonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Slf4j
@Service
public class LimitServiceImpl implements LimitService {
    private final LimitRepository limitRepository;

    @Autowired
    public LimitServiceImpl(LimitRepository limitRepository) {
        this.limitRepository = limitRepository;
    }

    @Override
    public List<LimitsEntity> getLimitsByAccountNumber(Long accountNumber) {
        return limitRepository.findByAccountNumber(accountNumber);
    }

    @Override
    public List<LimitsEntity> getAll() {
        return limitRepository.findAll();
    }

    @Override
    public void setLimitByExpenseCategoryAndAccountNumber(ExpenseCategory expenseCategory, Long accountNumber, BigDecimal limit) {
        LimitsEntity existingLimit = limitRepository.findLatestByExpenseCategoryAndAccountNumber(expenseCategory.toString(), accountNumber);
        LimitsEntity limitEntity = new LimitsEntity();
        limitEntity.setAccountNumber(accountNumber);
        limitEntity.setExpenseCategory(expenseCategory);
        limitEntity.setCreationDate(new Timestamp(System.currentTimeMillis()));
        limitEntity.setLimitUsd(limit);
        limitEntity.setUpdateDate(new Timestamp(System.currentTimeMillis()));
        if (existingLimit != null &&
                !DateComparisonUtil.areTheMonthsDifferent(existingLimit.getCreationDate(),
                        new Timestamp(System.currentTimeMillis()))) {
            LimitsEntity temp = existingLimit;
            BigDecimal currentRemainsBeforeExceed = temp.getRemainsBeforeExceed();
            BigDecimal newRemainsBeforeExceed = limit.add(currentRemainsBeforeExceed.subtract(temp.getLimitUsd()));
            limitEntity.setRemainsBeforeExceed(newRemainsBeforeExceed);
        } else {
            limitEntity.setRemainsBeforeExceed(limit);
        }
        limitRepository.save(limitEntity);
        log.info("A customer with account number {} has set a new limit for the {} category equal to {}$.", accountNumber, expenseCategory, limit);
    }
}
