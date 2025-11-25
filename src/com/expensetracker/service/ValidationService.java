package com.expensetracker.service;

import com.expensetracker.model.Transaction;
import com.expensetracker.util.ValidationException;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ValidationService {
    public static void validateTransaction(Transaction t) throws ValidationException {
        if (t == null) throw new ValidationException("Transaction is null");
        if (t.getAmount() == null || t.getAmount().compareTo(BigDecimal.ZERO) <= 0)
            throw new ValidationException("Amount must be > 0");
        if (t.getDate() == null || t.getDate().isAfter(LocalDate.now().plusDays(365)))
            throw new ValidationException("Invalid date");
    }
}
