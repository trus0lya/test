package com.example.test.repository;

import com.example.test.entity.TransactionsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TransactionRepository extends JpaRepository<TransactionsEntity, Long> {
    @Query(value = "SELECT * FROM transactions " +
            "WHERE account_num_from = :accountNumber AND limit_exceeded_id is not null", nativeQuery = true)
    List<TransactionsEntity> getByAccountNumberWithExceededLimit(Long accountNumber);
}
