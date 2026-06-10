package control;

import java.sql.SQLException;
import java.time.LocalDate;

import dao.FuncionarioDAO;
import dao.FuncionarioDAOImplementation;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import entity.Funcionario;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class FuncionarioControl {

	private ObservableList<Funcionario> lista = FXCollections.observableArrayList();
	
	private IntegerProperty id = new SimpleIntegerProperty(0);
	private StringProperty nome = new SimpleStringProperty("");
	private DoubleProperty salario = new SimpleDoubleProperty(0);
	private StringProperty cpf = new SimpleStringProperty("");
	private ObjectProperty<LocalDate> dataContrato = new SimpleObjectProperty<>(LocalDate.now());	
	private StringProperty email = new SimpleStringProperty("");
	private StringProperty cargo = new SimpleStringProperty("");
	private StringProperty setor = new SimpleStringProperty("");
	
	private FuncionarioDAO dao = new FuncionarioDAOImplementation();
	
	public FuncionarioControl() { 
        try {
			carregar();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void fromEntity (Funcionario f) {
		if (f != null) {
			id.set(f.getId());
			nome.set(f.getNome());
			salario.set(f.getSalario());
			cpf.set(f.getCpf());
			dataContrato.set(f.getDataContrato());
			cargo.set(f.getCargo());
			setor.set(f.getSetor());
			email.set(f.getEmail());
		}
	}
	
	public Funcionario toEntity () {
		Funcionario f = new Funcionario();
		f.setId(id.get());
		f.setNome(nome.get());
		f.setSalario(salario.get());
		f.setCpf(cpf.get());
		f.setDataContrato(dataContrato.get());
		f.setCargo(cargo.get());
		f.setSetor(setor.get());
		f.setEmail(email.get());
		return f;
	}
	
	public void limparCampos() {
		id.set(0);
		nome.set("");
		salario.set(0);
		cpf.set("");
		dataContrato.set(LocalDate.now());
		cargo.set("");
		setor.set("");
		email.set("");
	}
	
	public void salvar() throws SQLException {
		if (nome.get().isEmpty()) {
			mostrarErro("É necessário preencher o nome");		    
			return;
		} else if (cpf.get().isEmpty() || cpf.get().length() != 11) {
			mostrarErro("É necessário preencher um CPF de 11 digitos");
		    return;
		}else if (salario.get() <= 0) {
			mostrarErro("É necessário que o salário seja maior que 0");
		    return;
		} else if (email.get().isEmpty() || !email.get().contains("@")) {
			mostrarErro("É necessário preencher um e-mail válido");
		    return;
		} else if (cargo.get().isEmpty()) {
			mostrarErro("É necessário preencher o cargo");
		    return;
		} else if (setor.get().isEmpty()) {
			mostrarErro("É necessário preencher o setor");
		    return;
		} else {
			Funcionario f = toEntity();
			if (id.get() > 0) {
				dao.atualizar(id.get(), f);			
			} else {
				dao.cadastrar(f);
			}
			new Alert(AlertType.INFORMATION, "Funcionário gravado no sistema!").show();
		}
		
		limparCampos();
		carregar();
	}
	
	public void carregar() throws SQLException {
	    lista.clear();
	    lista.addAll(dao.pesquisar(""));
	}
	
	public void apagar(Funcionario f) throws SQLException {
	    dao.apagar(f);
	    carregar();
	}

	public void pesquisar() throws SQLException {
	    lista.clear();
	    lista.addAll(dao.pesquisar(cpf.get()));
	}

    public StringProperty cpfProperty() {
        return cpf;
    }

    public StringProperty nomeProperty() {
        return nome;
    }

    public DoubleProperty salarioProperty() {
        return salario;
    }

    public StringProperty emailProperty() {
        return email;
    }

    public StringProperty cargoProperty() {
        return cargo;
    }

    public StringProperty setorProperty() {
        return setor;
    }
    
    public ObjectProperty<LocalDate> dataContratoProperty() {
        return dataContrato;
    }
    
    private void mostrarErro(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
    
    public ObservableList<Funcionario> getLista() { 
        return lista;
    }
}
