package com.example.test.repository;

import com.example.test.entity.LimitsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LimitRepository extends JpaRepository<LimitsEntity, Long> {
    @Query(value = "SELECT * FROM limits l " +
            "WHERE l.expense_category = :expenseCategory " +
            "AND l.account_number = :accountNumber" +
            " AND l.creation_date = " +
            "(SELECT MAX(ll.creation_date) FROM limits ll " +
            "WHERE ll.expense_category = :expenseCategory " +
            "AND ll.account_number = :accountNumber) FOR UPDATE", nativeQuery = true)
    LimitsEntity findLatestByExpenseCategoryAndAccountNumber(@Param("expenseCategory") String expenseCategory, @Param("accountNumber") Long accountNumber);

    List<LimitsEntity> findByAccountNumber(Long accountNumber);
}
