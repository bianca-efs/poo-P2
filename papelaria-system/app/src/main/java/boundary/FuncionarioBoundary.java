package boundary;


import java.time.format.DateTimeFormatter;
import java.util.Optional;

import application.Tela;
import control.FuncionarioControl;
import entity.Funcionario;

import javafx.util.converter.NumberStringConverter;
import java.time.LocalDate;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.converter.LocalDateStringConverter;
import javafx.scene.image.ImageView;

public class FuncionarioBoundary implements Tela{

	private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private FuncionarioControl control = new FuncionarioControl();
	private TableView<Funcionario> table = new TableView<>();
	
	private ObservableList<String> setores = FXCollections.observableArrayList("Vendas", "Limpeza", "Recursos Humanos", "Marketing", "Financeiro", "Administração", "Estoque"); 
	private TextField txtCPF = new TextField();
	private TextField txtNome = new TextField();
	private TextField txtSalario = new TextField();
	private TextField txtCargo = new TextField();
	private TextField txtEmail = new TextField();
    private DatePicker pDataContrato = new DatePicker();
	private ComboBox<String> cmbSetor = new ComboBox<>();

	@Override
	public Pane render() {
		BorderPane panPrincipal = new BorderPane();
        GridPane paneCampos = new GridPane();
        cmbSetor.setItems(setores);
        
        paneCampos.add(new Label("Nome:"), 0, 0);
        paneCampos.add(txtNome, 1, 0);
        paneCampos.add(new Label("CPF:"), 0, 1);
        paneCampos.add(txtCPF, 1, 1);
        paneCampos.add(new Label("Salário:"), 0, 2);
        paneCampos.add(txtSalario, 1, 2);
        paneCampos.add(new Label("Cargo:"), 0, 3);
        paneCampos.add(txtCargo, 1, 3);
        paneCampos.add(new Label("E-mail:"), 0, 4);
        paneCampos.add(txtEmail, 1, 4);
        paneCampos.add(new Label("Data Contrato:"), 0, 5);
        paneCampos.add(pDataContrato, 1, 5);
        paneCampos.add(new Label("Setor:"), 0, 6);
        paneCampos.add(cmbSetor, 1, 6);
        
        panPrincipal.setTop( paneCampos );
        panPrincipal.setCenter( table );
        
        Button btnSalvar = new Button("Salvar");
        btnSalvar.setOnAction((e) -> {
        	control.salvar();
        	new Alert(AlertType.INFORMATION, "Funcionário gravado no sistema!").show();
        });
        Button btnPesquisar = new Button("Pesquisar");
        btnPesquisar.setOnAction((e) -> {
            control.pesquisar();
        });
        Button btnApagar = new Button("Apagar");
        btnApagar.setOnAction((e) -> {
        	control.limparCampos();
        });
        
        paneCampos.add(btnApagar, 0, 7);
        paneCampos.add(btnSalvar, 1, 7);
        paneCampos.add(btnPesquisar, 2, 7);
        
        StringConverter<? extends LocalDate> cvt = new LocalDateStringConverter();
        
        Bindings.bindBidirectional(txtCPF.textProperty(), control.cpfProperty());
        Bindings.bindBidirectional(txtNome.textProperty(), control.nomeProperty());
        Bindings.bindBidirectional(txtSalario.textProperty(), control.salarioProperty(), new NumberStringConverter());
        Bindings.bindBidirectional(txtEmail.textProperty(), control.emailProperty());
        Bindings.bindBidirectional(txtCargo.textProperty(), control.cargoProperty());
        Bindings.bindBidirectional(cmbSetor.valueProperty(), control.setorProperty());        
        Bindings.bindBidirectional(pDataContrato.valueProperty(), control.dataContratoProperty());	
        
        TableColumn<Funcionario, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(itemData -> new ReadOnlyStringWrapper(String.valueOf(itemData.getValue().getId())));
        TableColumn<Funcionario, String> colCPF = new TableColumn<>("CPF");
        colCPF.setCellValueFactory(itemData -> new ReadOnlyStringWrapper(itemData.getValue().getCpf()));
        TableColumn<Funcionario, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(itemData -> new ReadOnlyStringWrapper(itemData.getValue().getNome()));
        TableColumn<Funcionario, String> colSalario = new TableColumn<>("Salário");
        colSalario.setCellValueFactory(itemData -> new ReadOnlyStringWrapper(String.valueOf(itemData.getValue().getSalario())));
        TableColumn<Funcionario, String> colEmail = new TableColumn<>("E-mail");
        colEmail.setCellValueFactory(itemData -> new ReadOnlyStringWrapper(itemData.getValue().getEmail()));
        TableColumn<Funcionario, String> colCargo = new TableColumn<>("Cargo");
        colCargo.setCellValueFactory(itemData -> new ReadOnlyStringWrapper(itemData.getValue().getCargo()));
        TableColumn<Funcionario, String> colSetor = new TableColumn<>("Setor");
        colSetor.setCellValueFactory(itemData -> new ReadOnlyStringWrapper(itemData.getValue().getSetor()));
        TableColumn<Funcionario, String> colDataContrato = new TableColumn<>("Data Contrato");
        colDataContrato.setCellValueFactory( itemData -> new ReadOnlyStringWrapper(itemData.getValue().getDataContrato().format(dtf)));
        
        TableColumn<Funcionario, Void> colAcoes = new TableColumn<>("Ações");
        
        table.getSelectionModel().selectedItemProperty().addListener((obj, antigo, novo) -> control.fromEntity(novo));
        table.getColumns().add( colId );
        table.getColumns().add( colCPF );
        table.getColumns().add( colNome );
        table.getColumns().add( colSalario );
        table.getColumns().add( colEmail );
        table.getColumns().add( colCargo );
        table.getColumns().add( colSetor );
        table.getColumns().add( colDataContrato );
        
        table.setItems(control.getLista());
        
        Callback<TableColumn<Funcionario, Void>, TableCell<Funcionario, Void>>
        	callback = new Callback<>(){ 
        		public TableCell<Funcionario, Void> call(TableColumn<Funcionario, Void> column) {
        			return new TableCell<Funcionario, Void>(){
                        Button btnApagar = new Button();

                        {
                            Image iconDelete = new Image(getClass().getResourceAsStream("/images/bin.png"));
                            ImageView imgViewDelete = new ImageView(iconDelete);
                            imgViewDelete.setFitWidth(25);
                            imgViewDelete.setFitHeight(25);
                            btnApagar.setGraphic(imgViewDelete);
                            btnApagar.setOnAction(e -> {
                                Funcionario f = getTableView().getItems().get(getIndex());
                                Alert alert = new Alert(AlertType.CONFIRMATION, "Deletar Funcionário?", ButtonType.YES, ButtonType.NO);
                                Optional<ButtonType> result = alert.showAndWait();
                                if (result.isPresent() && result.get() == ButtonType.YES) {
                                    control.apagar(f);
                                }
                            });
                        }
                        
                        public void updateItem(Void parm, boolean empty) {    
                            if (!empty) {
                                setGraphic(btnApagar);
                            } else {
                                setGraphic(null);
                            }
                        }
                    };
                }
        };

        colAcoes.setCellFactory(callback);
        return panPrincipal;
    }
}
        