package com.example.test.controller;

import com.example.test.entity.LimitsEntity;
import com.example.test.mapper.impl.LimitMapper;
import com.example.test.model.Limit;
import com.example.test.service.LimitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/limits")
public class LimitController {
    private final LimitService limitService;

    private final LimitMapper limitMapper;

    @Autowired
    public LimitController(LimitService limitService,
                           LimitMapper limitMapper) {
        this.limitService = limitService;
        this.limitMapper = limitMapper;
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<List<Limit>> getAllLimitsByAccountNumber(@PathVariable Long accountNumber) {
        List<LimitsEntity> limits = limitService.getLimitsByAccountNumber(accountNumber);
        List<Limit> list = limits.stream()
                .map(limitMapper::convertEntityToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(list, HttpStatus.OK);
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