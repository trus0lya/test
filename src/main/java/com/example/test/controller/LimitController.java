package com.example.test.controller;

import com.example.test.entity.LimitsEntity;
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
    public ResponseEntity<?> getAllLimitsByAccountNumber(@PathVariable Long accountNumber) {
        List<LimitsEntity> limits = limitService.getLimitsByAccountNumber(accountNumber);
        return new ResponseEntity<>(limits, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addLimit(@RequestBody LimitsEntity limitRequest) {
        try {
            limitService.setLimitByExpenseCategoryAndAccountNumber(limitRequest.getExpenseCategory(), limitRequest.getAccountNumber(), limitRequest.getLimitUsd());
            return ResponseEntity.ok("The limit has been added successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error when adding the limit: " + e.getMessage());
        }
    }
}