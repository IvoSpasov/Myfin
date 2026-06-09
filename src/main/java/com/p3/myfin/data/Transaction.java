package com.p3.myfin.data;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private TransactionType type;
    @Column(name = "amount", precision = 19, scale = 4)
    private BigDecimal amount;

    public Long getId() { return id; }
    public TransactionType getType() { return type; }
    public BigDecimal getAmount() { return amount; }

    public void setType(TransactionType type) { this.type = type; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
}

