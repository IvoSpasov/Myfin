package com.p3.myfin.service;

import com.p3.myfin.api.TransactionCreateRequest;
import com.p3.myfin.api.TransactionResponse;
import com.p3.myfin.api.TransactionUpdateRequest;
import com.p3.myfin.data.Transaction;
import com.p3.myfin.data.TransactionRepository;
import com.p3.myfin.data.TransactionType;
import com.p3.myfin.error.BadRequestException;
import com.p3.myfin.error.NotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

// This annotation tells Spring to create an instance of this class and manage its lifecycle when we start the application.
// It also tells Spring that this class is a component. Other components are @Repository, @Controller, @RestController.
// You can also annotate with @Component. Each of these tell Spring to make a class and keep it in the application context.
// Spring Bean is an instance of a class managed by the Spring container.
// Spring container - a runtime environment that stores, creates, injects and manages Spring beans.
// Dependency Injection can only happen in Beans.
// @RestController - tells Spring this class handles HTTP requests (bean)
// @Service - indicates a service layer component (bean)
// @Repository - marks a data access component (bean)
// @Component - used when none of the standard ones apply (bean)
@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;

    // when there's only one constructor, we don't need the autowired annotation
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public TransactionResponse getTransaction(long id) {
        return transactionRepository
                .findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new NotFoundException("Transaction not found with id: " + id));
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

    public TransactionResponse updateTransaction(long id, TransactionUpdateRequest request) {
        var transaction = transactionRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Transaction not found with id: " + id));

        mapToEntity(request, transaction);
        var savedTransaction = transactionRepository.save(transaction);
        return mapToResponse(savedTransaction);
    }

    public void deleteTransaction(long id) {
        if (!transactionRepository.existsById(id)){
            throw new NotFoundException("Transaction not found with id: " + id);
        }
        transactionRepository.deleteById(id);
    }

    private void mapToEntity(TransactionCreateRequest request, Transaction transactionEntity) {
        transactionEntity.setType(request.type());
        transactionEntity.setAmount(request.amount());
    }

    private void mapToEntity(TransactionUpdateRequest request, Transaction transactionEntity) {
        transactionEntity.setType(request.type());
        transactionEntity.setAmount(request.amount());
        transactionEntity.setDateUpdated(Instant.now());
    }

    private TransactionResponse mapToResponse(Transaction entity) {
        return new TransactionResponse(
                entity.getId(),
                entity.getType(),
                entity.getAmount(),
                entity.getDateCreated(),
                entity.getDateUpdated());
    }

    private void validateAmount(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("Amount must be a positive and not a zero number.");
        }
    }
}
