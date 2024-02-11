package com.example.test.mapper.impl;

import com.example.test.entity.LimitsEntity;
import com.example.test.mapper.Mapper;
import com.example.test.model.limit.LimitResponse;
import org.springframework.stereotype.Component;

@Component
public class LimitMapper implements Mapper<LimitResponse, LimitsEntity> {
    @Override
    public LimitResponse convertEntityToDTO(LimitsEntity entity) {
        return new LimitResponse(entity.getAccountNumber(),
                entity.getExpenseCategory(),
                entity.getLimitUsd(),
                entity.getCreationDate(),
                entity.getRemainsBeforeExceed(),
                entity.getUpdateDate());
    }
}
