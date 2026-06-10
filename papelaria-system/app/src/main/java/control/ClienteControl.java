package control;

import java.sql.SQLException;
import java.time.LocalDate;

import dao.ClienteDAO;
import dao.ClienteDAOImplementation;
import entity.Cliente;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ClienteControl {

	private ObservableList<Cliente> lista = FXCollections.observableArrayList();
	private IntegerProperty id = new SimpleIntegerProperty(0);
	private StringProperty nome = new SimpleStringProperty("");
	private StringProperty cpf = new SimpleStringProperty("");
	private StringProperty email = new SimpleStringProperty("");
	private StringProperty telefone = new SimpleStringProperty("");
	private ObjectProperty<LocalDate> dataNascimento = new SimpleObjectProperty<>(LocalDate.now());	
	private ObjectProperty<LocalDate> dataCadastro = new SimpleObjectProperty<>(LocalDate.now());	

	private ClienteDAO dao = new ClienteDAOImplementation();

	public ClienteControl() { 
        carregar();
	}

	private void carregar() {
		lista.clear();
	    lista.addAll(dao.pesquisar(""));
	}
	
	public void fromEntity (Cliente c) {
		if (c != null) {
			id.set(c.getId());
			nome.set(c.getNome());
			cpf.set(c.getCpf());
			email.set(c.getEmail());
			telefone.set(c.getTelefone());
			dataNascimento.set(c.getDataNascimento());
			dataCadastro.set(c.getDataCadastro());
		}
	}
	
	public Cliente toEntity() {
		Cliente c = new Cliente();
		c.setId(id.get());
		c.setNome(nome.get());
		c.setCpf(cpf.get());
		c.setEmail(email.get());
		c.setTelefone(telefone.get());
		c.setDataNascimento(dataNascimento.get());
		c.setDataCadastro(dataCadastro.get());
		return c;
	}
	
	public void  limparCampos() {
		id.set(0);
		nome.set("");
		cpf.set("");
		email.set("");
		telefone.set("");
		dataNascimento.set(LocalDate.now());
		dataCadastro.set(LocalDate.now());
	}
	
	public void salvar() throws SQLException{
		if (nome.get().isEmpty()) {
			mostrarErro("É necessário preencher o nome");	
			return;
		} else if (cpf.get().isEmpty() || cpf.get().length() != 11) {
			mostrarErro("É necessário preencher um CPF de 11 digitos");
			return;
		} else if (email.get().isEmpty() || !email.get().contains("@")) {
			mostrarErro("É necessário preencher um e-mail válido");
			return;
		} else if (telefone.get().isEmpty()) {
			mostrarErro("É necessário preencher o telefone");		    
			return;
		} else {
			Cliente c = toEntity();
			if (id.get() > 0) {
				dao.atualizar(id.get(), c);
			} else {
				dao.cadastrar(c);
			}
			
			new Alert(AlertType.INFORMATION, "Cliente gravado no sistema!").show();
		}
		limparCampos();
		carregar();
	}

	private void mostrarErro(String msg) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
	}
	
	public void apagar(Cliente c) throws SQLException {
		dao.apagar(c);
		carregar();
	}
	
	public void pesquisar() throws SQLException {
		lista.clear();
		lista.addAll(dao.pesquisar(cpf.get()));
	}
	
	public StringProperty nomeProperty() {
	    return nome;
	}

	public StringProperty cpfProperty() {
	    return cpf;
	}

	public ObjectProperty<LocalDate> dataNascimentoProperty() {
	    return dataNascimento;
	}

	public StringProperty emailProperty() {
	    return email;
	}

	public StringProperty telefoneProperty() {
	    return telefone;
	}
	
	public ObservableList<Cliente> getLista() { 
        return lista;
    }

	public ObjectProperty<LocalDate> dataCadastroProperty() {
	    return dataCadastro;
	}
}
