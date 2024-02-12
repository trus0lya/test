package com.example.test.entity;

import com.example.test.enums.Currency;
import com.example.test.enums.ExpenseCategory;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "transactions", schema = "bank")
@Data
@NoArgsConstructor
public class TransactionsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic
    @Column(name = "account_num_from", nullable = false)
    private Long accountNumFrom;

    @Basic
    @Column(name = "account_num_to", nullable = false)
    private Long accountNumTo;

    @Basic
    @Column(name = "expense_category", nullable = false)
    @Enumerated(EnumType.STRING)
    private ExpenseCategory expenseCategory;

    @Basic
    @Column(name = "amount", nullable = true, precision = 2)
    private BigDecimal amount;

    @Basic
    @Column(name = "currency", nullable = true)
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Basic
    @Column(name = "date_time", nullable = false)
    private Timestamp dateTime;


}
