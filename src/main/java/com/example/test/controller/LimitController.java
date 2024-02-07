package com.example.test.controller;

import com.example.test.entity.LimitsEntity;
import com.example.test.enums.ExpenseCategory;
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


   @GetMapping
   public ResponseEntity<List<LimitsEntity>> all() {
       List<LimitsEntity> limitsEntities = limitService.getAll();
       if(limitsEntities.size() == 0) {
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }
       return new ResponseEntity<>(limitsEntities, HttpStatus.OK);
   }


    @PostMapping("/add")
    public ResponseEntity<List<LimitsEntity>> updateLimits (
            @RequestBody LimitsEntity limit
    ) {
        ExpenseCategory expenseCategory = ExpenseCategory.fromString(limit.getExpenseCategory().getCategory());
        limitService.setLimitByExpenseCategoryAndAccountNumber(expenseCategory, limit.getAccountNumber(), limit.getLimitUsd());
        return all();
    }
}