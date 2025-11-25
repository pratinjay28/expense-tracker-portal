package com.expensetracker.dao;

public class DAOException extends Exception {
    public DAOException(String msg, Throwable cause) { super(msg, cause); }
    public DAOException(String msg) { super(msg); }
}
