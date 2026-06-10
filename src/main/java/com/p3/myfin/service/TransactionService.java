package com.p3.myfin.service;

import com.p3.myfin.api.TransactionCreateRequest;
import com.p3.myfin.api.TransactionResponse;
import com.p3.myfin.data.Transaction;
import com.p3.myfin.data.TransactionRepository;
import com.p3.myfin.data.TransactionType;
import com.p3.myfin.error.BadRequestException;
import com.p3.myfin.error.NotFoundException;
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

    public TransactionResponse getTransaction(long id) {
        return transactionRepository
                .findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new NotFoundException("Transaction not found"));
    }

    public List<TransactionResponse> getTransactions(Optional<TransactionType> type, Optional<BigDecimal> amount) {
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

        return transactions.stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional
    public TransactionResponse createTransaction(TransactionCreateRequest request) {
        validateAmount(request.amount()); // business logic level validation
        var transaction = new Transaction();
        mapToEntity(request, transaction);
        var savedTransaction = transactionRepository.save(transaction);
        return mapToResponse(savedTransaction);
    }

    private void mapToEntity(TransactionCreateRequest request, Transaction transactionEntity) {
        transactionEntity.setType(request.type());
        transactionEntity.setAmount(request.amount());
    }

    private TransactionResponse mapToResponse(Transaction entity) {
        return new TransactionResponse(
                entity.getId(),
                entity.getType(),
                entity.getAmount(),
                entity.getDateCreated());
    }

    private void validateAmount(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("Amount must be a positive and not a zero number.");
        }
    }
}
