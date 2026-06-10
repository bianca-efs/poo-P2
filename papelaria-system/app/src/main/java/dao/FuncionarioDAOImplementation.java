package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import entity.Funcionario;

public class FuncionarioDAOImplementation implements FuncionarioDAO {
    private static final String DB_URI = "jdbc:mysql://localhost:3306/papelaria?useSSL=false&serverTimezone=UTC";

    private static final String DB_USER = "root";
    private static final String DB_PASS = "1234";
    private Connection con;

    public FuncionarioDAOImplementation() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver carregado...");

            con = DriverManager.getConnection(DB_URI, DB_USER, DB_PASS);
            System.out.println("Conectado no MySQL...");

        } catch (ClassNotFoundException e) {
            System.out.println("Erro ao carregar driver");
            e.printStackTrace();

        } catch (SQLException e) {
            System.out.println("Erro ao conectar no banco");
            e.printStackTrace();
        }
    }

    @Override
    public void cadastrar(Funcionario f) {
        try {
            String sql = "INSERT INTO funcionario (nome, cpf, salario, email, cargo, setor, data_contrato) VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement stm = con.prepareStatement(sql);

            stm.setString(1, f.getNome());
            stm.setString(2, f.getCpf());
            stm.setDouble(3, f.getSalario());
            stm.setString(4, f.getEmail());
            stm.setString(5, f.getCargo());
            stm.setString(6, f.getSetor());
            stm.setDate(7, java.sql.Date.valueOf(f.getDataContrato()));

            stm.executeUpdate();
            System.out.println("Funcionário inserido com sucesso");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void apagar(Funcionario f) {
        try {
            String sql = "DELETE FROM funcionario WHERE id = ?";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, f.getId());
            stm.executeUpdate();
            System.out.println("Funcionário removido");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void atualizar(int id, Funcionario f) {
        try {
            String sql = "UPDATE funcionario SET nome=?, cpf=?, salario=?, email=?, cargo=?, setor=?, data_contrato=? WHERE id=?";

            PreparedStatement stm = con.prepareStatement(sql);

            stm.setString(1, f.getNome());
            stm.setString(2, f.getCpf());
            stm.setDouble(3, f.getSalario());
            stm.setString(4, f.getEmail());
            stm.setString(5, f.getCargo());
            stm.setString(6, f.getSetor());
            stm.setDate(7, java.sql.Date.valueOf(f.getDataContrato()));
            stm.setInt(8, id);

            stm.executeUpdate();

            System.out.println("Funcionário atualizado");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Funcionario> pesquisarPorCPF(String cpf) {

        List<Funcionario> lista = new ArrayList<>();

        try {
            String sql = "SELECT * FROM funcionario WHERE cpf LIKE ?";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, "%" + cpf + "%");
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Funcionario f = new Funcionario();

                f.setId(rs.getInt("id"));
                f.setNome(rs.getString("nome"));
                f.setCpf(rs.getString("cpf"));
                f.setSalario(rs.getDouble("salario"));
                f.setEmail(rs.getString("email"));
                f.setCargo(rs.getString("cargo"));
                f.setSetor(rs.getString("setor"));

                LocalDate data = rs.getDate("data_contrato").toLocalDate();
                f.setDataContrato(data);

                lista.add(f);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}