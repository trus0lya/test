package com.example.test.controller;

import com.example.test.entity.LimitsEntity;
import com.example.test.service.LimitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("api/v1/limits")
public class LimitController {
    private final LimitService limitService;

    @Autowired
    public LimitController(LimitService limitService) {
        this.limitService = limitService;
    }


   @GetMapping
   public ResponseEntity<List<LimitsEntity>> getAllLimits() {
       List<LimitsEntity> limits = limitService.getAll();
       if(!limits.isEmpty()) {
           return new ResponseEntity<>(limits, HttpStatus.OK);
       }
       return new ResponseEntity<>(HttpStatus.NOT_FOUND);
   }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<?> getAllLimitsByAccountNumber(@PathVariable Long accountNumber) {
        List<LimitsEntity> limits = limitService.getLimitsByAccountNumber(accountNumber);
        if (!limits.isEmpty()) {
            return new ResponseEntity<>(limits, HttpStatus.OK);
        }
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", "No limits were found for the account with the number " + accountNumber);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);

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