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

    //@GetMapping
    //public Transaction getTransaction() {
    //    return transactionService.getTransaction(1);
    //}

    @PostMapping
    public void createTransaction(@RequestParam TransactionType type, @RequestParam BigDecimal amount) {
        transactionService.createTransaction(type, amount);
    }
}
