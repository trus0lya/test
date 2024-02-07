package com.example.test.repository;

import com.example.test.entity.LimitsEntity;
import com.example.test.enums.ExpenseCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LimitRepository extends JpaRepository<LimitsEntity, Long> {
    @Query(value = "SELECT * FROM limits WHERE expense_category = :expenseCategory AND account_number = :accountNumber ORDER BY creation_date DESC FOR UPDATE", nativeQuery = true)
    List<LimitsEntity> findLatestByExpenseCategoryAndAccountNumberForUpdate(@Param("expenseCategory") ExpenseCategory expenseCategory, @Param("accountNumber") Long accountNumber);
    List<LimitsEntity> findByAccountNumber(Long accountNumber);
}
