package com.expensetracker.model;

import java.math.BigDecimal;

public class Account {
    private long id;
    private String name;
    private BigDecimal balance;

    public Account(long id, String name, BigDecimal balance) {
        this.id = id; this.name = name; this.balance = balance;
    }

    public synchronized void applyTransaction(java.math.BigDecimal amount, Transaction.TransactionType type) {
        if (type == Transaction.TransactionType.EXPENSE) {
            this.balance = this.balance.subtract(amount);
        } else {
            this.balance = this.balance.add(amount);
        }
    }

    public synchronized java.math.BigDecimal getBalance() { return balance; }

    public long getId() { return id; }
    public String getName() { return name; }
}
