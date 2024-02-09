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

    @GetMapping("/{accountNumber}")
    public ResponseEntity<?> getTransactionsExceededLimits(@PathVariable Long accountNumber) {
        List<TransactionsEntity> transactions = transactionService.getExceededLimits(accountNumber);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
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