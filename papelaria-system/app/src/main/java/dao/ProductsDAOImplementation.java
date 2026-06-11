package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.Products;

public class ProductsDAOImplementation implements ProductsDAO {
    private static final String DB_URI = "jdbc:mysql://localhost:3306/papelaria?useSSL=false&serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "1234";
    private Connection con;

    public ProductsDAOImplementation() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(DB_URI, DB_USER, DB_PASS);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void cadastrar(Products p) throws SQLException {
        String sql = "INSERT INTO products (nome, sku, price, quantity, sku) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement stm = con.prepareStatement(sql);

        stm.setString(1, p.getName());
        stm.setDouble(2, p.getPrice());
        stm.setInt(3, p.getQuantity());
        stm.setString(4, p.getSku());
        stm.executeUpdate();

    }

    @Override
    public void apagar(Products p) throws SQLException {
        String sql = "DELETE FROM products WHERE id = ?";
        PreparedStatement stm = con.prepareStatement(sql);

        stm.setInt(1, p.getId());
        stm.executeUpdate();

    }

    @Override
    public void atualizar(int id, Products p) throws SQLException {
        String sql = "UPDATE products SET name=?, price=?, quantity=?, sku=? WHERE id=?";
        PreparedStatement stm = con.prepareStatement(sql);

        stm.setString(1, p.getName());
        stm.setDouble(2, p.getPrice());
        stm.setInt(3, p.getQuantity());
        stm.setString(4, p.getSku());
        stm.executeUpdate();

    }


    @Override
    public List<Products> pesquisar(String name) {
        List<Products> lista = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE cpf LIKE ?";
        try {
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, "%" + name + "%");
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Products p = new Products();

                p.setId(rs.getInt("id"));
                p.setName(rs.getString("nome"));
                p.setPrice(rs.getDouble("Preço"));
                p.setQuantity(rs.getInt("Estoque"));
                p.setSku(rs.getString("SKU"));

                lista.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}
