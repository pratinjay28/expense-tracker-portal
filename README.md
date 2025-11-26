
---

# 📊 Expense Tracker (Java + Swing + JDBC)

A simple **Expense Tracking Desktop Application** built entirely in **Java**, using:

* **Swing GUI**
* **JDBC + SQLite Database**
* **OOP Principles** (Inheritance, Polymorphism, Encapsulation, Abstraction)
* **DAO Layer**
* **Service Layer**
* **Multithreading (SwingWorker)**
* **Exception Handling**
* **Java Collections & Generics**

This project is perfect for academic submissions, OOP concepts demonstration, and GUI-based Java applications.

---

## 🚀 Features

### ✔ Core Functionalities

* Add Income / Expense transactions
* View all transactions in a table
* Automatic account balance update
* Categorization of expense/income
* SQLite file-based storage (`expensetracker.db`)
* Real-time UI refresh (SwingWorker thread)

### ✔ Tech Stack

* **Java 17+ or later**
* **SQLite (via sqlite-jdbc.jar)**
* **Swing UI**
* **Maven-Free Manual Project Structure**

---

## 📁 Project Structure

```
src/
└── com/
    └── expensetracker/
        ├── app/
        │   └── MainApp.java
        ├── ui/
        │   ├── MainFrame.java
        │   └── AddTransactionDialog.java
        ├── model/
        │   ├── Transaction.java
        │   ├── Expense.java
        │   ├── Income.java
        │   ├── Category.java
        │   ├── Account.java
        │   └── User.java
        ├── service/
        │   ├── TransactionService.java
        │   ├── AccountService.java
        │   └── ValidationService.java
        ├── dao/
        │   ├── DAO.java
        │   ├── DAOException.java
        │   ├── TransactionDAOJDBC.java
        │   ├── AccountDAOJDBC.java
        │   └── CategoryDAOJDBC.java
        ├── db/
        │   ├── DBConnectionManager.java
        │   └── SchemaManager.java
        └── util/
            ├── DateUtil.java
            └── ValidationException.java
```

---

## 🛠 How to Run (Manual Java Compile)

### 1️⃣ **Install SQLite JDBC**

Place file in project root:

```
sqlite-jdbc.jar
```

### 2️⃣ **Compile**

Run from project root:

```bash
mkdir -p out
javac -d out -cp sqlite-jdbc.jar $(find src -name "*.java")
```

### 3️⃣ **Run**

```bash
java -cp out:sqlite-jdbc.jar com.expensetracker.app.MainApp
```

💡 On Windows, use `;` instead of `:` for classpath.

---

## 🧱 Database

The app automatically creates the database file at:

```
expensetracker.db
```

Tables Created:

* users
* accounts
* categories
* transactions

`SchemaManager` handles table creation.

---

## 🧰 OOP Concepts Used

* **Inheritance:** `Transaction` → `Income` / `Expense`
* **Polymorphism:** Overridden behavior for transaction types
* **Abstraction:** DAO Interfaces
* **Encapsulation:** Models with private fields + getters/setters
* **Exception Handling:** Custom `DAOException`, `ValidationException`

---

## 🔄 Multithreading

Uses **SwingWorker** to:

* Load transactions without freezing the UI
* Refresh table asynchronously

---


## Contributing

Contributions are welcome!
Fork the repo → make changes → submit a PR.

---

## License

This project is fully open-source.
You may modify and use it for educational or personal projects.

---

