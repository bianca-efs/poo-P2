package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.Fornecedor;
import entity.Funcionario;

public class FornecedorDAOImplementation implements FornecedorDAO{
	private static final String DB_URI = "jdbc:mysql://localhost:3306/papelaria?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "1234";
    private Connection con;
    
    public FornecedorDAOImplementation() {
    	try {
    		 Class.forName("com.mysql.cj.jdbc.Driver");
             System.out.println("Driver carregado...");
             con = DriverManager.getConnection(DB_URI, DB_USER, DB_PASS);
    	} catch (ClassNotFoundException | SQLException e) {
    		e.printStackTrace();
    	}
    }
    
	@Override
	public void cadastrar(Fornecedor fr) {
		try {
			String sql = "INSERT INTO fornecedor (nome, cnpj, email, telefone, dataContrato, endereco) VALUES (?, ?, ?, ?, ?, ?)";
			PreparedStatement stm = con.prepareStatement(sql);
			
			stm.setString(1, fr.getNome());
			stm.setString(2, fr.getCnpj());
			stm.setString(3, fr.getEmail());
			stm.setString(4, fr.getTelefone());
            stm.setDate(5, java.sql.Date.valueOf(fr.getDataContrato()));
            stm.setString(6, fr.getEndereco());
            
            stm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void apagar(Fornecedor fr) {
		try {
			String sql = "DELETE FROM fornecedor WHERE id = ?";
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setInt(1, fr.getId());
			stm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void atualizar(int id, Fornecedor fr) {
		try {
            String sql = "UPDATE fornecedor SET nome=?, cnpj=?, email=?, telefone=?, dataContrato=?, endereco=? WHERE id=?";
            PreparedStatement stm = con.prepareStatement(sql);

            stm.setString(1, fr.getNome());
            stm.setString(2, fr.getCnpj());
            stm.setString(3, fr.getEmail());
            stm.setString(4, fr.getTelefone());
            stm.setDate(5, java.sql.Date.valueOf(fr.getDataContrato()));
            stm.setString(6, fr.getEndereco());
            stm.setInt(7, id);
            stm.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}

	@Override
	public List<Fornecedor> pesquisar(String cnpj) {
		List<Fornecedor> lista = new ArrayList<>();
        try {
            String sql = "SELECT * FROM fornecedor WHERE cnpj LIKE ?";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, "%" + cnpj + "%");
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Fornecedor fr = new Fornecedor();

                fr.setId(rs.getInt("id"));
                fr.setNome(rs.getString("nome"));
                fr.setCnpj(rs.getString("cnpj"));
                fr.setEmail(rs.getString("email"));
                fr.setTelefone(rs.getString("telefone"));
                fr.setDataContrato(rs.getDate("data_contrato").toLocalDate());
                fr.setEndereco(rs.getString("endereco"));
                lista.add(fr);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

}
