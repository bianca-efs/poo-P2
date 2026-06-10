package dao;

import java.sql.SQLException;
import java.util.List;
import entity.Funcionario;

public interface FuncionarioDAO {
    void cadastrar(Funcionario f) throws SQLException;
    void apagar(Funcionario f) throws SQLException;
    void atualizar(int id, Funcionario f) throws SQLException;
    List<Funcionario> pesquisar(String cpf) throws SQLException;
}