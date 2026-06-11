package dao;

import java.util.List;

import entity.Fornecedor;

public interface FornecedorDAO {
	void cadastrar(Fornecedor fr);
	void apagar(Fornecedor fr);
	void atualizar(int id, Fornecedor fr);
	List<Fornecedor> pesquisar(String cnpj);
}
