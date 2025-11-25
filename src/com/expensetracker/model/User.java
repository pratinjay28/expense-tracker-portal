package com.expensetracker.model;

public class User {
    private long id;
    private String name;
    private String email;
    private String passwordHash;

    public User(long id, String name, String email, String passwordHash) {
        this.id = id; this.name = name; this.email = email; this.passwordHash = passwordHash;
    }

    public long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
}
