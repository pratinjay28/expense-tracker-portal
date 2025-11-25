package com.expensetracker.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public abstract class Transaction {
    protected long id;
    protected long userId;
    protected long accountId;
    protected long categoryId;
    protected BigDecimal amount;
    protected LocalDate date;
    protected String description;

    public Transaction(long id, long userId, long accountId, long categoryId,
                       BigDecimal amount, LocalDate date, String description) {
        this.id = id;
        this.userId = userId;
        this.accountId = accountId;
        this.categoryId = categoryId;
        this.amount = amount;
        this.date = date;
        this.description = description;
    }

    public enum TransactionType { EXPENSE, INCOME }

    public abstract TransactionType getType();

    // getters and setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public long getUserId() { return userId; }
    public long getAccountId() { return accountId; }
    public long getCategoryId() { return categoryId; }
    public java.math.BigDecimal getAmount() { return amount; }
    public java.time.LocalDate getDate() { return date; }
    public String getDescription() { return description; }
}
