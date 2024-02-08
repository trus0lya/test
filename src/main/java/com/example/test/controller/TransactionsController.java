package com.example.test.controller;


import com.example.test.entity.TransactionsEntity;
import com.example.test.service.impl.TransactionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/transactions")
public class TransactionsController {
    private final TransactionServiceImpl transactionService;

    @Autowired
    public TransactionsController(TransactionServiceImpl transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public ResponseEntity<List<TransactionsEntity>> getAllTransactions() {
        List<TransactionsEntity> entities = transactionService.getAll();
        if(!entities.isEmpty()) {
            return new ResponseEntity<>(entities, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<?> getTransactionsExceededLimits(@PathVariable Long accountNumber) {
        List<TransactionsEntity> transactions = transactionService.getExceededLimits(accountNumber);
        if (!transactions.isEmpty()) {
            return new ResponseEntity<>(transactions, HttpStatus.OK);
        }
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", "No transactions with exceeded limits were found for the account with the number " + accountNumber);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addTransaction(@RequestBody TransactionsEntity transactionsEntity) {
        try {
            transactionService.transaction(transactionsEntity.getAccountNumFrom(),
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
