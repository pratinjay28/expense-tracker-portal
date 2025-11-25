package com.expensetracker.model;

public class Category {
    private long id;
    private long userId;
    private String name;
    private String type; // INCOME or EXPENSE

    public Category(long id, long userId, String name, String type) {
        this.id = id; this.userId = userId; this.name = name; this.type = type;
    }

    public long getId() { return id; }
    public String getName() { return name; }
    public String getType() { return type; }
}
