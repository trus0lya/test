package com.example.test.mapper.impl;

import com.example.test.entity.TransactionsEntity;
import com.example.test.mapper.Mapper;
import com.example.test.model.Limit;
import com.example.test.model.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper implements Mapper<Transaction, TransactionsEntity> {
    @Override
    public Transaction convertEntityToDTO(TransactionsEntity entity) {
        LimitMapper limitMapper = new LimitMapper();
        Limit limit = limitMapper.convertEntityToDTO(entity.getLimitsEntity());
        return new Transaction(entity.getAccountNumFrom(),
                entity.getAccountNumTo(),
                entity.getExpenseCategory(),
                entity.getAmount(),
                entity.getCurrency(),
                entity.getDateTime(),
                limit);
    }
}
