package com.p3.myfin.api;

import com.p3.myfin.data.Transaction;
import com.p3.myfin.data.TransactionType;
import com.p3.myfin.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
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
    public TransactionResponse getTransaction(@PathVariable long id) {
        return transactionService.getTransaction(id);
    }

    @GetMapping // with query parameters
    public List<TransactionResponse> getTransactions(@RequestParam Optional<TransactionType> type,
                                                     @RequestParam Optional<BigDecimal> amount) {
        return transactionService.getTransactions(type, amount);
    }

    @PostMapping
    public ResponseEntity<TransactionResponse> createTransaction(@Valid @RequestBody TransactionCreateRequest request) {
        var createdTransaction = transactionService.createTransaction(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("{/{id}}")
                .buildAndExpand(createdTransaction.id())
                .toUri();
        return ResponseEntity.created(location).body(createdTransaction);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionResponse> updateTransaction(@PathVariable long id,
                                                                 @Valid @RequestBody TransactionUpdateRequest request) {
        var updatedTransaction = transactionService.updateTransaction(id, request);
        return ResponseEntity.ok(updatedTransaction);
    }
}
