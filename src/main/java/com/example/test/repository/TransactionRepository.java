package com.example.test.repository;

import com.example.test.entity.TransactionsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface TransactionRepository extends JpaRepository<TransactionsEntity, Long> {

}
