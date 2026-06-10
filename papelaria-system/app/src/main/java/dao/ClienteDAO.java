package dao;

import java.sql.SQLException;
import java.util.List;

import entity.Cliente;

public interface ClienteDAO {
	void cadastrar(Cliente c) throws SQLException;
	void apagar(Cliente c) throws SQLException;
	void atualizar(int id, Cliente c) throws SQLException;
	List <Cliente> pesquisar(String cpf);
}
