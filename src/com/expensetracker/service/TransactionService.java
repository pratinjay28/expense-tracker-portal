package com.expensetracker.service;

import com.expensetracker.dao.TransactionDAOJDBC;
import com.expensetracker.db.DBConnectionManager;
import com.expensetracker.model.Transaction;

import java.util.List;

public class TransactionService {
    private final TransactionDAOJDBC dao;

    public TransactionService(DBConnectionManager db) {
        this.dao = new TransactionDAOJDBC(db);
    }

    public Transaction save(Transaction t) throws Exception {
        // perform simple validation
        ValidationService.validateTransaction(t);
        return dao.save(t);
    }

    public List<Transaction> listAll() throws Exception {
        return dao.findAll();
    }
}
