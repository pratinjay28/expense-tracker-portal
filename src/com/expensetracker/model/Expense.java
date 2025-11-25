package com.expensetracker.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Expense extends Transaction {
    public Expense(long id, long userId, long accountId, long categoryId,
                   BigDecimal amount, LocalDate date, String description) {
        super(id, userId, accountId, categoryId, amount, date, description);
    }

    @Override
    public TransactionType getType() {
        return TransactionType.EXPENSE;
    }
}
