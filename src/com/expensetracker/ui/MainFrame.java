package com.expensetracker.ui;

import com.expensetracker.db.DBConnectionManager;
import com.expensetracker.dao.TransactionDAOJDBC;
import com.expensetracker.model.Transaction;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.List;

public class MainFrame extends JFrame {
    private final DBConnectionManager db;
    private TransactionTableModel tableModel;

    public MainFrame(DBConnectionManager db) {
        super("Expense Tracker - Java Only");
        this.db = db;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        initUI();
    }

    private void initUI() {
        JToolBar toolbar = new JToolBar();
        JButton btnAdd = new JButton("Add Transaction");
        JButton btnRefresh = new JButton("Refresh");
        toolbar.add(btnAdd);
        toolbar.add(btnRefresh);
        add(toolbar, BorderLayout.NORTH);

        tableModel = new TransactionTableModel();
        JTable table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        btnAdd.addActionListener(e -> {
            AddTransactionDialog dlg = new AddTransactionDialog(this, db);
            dlg.setVisible(true);
            loadTransactionsInBackground();
        });

        btnRefresh.addActionListener(e -> loadTransactionsInBackground());

        // load on startup
        loadTransactionsInBackground();
    }

    private void loadTransactionsInBackground() {
        SwingWorker<List<Transaction>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<Transaction> doInBackground() throws Exception {
                TransactionDAOJDBC dao = new TransactionDAOJDBC(db);
                return dao.findAll();
            }

            @Override
            protected void done() {
                try {
                    tableModel.setTransactions(get());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(MainFrame.this, "Load failed: " + ex.getMessage());
                }
            }
        };
        worker.execute();
    }

    // simple table model
    private static class TransactionTableModel extends AbstractTableModel {
        private java.util.List<Transaction> list = java.util.Collections.emptyList();
        private final String[] cols = {"ID","Type","Amount","Date","Description"};

        public void setTransactions(java.util.List<Transaction> txs) {
            this.list = txs;
            fireTableDataChanged();
        }

        @Override
        public int getRowCount() { return list.size(); }
        @Override
        public int getColumnCount() { return cols.length; }
        @Override
        public String getColumnName(int column) { return cols[column]; }
        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Transaction t = list.get(rowIndex);
            return switch(columnIndex) {
                case 0 -> t.getId();
                case 1 -> t.getType();
                case 2 -> t.getAmount();
                case 3 -> t.getDate();
                case 4 -> t.getDescription();
                default -> null;
            };
        }
    }
}
