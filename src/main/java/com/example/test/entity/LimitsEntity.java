package com.example.test.entity;

import com.example.test.enums.ExpenseCategory;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;


@Entity
@Table(name = "limits", schema = "bank")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LimitsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic
    @Column(name = "account_number", nullable = false)
    private Long accountNumber;

    @Basic
    @Column(name = "expense_category", nullable = false)
    @Enumerated(EnumType.STRING)
    private ExpenseCategory expenseCategory;

    @Basic
    @Column(name = "limit_usd", nullable = false, precision = 2)
    private BigDecimal limitUsd;

    @Basic
    @Column(name = "creation_date", nullable = false)
    private Timestamp creationDate;

    @Basic
    @Column(name = "remains_before_exceed", nullable = false, precision = 2)
    private BigDecimal remainsBeforeExceed;

    @Basic
    @Column(name = "update_date", nullable = false)
    private Timestamp updateDate;
}
