package com.p3.myfin.api;

import com.p3.myfin.data.Transaction;
import com.p3.myfin.data.TransactionType;
import com.p3.myfin.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("{id}") // with path variables
    public Transaction getTransaction(@PathVariable long id) {
        return transactionService.getTransaction(id);
    }

    @GetMapping // with query parameters
    public List<Transaction> getTransactions(@RequestParam Optional<TransactionType> type,
                                             @RequestParam Optional<BigDecimal> amount) {
        return transactionService.getTransactions(type, amount);
    }

    @PostMapping
    public Transaction createTransaction(@Valid @RequestBody TransactionCreateRequest request) {
        return transactionService.createTransaction(request);
    }
}
