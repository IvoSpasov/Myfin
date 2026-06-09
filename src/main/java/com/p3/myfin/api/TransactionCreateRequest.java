package com.p3.myfin.api;

import com.p3.myfin.data.TransactionType;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TransactionCreateRequest(
        @NotNull TransactionType type,
        @NotNull BigDecimal amount
) {
}
