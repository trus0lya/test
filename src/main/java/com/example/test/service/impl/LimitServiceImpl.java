package com.example.test.service.impl;

import com.example.test.entity.LimitsEntity;
import com.example.test.enums.ExpenseCategory;
import com.example.test.mapper.impl.LimitMapper;
import com.example.test.model.limit.LimitResponse;
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

    private final LimitMapper limitMapper;

    @Autowired
    public LimitServiceImpl(LimitRepository limitRepository, LimitMapper limitMapper) {
        this.limitRepository = limitRepository;
        this.limitMapper = limitMapper;
    }

    @Override
    public List<LimitResponse> getLimitsByAccountNumber(Long accountNumber) {
        return limitRepository.findByAccountNumber(accountNumber).stream().map(limitMapper::convertEntityToDTO).toList();
    }

    @Override
    public List<LimitResponse> getAll() {
        return limitRepository.findAll().stream().map(limitMapper::convertEntityToDTO).toList();
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
                !DateComparisonUtil.isMonthsDifferent(existingLimit.getUpdateDate(),
                        new Timestamp(System.currentTimeMillis()))) {
            BigDecimal currentRemainsBeforeExceed = existingLimit.getRemainsBeforeExceed();
            BigDecimal newRemainsBeforeExceed = limit.add(currentRemainsBeforeExceed.subtract(existingLimit.getLimitUsd()));
            limitEntity.setRemainsBeforeExceed(newRemainsBeforeExceed);
        } else {
            limitEntity.setRemainsBeforeExceed(limit);
        }
        limitRepository.save(limitEntity);
        log.info("A customer with account number {} has set a new limit for the {} category equal to {}$.", accountNumber, expenseCategory, limit);
    }
}
