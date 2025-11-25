package com.expensetracker.service;

import com.expensetracker.dao.AccountDAOJDBC;
import com.expensetracker.db.DBConnectionManager;
import com.expensetracker.model.Account;

public class AccountService {
    private final AccountDAOJDBC dao;

    public AccountService(DBConnectionManager db) {
        this.dao = new AccountDAOJDBC(db);
    }

    public Account findById(long id) throws Exception {
        return dao.findById(id).orElseThrow(() -> new Exception("Account not found" + id));
    }
}
