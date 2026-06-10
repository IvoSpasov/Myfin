package com.p3.myfin.data;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private TransactionType type;

    @Column(name = "amount", precision = 19, scale = 4)
    private BigDecimal amount;

    @Column(name = "date_created", nullable = false, updatable = false, columnDefinition = "TIMESTAMPTZ")
    @CreationTimestamp
    private Instant dateCreated;

    @Column(name = "date_updated", nullable = true, updatable = true, columnDefinition = "TIMESTAMPTZ")
    private Instant dateUpdated;

    public long getId() { return id; }
    public TransactionType getType() { return type; }
    public BigDecimal getAmount() { return amount; }
    public Instant getDateCreated() { return dateCreated; }
    public Instant getDateUpdated() { return dateUpdated; }

    public void setType(TransactionType type) { this.type = type; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public void setDateUpdated(Instant dateUpdated) { this.dateUpdated = dateUpdated; }
}

