package com.p3.myfin.data;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "balances")
public class Balance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "amount", precision = 19, scale = 4)
    private BigDecimal balance;

    public long getId() { return id; }
    public BigDecimal getBalance() { return balance; }
}
