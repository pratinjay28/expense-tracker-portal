package com.expensetracker.app;

import com.expensetracker.db.DBConnectionManager;
import com.expensetracker.db.SchemaManager;
import com.expensetracker.ui.MainFrame;

import javax.swing.*;
import java.sql.Connection;

public class MainApp {
    public static void main(String[] args) {
        // Configure DB URL for SQLite (file-based). Change URL for other DBs.
        String url = "jdbc:sqlite:expensetracker.db";
        DBConnectionManager db = new DBConnectionManager(url, "", "");
        try (Connection conn = db.getConnection()) {
            SchemaManager.createTables(conn);
        } catch (Exception e) {
            System.err.println("Failed to initialize database: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame(db);
            frame.setVisible(true);
        });
    }
}
