package com.expensetracker.dao;

import com.expensetracker.db.DBConnectionManager;
import com.expensetracker.model.Expense;
import com.expensetracker.model.Income;
import com.expensetracker.model.Transaction;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TransactionDAOJDBC implements DAO<Transaction, Long> {
    private final DBConnectionManager db;

    public TransactionDAOJDBC(DBConnectionManager db) { this.db = db; }

    @Override
    public Transaction save(Transaction t) throws DAOException {
        String sql = "INSERT INTO transactions (user_id, account_id, category_id, type, amount, date, description) VALUES (?,?,?,?,?,?,?)";
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            conn.setAutoCommit(false);
            ps.setLong(1, t.getUserId());
            ps.setLong(2, t.getAccountId());
            ps.setLong(3, t.getCategoryId());
            ps.setString(4, t.getType().name());
            ps.setBigDecimal(5, t.getAmount());
            ps.setString(6, t.getDate().toString());
            ps.setString(7, t.getDescription());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) t.setId(rs.getLong(1));
            }

            // update account balance within same transaction
            updateAccountBalance(conn, t);

            conn.commit();
            return t;
        } catch (SQLException e) {
            throw new DAOException("Failed to save transaction", e);
        }
    }

    private void updateAccountBalance(Connection conn, Transaction t) throws SQLException {
        String sel = "SELECT balance FROM accounts WHERE id = ?";
        String upd = "UPDATE accounts SET balance = ? WHERE id = ?";
        try (PreparedStatement psSel = conn.prepareStatement(sel)) {
            psSel.setLong(1, t.getAccountId());
            try (ResultSet rs = psSel.executeQuery()) {
                BigDecimal bal = BigDecimal.ZERO;
                if (rs.next()) bal = rs.getBigDecimal(1);
                if (t.getType() == Transaction.TransactionType.EXPENSE) bal = bal.subtract(t.getAmount());
                else bal = bal.add(t.getAmount());
                try (PreparedStatement psUpd = conn.prepareStatement(upd)) {
                    psUpd.setBigDecimal(1, bal);
                    psUpd.setLong(2, t.getAccountId());
                    psUpd.executeUpdate();
                }
            }
        }
    }

    @Override
    public Optional<Transaction> findById(Long id) throws DAOException {
        String sql = "SELECT id, user_id, account_id, category_id, type, amount, date, description FROM transactions WHERE id = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(readTransaction(rs));
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DAOException("Find failed", e);
        }
    }

    @Override
    public List<Transaction> findAll() throws DAOException {
        String sql = "SELECT id, user_id, account_id, category_id, type, amount, date, description FROM transactions ORDER BY date DESC";
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<Transaction> list = new ArrayList<>();
            while (rs.next()) list.add(readTransaction(rs));
            return list;
        } catch (SQLException e) {
            throw new DAOException("Find all failed", e);
        }
    }

    @Override
    public boolean delete(Long id) throws DAOException {
        String sql = "DELETE FROM transactions WHERE id = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DAOException("Delete failed", e);
        }
    }

    private Transaction readTransaction(ResultSet rs) throws SQLException {
        long id = rs.getLong("id");
        long userId = rs.getLong("user_id");
        long accountId = rs.getLong("account_id");
        long categoryId = rs.getLong("category_id");
        String type = rs.getString("type");
        BigDecimal amount = rs.getBigDecimal("amount");
        LocalDate date = LocalDate.parse(rs.getString("date"));
        String desc = rs.getString("description");
        if ("EXPENSE".equals(type)) return new Expense(id, userId, accountId, categoryId, amount, date, desc);
        else return new Income(id, userId, accountId, categoryId, amount, date, desc);
    }
}
