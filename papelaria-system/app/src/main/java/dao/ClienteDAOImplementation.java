package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.Cliente;

public class ClienteDAOImplementation implements ClienteDAO {

	private static final String DB_URI = "jdbc:mysql://localhost:3306/papelaria?useSSL=false&serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "1234";
    private Connection con;
    
    public ClienteDAOImplementation() {
    	try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(DB_URI, DB_USER, DB_PASS);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
    }
	
	@Override
	public void cadastrar(Cliente c) throws SQLException {
		String sql = "INSERT INTO cliente (nome, cpf, email, telefone, dataNascimento, dataCadastro) values (?, ?, ?, ?, ?, ?)";
			PreparedStatement stm = con.prepareStatement(sql);
			
			stm.setString(1, c.getNome());
			stm.setString(2, c.getCpf());
			stm.setString(3, c.getEmail());
			stm.setString(4, c.getTelefone());
			stm.setDate(5, java.sql.Date.valueOf(c.getDataNascimento()));
			stm.setDate(6, java.sql.Date.valueOf(c.getDataCadastro()));
			stm.executeUpdate();
	}

	@Override
	public void apagar(Cliente c) throws SQLException {
		String sql = "DELETE FROM cliente WHERE id = ?";
		PreparedStatement stm = con.prepareStatement(sql);
		stm.setInt(1, c.getId());
		stm.executeUpdate();
	}

	@Override
	public void atualizar(int id, Cliente c) throws SQLException {
		String sql = "UPDATE cliente SET nome=?, cpf=?, email=?, telefone=?, dataNascimento=?, dataCadastro=? WHERE ID=?";
		PreparedStatement stm = con.prepareStatement(sql);
		
		stm.setString(1, c.getNome());
		stm.setString(2, c.getCpf());
		stm.setString(3, c.getEmail());
		stm.setString(4, c.getTelefone());
		stm.setDate(5, java.sql.Date.valueOf(c.getDataNascimento()));
		stm.setDate(6, java.sql.Date.valueOf(c.getDataCadastro()));
		stm.setInt(7, id);
		stm.executeUpdate();
	}

	@Override
	public List<Cliente> pesquisar(String cpf) {
		List <Cliente> lista = new ArrayList<>();
		String sql = "SELECT * FROM cliente WHERE cpf LIKE ?";
        try {
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setString(1, "%" + cpf + "%");
			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
				Cliente c = new Cliente();
				
				c.setId(rs.getInt("id"));
				c.setNome(rs.getString("nome"));
				c.setCpf(rs.getString("cpf"));
				c.setEmail(rs.getString("email"));
				c.setTelefone(rs.getString("telefone"));
				c.setDataNascimento(rs.getDate("dataNascimento").toLocalDate());
				c.setDataCadastro(rs.getDate("dataCadastro").toLocalDate());

				lista.add(c);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}

}
