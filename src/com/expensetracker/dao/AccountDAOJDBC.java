package com.expensetracker.dao;

import com.expensetracker.db.DBConnectionManager;
import com.expensetracker.model.Account;

import java.math.BigDecimal;
import java.sql.*;
import java.util.Optional;

public class AccountDAOJDBC {
    private final DBConnectionManager db;

    public AccountDAOJDBC(DBConnectionManager db) { this.db = db; }

    public Optional<Account> findById(long id) throws DAOException {
        String sql = "SELECT id, name, balance FROM accounts WHERE id = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Account(rs.getLong(1), rs.getString(2), rs.getBigDecimal(3)));
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DAOException("Account find failed", e);
        }
    }

    public long create(String name, BigDecimal initialBalance, long userId) throws DAOException {
        String sql = "INSERT INTO accounts (user_id, name, balance, currency) VALUES (?,?,?,?)";
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, userId);
            ps.setString(2, name);
            ps.setBigDecimal(3, initialBalance);
            ps.setString(4, "USD");
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getLong(1);
            }
            return -1;
        } catch (SQLException e) {
            throw new DAOException("Create account failed", e);
        }
    }
}
