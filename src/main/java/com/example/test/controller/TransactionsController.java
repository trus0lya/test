package com.example.test.controller;


import com.example.test.entity.TransactionsEntity;
import com.example.test.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class TransactionsController {
    private final TransactionService transactionService;

    @Autowired
    public TransactionsController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @RequestMapping(value = "trans", method = RequestMethod.GET)
    public ResponseEntity<List<TransactionsEntity>> getAll() {
        List<TransactionsEntity> entities = transactionService.getAllTransactions();
        if(entities.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(entities, HttpStatus.OK);
    }

    @RequestMapping(value = "addtrans", method =  RequestMethod.POST)
    public void doTrans(
            @RequestBody TransactionsEntity transactionsEntity
    ) {
        transactionService.transaction(transactionsEntity.getAccountNumFrom(),
                transactionsEntity.getAccountNumTo(),
                transactionsEntity.getExpenseCategory(),
                transactionsEntity.getAmount(),
                transactionsEntity.getCurrency());
    }
}
