package boundary;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import application.Tela;
import control.FornecedorControl;
import entity.Fornecedor;
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

public class FornecedorBoundary implements Tela{

	private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private FornecedorControl control = new FornecedorControl();
	private TableView<Fornecedor> table = new TableView<>();
	
	private TextField txtCNPJ = new TextField();
	private TextField txtNome = new TextField();
	private TextField txtEmail = new TextField();
	private TextField txtTelefone = new TextField();
	private TextField txtEndereco = new TextField();
    private DatePicker pDataContrato = new DatePicker();
	private Stage stage;
	
	public FornecedorBoundary(Stage stage) {
		this.stage = stage;
	}
	
	@Override
	public Pane render() {
		BorderPane panPrincipal = new BorderPane();
		GridPane paneCampos = new GridPane();
		paneCampos.setHgap(10);
		paneCampos.setVgap(10);
        paneCampos.setPadding(new javafx.geometry.Insets(10));

        paneCampos.add(new Label("Nome:"), 0, 0);
        paneCampos.add(txtNome, 1, 0);
        paneCampos.add(new Label("CNPJ:"), 0, 1);
        paneCampos.add(txtCNPJ, 1, 1);
        paneCampos.add(new Label("E-mail:"), 0, 2);
        paneCampos.add(txtEmail, 1, 2);
        paneCampos.add(new Label("Telefone:"), 0, 3);
        paneCampos.add(txtTelefone, 1, 3);
        paneCampos.add(new Label("Data Contrato:"), 0, 4);
        paneCampos.add(pDataContrato, 1, 4);
        paneCampos.add(new Label("Endereço:"), 0, 5);
        paneCampos.add(txtEndereco, 1, 5);
        
        panPrincipal.setTop(paneCampos);
        panPrincipal.setCenter(table);
        
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
        
        Bindings.bindBidirectional(txtCNPJ.textProperty(), control.CNPJProperty());
        Bindings.bindBidirectional(txtNome.textProperty(), control.nomeProperty());
        Bindings.bindBidirectional(txtEmail.textProperty(), control.emailProperty());
        Bindings.bindBidirectional(txtTelefone.textProperty(), control.telefoneProperty());
        Bindings.bindBidirectional(pDataContrato.valueProperty(), control.dataContratoProperty());
        Bindings.bindBidirectional(txtEndereco.textProperty(), control.enderecoProperty());

        TableColumn<Fornecedor, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(itemData -> new ReadOnlyStringWrapper(String.valueOf(itemData.getValue().getId())));
        TableColumn<Fornecedor, String> colCNPJ = new TableColumn<>("CNPJ");
        colCNPJ.setCellValueFactory(itemData -> new ReadOnlyStringWrapper(itemData.getValue().getCnpj()));
        TableColumn<Fornecedor, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(itemData -> new ReadOnlyStringWrapper(itemData.getValue().getNome()));
        TableColumn<Fornecedor, String> colEmail = new TableColumn<>("E-mail");
        colEmail.setCellValueFactory(itemData -> new ReadOnlyStringWrapper(itemData.getValue().getEmail()));
        TableColumn<Fornecedor, String> colTelefone = new TableColumn<>("Telefone");
        colTelefone.setCellValueFactory(itemData -> new ReadOnlyStringWrapper(itemData.getValue().getTelefone()));
        TableColumn<Fornecedor, String> colDataContrato = new TableColumn<>("Data Contrato");
        colDataContrato.setCellValueFactory( itemData -> new ReadOnlyStringWrapper(itemData.getValue().getDataContrato().format(dtf)));
        TableColumn<Fornecedor, String> colEndereco = new TableColumn<>("Endereço");
        colEndereco.setCellValueFactory(itemData -> new ReadOnlyStringWrapper(itemData.getValue().getEndereco()));
        
        TableColumn<Fornecedor, Void> colAcoes = new TableColumn<>("Ações");
        
        table.getSelectionModel().selectedItemProperty().addListener((obj, antigo, novo) -> control.fromEntity(novo));
        table.getColumns().add(colId);
        table.getColumns().add(colCNPJ);
        table.getColumns().add(colNome);
        table.getColumns().add(colEmail);
        table.getColumns().add(colTelefone);
        table.getColumns().add(colDataContrato);
        table.getColumns().add(colEndereco);
        
        table.getColumns().add(colAcoes);
        
        table.setItems(control.getLista());
        
        Callback<TableColumn<Fornecedor, Void>, TableCell<Fornecedor, Void>>
    	callback = new Callback<>(){ 
    		public TableCell<Fornecedor, Void> call(TableColumn<Fornecedor, Void> column) {
    			return new TableCell<Fornecedor, Void>(){
                    Button btnApagar = new Button();

                    {
                        Image iconDelete = new Image(getClass().getResourceAsStream("/images/bin.png"));
                        ImageView imgViewDelete = new ImageView(iconDelete);
                        imgViewDelete.setFitWidth(25);
                        imgViewDelete.setFitHeight(25);
                        btnApagar.setGraphic(imgViewDelete);
                        btnApagar.setOnAction(e -> {
                            Fornecedor fr = getTableView().getItems().get(getIndex());
                            Alert alert = new Alert(AlertType.CONFIRMATION, "Deletar Fornecedor?", ButtonType.YES, ButtonType.NO);
                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.isPresent() && result.get() == ButtonType.YES) {
                                try {
									control.apagar(fr);
								} catch (SQLException e1) {
									// TODO Auto-generated catch block
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
                        	    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        	    alert.setTitle("Confirmação de edição");
                        	    alert.setHeaderText(null);
                        	    alert.setContentText("Deseja salvar as alterações deste fornecedor?");
                        	    Optional<ButtonType> result = alert.showAndWait();
                        	    if (result.isPresent() && result.get() == ButtonType.OK) {
                        	        try {
										control.salvar();
									} catch (SQLException e1) {
										// TODO Auto-generated catch block
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
