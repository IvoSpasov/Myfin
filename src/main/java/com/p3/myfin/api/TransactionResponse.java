package com.p3.myfin.api;

import com.p3.myfin.data.TransactionType;

import java.math.BigDecimal;
import java.time.Instant;

public record TransactionResponse(
        long id,
        TransactionType type,
        BigDecimal amount,
        Instant dateCreated
) {
}
