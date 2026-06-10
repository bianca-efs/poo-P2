package dao;

import java.util.List;
import entity.Funcionario;

public interface FuncionarioDAO {
    void cadastrar(Funcionario f);
    void apagar(Funcionario f);
    void atualizar(int id, Funcionario f);
    List <Funcionario> pesquisarPorCPF(String cpf);
}