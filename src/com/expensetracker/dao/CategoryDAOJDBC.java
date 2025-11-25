package com.expensetracker.dao;

import com.expensetracker.db.DBConnectionManager;
import com.expensetracker.model.Category;

import java.sql.*;
import java.util.Optional;

public class CategoryDAOJDBC {
    private final DBConnectionManager db;

    public CategoryDAOJDBC(DBConnectionManager db) { this.db = db; }

    public long create(long userId, String name, String type) throws DAOException {
        String sql = "INSERT INTO categories (user_id, name, type) VALUES (?,?,?)";
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, userId);
            ps.setString(2, name);
            ps.setString(3, type);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getLong(1);
            }
            return -1;
        } catch (SQLException e) {
            throw new DAOException("Create category failed", e);
        }
    }

    public Optional<Category> findById(long id) throws DAOException {
        String sql = "SELECT id, user_id, name, type FROM categories WHERE id = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(new Category(rs.getLong(1), rs.getLong(2), rs.getString(3), rs.getString(4)));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DAOException("Find category failed", e);
        }
    }
}
