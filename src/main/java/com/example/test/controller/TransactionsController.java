package com.example.test.controller;


import com.example.test.entity.TransactionsEntity;
import com.example.test.mapper.impl.TransactionMapper;
import com.example.test.model.Transaction;
import com.example.test.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/transactions")
public class TransactionsController {
    private final TransactionService transactionService;

    private final TransactionMapper transactionMapper;

    @Autowired
    public TransactionsController(TransactionService transactionService,
                                  TransactionMapper transactionMapper) {
        this.transactionService = transactionService;
        this.transactionMapper = transactionMapper;
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<List<Transaction>> getTransactionsExceededLimits(@PathVariable Long accountNumber) {
        List<TransactionsEntity> transactions = transactionService.getExceededLimits(accountNumber);
        List<Transaction> list = transactions.stream()
                .map(transactionMapper::convertEntityToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addTransaction(@RequestBody TransactionsEntity transactionsEntity) {
        try {
            transactionService.addTransaction(transactionsEntity.getAccountNumFrom(),
                    transactionsEntity.getAccountNumTo(),
                    transactionsEntity.getExpenseCategory(),
                    transactionsEntity.getAmount(),
                    transactionsEntity.getCurrency());
            return ResponseEntity.ok("The transaction was completed successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Transaction error: " + e.getMessage());
        }
    }
}