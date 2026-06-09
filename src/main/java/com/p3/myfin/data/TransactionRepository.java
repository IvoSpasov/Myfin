package com.p3.myfin.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    //Transaction getTransaction(long id);
    //void createTransaction(TransactionType type, BigDecimal amount);
    //void editTransaction(long id, TransactionType type, BigDecimal amount);
}
