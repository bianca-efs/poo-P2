package boundary;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import application.Tela;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;
import control.ClienteControl;
import entity.Cliente;

public class ClienteBoundary implements Tela{

	private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private ClienteControl control = new ClienteControl();
	private TableView<Cliente> table = new TableView<>();
	
	private TextField txtCPF = new TextField();
	private TextField txtNome = new TextField();
	private TextField txtEmail = new TextField();
	private TextField txtTelefone = new TextField();
	private DatePicker txtDataNascimento = new DatePicker();
	private DatePicker txtDataCadastro = new DatePicker();
	private Stage stage;
	
	public ClienteBoundary(Stage stage) {
		this.stage = stage;
	}
	
	@Override
	public Pane render() {
		BorderPane panPrincipal = new BorderPane();
        GridPane paneCampos = new GridPane();
        paneCampos.setHgap(10); 
        paneCampos.setVgap(10); 
        paneCampos.setPadding(new javafx.geometry.Insets(10));
        
        paneCampos.add(new Label("CPF:"), 0, 0);
        paneCampos.add(txtCPF, 1, 0);
        paneCampos.add(new Label("Nome:"), 0, 1);
        paneCampos.add(txtNome, 1, 1);
        paneCampos.add(new Label("E-mail:"), 0, 2);
        paneCampos.add(txtEmail, 1, 2);
        paneCampos.add(new Label("Telefone:"), 0, 3);
        paneCampos.add(txtTelefone, 1, 3);
        paneCampos.add(new Label("Data Nascimento:"), 0, 4);
        paneCampos.add(txtDataNascimento, 1, 4);
        paneCampos.add(new Label("Data Cadastro:"), 0, 5);
        paneCampos.add(txtDataCadastro, 1, 5);
        
        panPrincipal.setTop( paneCampos );
        panPrincipal.setCenter( table );
        
        Button btnSalvar = new Button("Salvar");
        btnSalvar.setOnAction((e) -> {
        	try {
				control.salvar();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        });
        Button btnPesquisar = new Button("Pesquisar");
        btnPesquisar.setOnAction((e) -> {
            try {
				control.pesquisar();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        });
        Button btnApagar = new Button("Limpar Campos");
        btnApagar.setOnAction((e) -> {
        	control.limparCampos();
        });
        Button btnVoltar = new Button("Menu");
        btnVoltar.setOnAction(e -> {
            MenuBoundary menu = new MenuBoundary(stage);
            stage.setScene(new Scene(menu.render(), 900, 600));
        });
        
        Image iconEdit = new Image(getClass().getResourceAsStream("/images/casa.png"));
        ImageView imgViewMenu = new ImageView(iconEdit);
        imgViewMenu.setFitWidth(25);
        imgViewMenu.setFitHeight(25);
        btnVoltar.setGraphic(imgViewMenu);
        
        paneCampos.add(btnApagar, 0, 6);
        paneCampos.add(btnSalvar, 2, 0);
        paneCampos.add(btnPesquisar, 2, 1);
        paneCampos.add(btnVoltar, 23, 0);
        
        Bindings.bindBidirectional(txtCPF.textProperty(), control.cpfProperty());
        Bindings.bindBidirectional(txtNome.textProperty(), control.nomeProperty());
        Bindings.bindBidirectional(txtTelefone.textProperty(), control.telefoneProperty());
        Bindings.bindBidirectional(txtEmail.textProperty(), control.emailProperty());
        Bindings.bindBidirectional(txtDataCadastro.valueProperty(), control.dataCadastroProperty());
        Bindings.bindBidirectional(txtDataNascimento.valueProperty(), control.dataNascimentoProperty());	

        TableColumn<Cliente, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(itemData -> new ReadOnlyStringWrapper(String.valueOf(itemData.getValue().getId())));
        TableColumn<Cliente, String> colCPF = new TableColumn<>("CPF");
        colCPF.setCellValueFactory(itemData -> new ReadOnlyStringWrapper(itemData.getValue().getCpf()));
        TableColumn<Cliente, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(itemData -> new ReadOnlyStringWrapper(itemData.getValue().getNome()));
        TableColumn<Cliente, String> colEmail = new TableColumn<>("E-mail");
        colEmail.setCellValueFactory(itemData -> new ReadOnlyStringWrapper(String.valueOf(itemData.getValue().getEmail())));
        TableColumn<Cliente, String> colTelefone = new TableColumn<>("Telefonel");
        colTelefone.setCellValueFactory(itemData -> new ReadOnlyStringWrapper(itemData.getValue().getTelefone()));
        TableColumn<Cliente, String> colDataNascimento = new TableColumn<>("Data Nascimento");
        colDataNascimento.setCellValueFactory( itemData -> new ReadOnlyStringWrapper(itemData.getValue().getDataNascimento().format(dtf)));
        TableColumn<Cliente, String> colDataCadastro = new TableColumn<>("Data Cadastro");
        colDataCadastro.setCellValueFactory( itemData -> new ReadOnlyStringWrapper(itemData.getValue().getDataCadastro().format(dtf)));
		
        TableColumn<Cliente, Void> colAcoes = new TableColumn<>("Ações");
        
        table.getSelectionModel().selectedItemProperty().addListener((obj, antigo, novo) -> control.fromEntity(novo));
        table.getColumns().add(colId);
        table.getColumns().add(colCPF);
        table.getColumns().add(colNome);
        table.getColumns().add(colEmail);
        table.getColumns().add(colTelefone);
        table.getColumns().add(colDataCadastro);
        table.getColumns().add(colDataNascimento);
        
        table.getColumns().add(colAcoes);
        table.setItems(control.getLista());

        Callback<TableColumn<Cliente, Void>, TableCell<Cliente, Void>> callback = new Callback<>(){ 
    		public TableCell<Cliente, Void> call(TableColumn<Cliente, Void> column) {
    			return new TableCell<Cliente, Void>(){
                    Button btnApagar = new Button();

                    {
                        Image iconDelete = new Image(getClass().getResourceAsStream("/images/bin.png"));
                        ImageView imgViewDelete = new ImageView(iconDelete);
                        imgViewDelete.setFitWidth(25);
                        imgViewDelete.setFitHeight(25);
                        btnApagar.setGraphic(imgViewDelete);
                        btnApagar.setOnAction(e -> {
                            Cliente c = getTableView().getItems().get(getIndex());
                            Alert alert = new Alert(AlertType.CONFIRMATION, "Deletar Cliente?", ButtonType.YES, ButtonType.NO);
                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.isPresent() && result.get() == ButtonType.YES) {
                                try {
									control.apagar(c);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
                            }
                        });
                    }
                    
                    
                    Button btnEditar = new Button();

                    {
                        Image iconEdit = new Image(getClass().getResourceAsStream("/images/editar.png"));
                        ImageView imgViewEdit = new ImageView(iconEdit);
                        imgViewEdit.setFitWidth(25);
                        imgViewEdit.setFitHeight(25);
                        btnEditar.setGraphic(imgViewEdit);
                        btnEditar.setOnAction(e -> {
                        	Cliente c = getTableView().getItems().get(getIndex());                        	    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        	    alert.setTitle("Confirmação de edição");
                        	    alert.setHeaderText(null);
                        	    alert.setContentText("Deseja salvar as alterações deste cliente?");
                        	    Optional<ButtonType> result = alert.showAndWait();
                        	    if (result.isPresent() && result.get() == ButtonType.OK) {
                        	        try {
										control.salvar();
									} catch (SQLException e1) {
										e1.printStackTrace();
									}
                        	    }
                        });
                    }
                    
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            HBox box = new HBox(5);
                            box.getChildren().addAll(btnEditar, btnApagar);
                            setGraphic(box);
                        }
                    }
                };
            }
    };
    colAcoes.setCellFactory(callback);
    return panPrincipal;
	}
}
