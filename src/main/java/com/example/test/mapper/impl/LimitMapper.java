package com.example.test.mapper.impl;

import com.example.test.entity.LimitsEntity;
import com.example.test.mapper.Mapper;
import com.example.test.model.Limit;
import org.springframework.stereotype.Component;

@Component
public class LimitMapper implements Mapper<Limit, LimitsEntity> {
    @Override
    public Limit convertEntityToDTO(LimitsEntity entity) {
        return new Limit(entity.getAccountNumber(),
                entity.getExpenseCategory(),
                entity.getLimitUsd(),
                entity.getCreationDate(),
                entity.getRemainsBeforeExceed(),
                entity.getUpdateDate());
    }
}
