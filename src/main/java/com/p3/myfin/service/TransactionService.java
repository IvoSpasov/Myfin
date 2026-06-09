package com.p3.myfin.service;

import com.p3.myfin.data.Transaction;
import com.p3.myfin.data.TransactionRepository;
import com.p3.myfin.data.TransactionType;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
// this annotation tells Spring to create an instance of this class and manage its lifecycle when we start the application
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction getTransaction(long id) {
        return transactionRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Transaction not found"));
    }

    public List<Transaction> getTransactions(Optional<TransactionType> type, Optional<BigDecimal> amount) {
        List<Transaction> transactions;
        if (type.isPresent() && amount.isPresent()) {
            transactions = transactionRepository.findByType(type.get()).stream()
                    .filter(t -> t.getAmount().compareTo(amount.get()) == 0)
                    .toList();
        } else if (type.isPresent()) {
            transactions = transactionRepository.findByType(type.get());
        } else if (amount.isPresent()) {
            transactions = transactionRepository.findByAmount(amount.get());
        } else {
            transactions = transactionRepository.findAll();
        }

        return transactions;
    }

    @Transactional
    public void createTransaction(TransactionType type, BigDecimal amount) {
        var transaction = new Transaction();
        transaction.setType(type);
        transaction.setAmount(amount);
        transactionRepository.save(transaction);
    }
}
