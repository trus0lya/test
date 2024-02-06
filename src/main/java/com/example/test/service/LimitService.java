package com.example.test.service;

import com.example.test.entity.LimitsEntity;
import com.example.test.enums.ExpenseCategory;
import com.example.test.repository.LimitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class LimitService {
    private final LimitRepository limitRepository;

    @Autowired
    public LimitService(LimitRepository limitRepository) {
        this.limitRepository = limitRepository;
    }

    public List<LimitsEntity> getAll() {
        return limitRepository.findAll();
    }

    public void setLimitByExpenseCategoryAndAccountNumber(ExpenseCategory expenseCategory, Long accountNumber, BigDecimal limit) {
        Optional<List<LimitsEntity>> existingLimit = limitRepository.findLatestByExpenseCategoryAndAccountNumber(expenseCategory, accountNumber);

        LimitsEntity limitEntity = new LimitsEntity();
        limitEntity.setAccountNumber(accountNumber);
        limitEntity.setExpenseCategory(expenseCategory);
        limitEntity.setCreationDate(new Timestamp(System.currentTimeMillis()));
        limitEntity.setLimitUsd(limit);

        if (existingLimit.isPresent() && !existingLimit.get().isEmpty()) {
            LimitsEntity temp = existingLimit.get().get(0);
            BigDecimal currentRemainsBeforeExceed = temp.getRemainsBeforeExceed();
            BigDecimal newRemainsBeforeExceed = limit.add(currentRemainsBeforeExceed.subtract(temp.getLimitUsd()));
            limitEntity.setRemainsBeforeExceed(newRemainsBeforeExceed);
        } else {
            limitEntity.setRemainsBeforeExceed(limit);
        }

        limitRepository.save(limitEntity);
    }
}
