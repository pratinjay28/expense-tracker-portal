package com.expensetracker.ui;

import com.expensetracker.db.DBConnectionManager;
import com.expensetracker.dao.TransactionDAOJDBC;
import com.expensetracker.model.Expense;
import com.expensetracker.model.Income;
import com.expensetracker.model.Transaction;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class AddTransactionDialog extends JDialog {
    public AddTransactionDialog(Frame owner, DBConnectionManager db) {
        super(owner, "Add Transaction", true);
        setSize(420, 260);
        setLocationRelativeTo(owner);
        setLayout(new GridLayout(0,2,6,6));

        add(new JLabel("Type:"));
        JComboBox<String> type = new JComboBox<>(new String[]{"EXPENSE","INCOME"});
        add(type);

        add(new JLabel("Amount:")); JTextField tfAmount = new JTextField(); add(tfAmount);
        add(new JLabel("Date (YYYY-MM-DD):")); JTextField tfDate = new JTextField(LocalDate.now().toString()); add(tfDate);
        add(new JLabel("Description:")); JTextField tfDesc = new JTextField(); add(tfDesc);

        // Simple fixed user/account/category ids for demo
        add(new JLabel("(Demo) User ID:")); JLabel lblUser = new JLabel("1"); add(lblUser);
        add(new JLabel("(Demo) Account ID:")); JLabel lblAcc = new JLabel("1"); add(lblAcc);
        add(new JLabel("(Demo) Category ID:")); JLabel lblCat = new JLabel("1"); add(lblCat);

        JButton btnSave = new JButton("Save"); add(btnSave);
        JButton btnCancel = new JButton("Cancel"); add(btnCancel);

        btnCancel.addActionListener(e -> dispose());

        btnSave.addActionListener(e -> {
            try {
                BigDecimal amt = new BigDecimal(tfAmount.getText().trim());
                LocalDate date = LocalDate.parse(tfDate.getText().trim());
                String desc = tfDesc.getText().trim();
                long userId = Long.parseLong(lblUser.getText());
                long accId = Long.parseLong(lblAcc.getText());
                long catId = Long.parseLong(lblCat.getText());

                Transaction t;
                if ("EXPENSE".equals(type.getSelectedItem())) {
                    t = new Expense(0, userId, accId, catId, amt, date, desc);
                } else {
                    t = new Income(0, userId, accId, catId, amt, date, desc);
                }

                TransactionDAOJDBC dao = new TransactionDAOJDBC(db);
                dao.save(t);
                JOptionPane.showMessageDialog(this, "Saved successfully.");
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Save failed: " + ex.getMessage());
                ex.printStackTrace();
            }
        });
    }
}
