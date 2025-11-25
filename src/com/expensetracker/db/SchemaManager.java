package com.expensetracker.db;

import java.sql.Connection;
import java.sql.Statement;

public class SchemaManager {
    public static void createTables(Connection conn) throws Exception {
        try (Statement s = conn.createStatement()) {
            s.executeUpdate("CREATE TABLE IF NOT EXISTS users (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, email TEXT UNIQUE NOT NULL, password_hash TEXT NOT NULL, created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)");
            s.executeUpdate("CREATE TABLE IF NOT EXISTS categories (id INTEGER PRIMARY KEY AUTOINCREMENT, user_id INTEGER NOT NULL, name TEXT NOT NULL, type TEXT CHECK(type IN ('INCOME','EXPENSE')) NOT NULL)");
            s.executeUpdate("CREATE TABLE IF NOT EXISTS accounts (id INTEGER PRIMARY KEY AUTOINCREMENT, user_id INTEGER NOT NULL, name TEXT NOT NULL, balance NUMERIC(14,2) DEFAULT 0.00, currency TEXT DEFAULT 'USD')");
            s.executeUpdate("CREATE TABLE IF NOT EXISTS transactions (id INTEGER PRIMARY KEY AUTOINCREMENT, user_id INTEGER NOT NULL, account_id INTEGER NOT NULL, category_id INTEGER, type TEXT CHECK(type IN ('EXPENSE','INCOME')) NOT NULL, amount NUMERIC(14,2) NOT NULL, date TEXT NOT NULL, description TEXT, created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)");
            // seed demo user/account/category if not present
            s.executeUpdate("INSERT INTO users (name,email,password_hash) SELECT 'Demo User','demo@example.com','demo' WHERE NOT EXISTS (SELECT 1 FROM users WHERE email='demo@example.com')");
            s.executeUpdate("INSERT INTO accounts (user_id,name,balance,currency) SELECT u.id,'Cash',1000.00,'USD' FROM users u WHERE u.email='demo@example.com' AND NOT EXISTS (SELECT 1 FROM accounts a WHERE a.user_id=u.id AND a.name='Cash')");
            s.executeUpdate("INSERT INTO categories (user_id,name,type) SELECT u.id,'General','EXPENSE' FROM users u WHERE u.email='demo@example.com' AND NOT EXISTS (SELECT 1 FROM categories c WHERE c.user_id=u.id AND c.name='General')");
        }
    }
}
