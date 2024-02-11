package com.example.test.mapper.impl;

import com.example.test.entity.TransactionsEntity;
import com.example.test.mapper.Mapper;
import com.example.test.model.limit.LimitResponse;
import com.example.test.model.transaction.TransactionResponse;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper implements Mapper<TransactionResponse, TransactionsEntity> {
    @Override
    public TransactionResponse convertEntityToDTO(TransactionsEntity entity) {
        LimitMapper limitMapper = new LimitMapper();
        LimitResponse limit = limitMapper.convertEntityToDTO(entity.getLimitsEntity());
        return new TransactionResponse(entity.getAccountNumFrom(),
                entity.getAccountNumTo(),
                entity.getExpenseCategory(),
                entity.getAmount(),
                entity.getCurrency(),
                entity.getDateTime(),
                limit);
    }
}
