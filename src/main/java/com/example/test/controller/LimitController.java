package com.example.test.controller;

import com.example.test.model.limit.LimitRequest;
import com.example.test.model.limit.LimitResponse;
import com.example.test.service.LimitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/limits")
public class LimitController {
    private final LimitService limitService;
    @Autowired
    public LimitController(LimitService limitService) {
        this.limitService = limitService;
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<List<LimitResponse>> getAllLimitsByAccountNumber(@PathVariable Long accountNumber) {
        List<LimitResponse> list = limitService.getLimitsByAccountNumber(accountNumber);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addLimit(@RequestBody LimitRequest limit) {
        try {
            limitService.setLimitByExpenseCategoryAndAccountNumber(limit.getExpenseCategory(), limit.getAccountNumber(), limit.getLimitUsd());
            return ResponseEntity.ok("The limit has been added successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error when adding the limit: " + e.getMessage());
        }
    }
}