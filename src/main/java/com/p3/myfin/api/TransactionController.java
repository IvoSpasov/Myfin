package com.p3.myfin.api;

import com.p3.myfin.data.Transaction;
import com.p3.myfin.data.TransactionType;
import com.p3.myfin.service.TransactionService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("api/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("{id}")
    public Transaction getTransaction(@PathVariable long id) {
        return transactionService.getTransaction(id);
    }

    @PostMapping
    public void createTransaction(@RequestParam TransactionType type, @RequestParam BigDecimal amount) {
        transactionService.createTransaction(type, amount);
    }
}
