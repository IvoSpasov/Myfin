package com.p3.myfin.api;

import com.p3.myfin.data.TransactionType;

import java.math.BigDecimal;

public record TransactionCreateRequest(
        TransactionType type,
        BigDecimal amount
) {
}
