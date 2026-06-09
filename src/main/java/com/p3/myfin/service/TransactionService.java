package com.p3.myfin.service;

import com.p3.myfin.data.Transaction;
import com.p3.myfin.data.TransactionRepository;
import com.p3.myfin.data.TransactionType;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service // this annotation tells Spring to create an instance of this class and manage its lifecycle when we start the application
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

//    public Transaction getTransaction(long id) {
//        return transactionRepository.getTransaction(id);
//    }

    @Transactional
    public void createTransaction(TransactionType type, BigDecimal amount) {
        var transaction = new Transaction();
        transaction.setType(type);
        transaction.setAmount(amount);
        transactionRepository.save(transaction);
    };
}
