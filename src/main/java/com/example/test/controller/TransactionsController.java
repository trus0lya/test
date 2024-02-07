package com.example.test.controller;


import com.example.test.entity.TransactionsEntity;
import com.example.test.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/transactions")
public class TransactionsController {
    private final TransactionService transactionService;

    @Autowired
    public TransactionsController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public ResponseEntity<List<TransactionsEntity>> getAll() {
        List<TransactionsEntity> entities = transactionService.getAllTransactions();
        if(entities.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(entities, HttpStatus.OK);
    }

   @PostMapping("/add")
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
