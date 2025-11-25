package com.expensetracker.dao;

import java.util.List;
import java.util.Optional;

public interface DAO<T, ID> {
    T save(T t) throws DAOException;
    Optional<T> findById(ID id) throws DAOException;
    List<T> findAll() throws DAOException;
    boolean delete(ID id) throws DAOException;
}
