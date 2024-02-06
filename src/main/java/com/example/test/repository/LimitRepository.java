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
    @Query("SELECT l FROM LimitsEntity l WHERE l.expenseCategory = :expenseCategory AND l.accountNumber = :accountNumber ORDER BY l.creationDate DESC")
    Optional<List<LimitsEntity>> findLatestByExpenseCategoryAndAccountNumber(@Param("expenseCategory") ExpenseCategory expenseCategory, @Param("accountNumber") Long accountNumber);
}
