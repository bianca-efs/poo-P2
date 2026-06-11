package dao;

import java.sql.SQLException;
import java.util.List;

import entity.Products;

public interface ProductsDAO {
    public void cadastrar(Products p) throws SQLException;
    public void apagar(Products p) throws SQLException;
    public void atualizar(int id, Products p) throws SQLException;
    public List<Products> pesquisar(String name);
}
