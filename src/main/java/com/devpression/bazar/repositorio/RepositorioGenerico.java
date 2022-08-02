package com.devpression.bazar.repositorio;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public interface RepositorioGenerico<T,I> {
    public void create(T t) throws SQLException, ClassNotFoundException;
    public void update(T t) throws SQLException, ClassNotFoundException;
    public T read(I i) throws SQLException, ClassNotFoundException, ParseException;
    public void delete(I i) throws SQLException, ClassNotFoundException;
    public List<T> readAll() throws SQLException, ClassNotFoundException, ParseException;
}
