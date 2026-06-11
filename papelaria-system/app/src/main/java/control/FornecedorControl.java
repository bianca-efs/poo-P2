package control;

import java.sql.SQLException;
import java.time.LocalDate;

import dao.FornecedorDAO;
import dao.FornecedorDAOImplementation;
import entity.Fornecedor;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class FornecedorControl {
	private ObservableList<Fornecedor> lista = FXCollections.observableArrayList();
	
	private IntegerProperty id = new SimpleIntegerProperty(0);
	private StringProperty nome = new SimpleStringProperty("");
	private StringProperty cnpj = new SimpleStringProperty("");
	private StringProperty email = new SimpleStringProperty("");
	private StringProperty telefone = new SimpleStringProperty("");
	private ObjectProperty<LocalDate> dataContrato = new SimpleObjectProperty<>(LocalDate.now());	
	private StringProperty endereco = new SimpleStringProperty("");
	
	private FornecedorDAO dao = new FornecedorDAOImplementation();
	
	public FornecedorControl() { 
		carregar();	
	}

	public void fromEntity (Fornecedor fr) {
		if (fr != null) {
			id.set(fr.getId());
			nome.set(fr.getNome());
			cnpj.set(fr.getCnpj());
			email.set(fr.getEmail());
			telefone.set(fr.getTelefone());
			dataContrato.set(fr.getDataContrato());
			endereco.set(fr.getEndereco());
		}
	}
	
	public Fornecedor toEntity() {
		Fornecedor fr = new Fornecedor();
		fr.setId(id.get());
		fr.setNome(nome.get());
		fr.setCnpj(cnpj.get());
		fr.setEmail(email.get());
		fr.setTelefone(telefone.get());
		fr.setDataContrato(dataContrato.get());
		fr.setEndereco(endereco.get());
		return fr;
	}
	
	public void limparCampos() {
		id.set(0);
		nome.set("");
		cnpj.set("");
		email.set("");
		telefone.set("");
		dataContrato.set(LocalDate.now());
		endereco.set("");
	}
	
	public void salvar() throws SQLException {
		if (nome.get().isEmpty()) {
			mostrarErro("É necessário preencher o nome");		    
			return;
		} else if (cnpj.get().isEmpty() || cnpj.get().length() != 14) {
			mostrarErro("É necessário preencher um CNPJ de 14 digitos");
		    return;
		} else if (email.get().isEmpty() || !email.get().contains("@")) {
			mostrarErro("É necessário preencher um e-mail válido");
		    return;
		} else if (telefone.get().isEmpty()) {
			mostrarErro("É necessário preencher o telefone");
		    return;
		} else if (endereco.get().isEmpty()) {
			mostrarErro("É necessário preencher o endereço");
		    return;
		} else {
			Fornecedor fr = toEntity();
			if (id.get() > 0) {
				dao.atualizar(id.get(), fr);			
			} else {
				dao.cadastrar(fr);
			}
			new Alert(AlertType.INFORMATION, "Fornecedor gravado no sistema!").show();
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
	
	public void apagar(Fornecedor fr) throws SQLException {
	    dao.apagar(fr);
	    carregar();
	}

	private void carregar() {
		lista.clear();
	    lista.addAll(dao.pesquisar(""));
	}
	
	public void pesquisar() throws SQLException {
	    lista.clear();
	    lista.addAll(dao.pesquisar(cnpj.get()));
	}
	
	public ObservableList<Fornecedor> getLista() { 
        return lista;
    }

	public StringProperty CNPJProperty() {
	    return cnpj;
	}

	public StringProperty nomeProperty() {
	    return nome;
	}

	public StringProperty emailProperty() {
	    return email;
	}

	public StringProperty telefoneProperty() {
	    return telefone;
	}

	public ObjectProperty<LocalDate> dataContratoProperty() {
	    return dataContrato;
	}

	public StringProperty enderecoProperty() {
	    return endereco;
	}
}
