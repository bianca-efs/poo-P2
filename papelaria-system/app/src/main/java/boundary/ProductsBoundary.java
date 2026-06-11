package boundary;

import java.sql.SQLException;
import java.util.Optional;

import application.Tela;
import control.ProductsControl;
import entity.Products;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.NumberStringConverter;

public class ProductsBoundary implements Tela {
    private Stage stage;
    private ProductsControl c = new ProductsControl();
    private TableView<Products> table = new TableView<>();

    private TextField txtName = new TextField();
    private TextField txtPrice = new TextField();
    private TextField txtQuantity = new TextField();
    private TextField txtSku = new TextField();

    public ProductsBoundary(Stage stage) {
        this.stage = stage;
    }

    @Override
    public Pane render() {
        BorderPane principal = new BorderPane();
        GridPane gap = new GridPane();

        gap.setHgap(20);
        gap.setVgap(20);
        gap.setPadding(new javafx.geometry.Insets(10));

        gap.add(new Label("Nome"), 0, 0);
        gap.add(txtName, 1, 0);
        gap.add(new Label("Preço"), 0, 1);
        gap.add(txtPrice, 1, 1);
        gap.add(new Label("Quantidade"), 0, 2);
        gap.add(txtQuantity, 1, 2);
        gap.add(new Label("SKU"), 0, 3);
        gap.add(txtSku, 1, 3);

        principal.setTop(gap);
        principal.setCenter(table);

        Button btnSalvar = new Button("Salvar");
        btnSalvar.setOnAction((e) -> {
            try {
                c.salvar();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        Button btnPesquisar = new Button("Pesquisar");
        btnPesquisar.setOnAction((e) -> {
            try {
                c.pesquisar();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });

        Button btnApagar = new Button("Apagar");
        btnApagar.setOnAction((e) -> {
            c.limpar();
        });

        Button btnVoltar = new Button("Menu");
        btnVoltar.setOnAction((e) -> {
            MenuBoundary menu = new MenuBoundary(stage);
            stage.setScene(new Scene(menu.render(), 900, 600));
        });

        Image img = new Image(getClass().getResourceAsStream("/images/casa.png"));
        ImageView imgView = new ImageView(img);

        gap.add(btnApagar, 0, 6);
        gap.add(btnSalvar, 2, 0);
        gap.add(btnPesquisar, 2, 1);
        gap.add(btnVoltar, 23, 0);

        Bindings.bindBidirectional(txtName.textProperty(), c.nameProperty());
        Bindings.bindBidirectional(txtPrice.textProperty(), c.priceProperty(), new NumberStringConverter());
        Bindings.bindBidirectional(txtQuantity.textProperty(), c.quantityProperty(), new NumberStringConverter());
        Bindings.bindBidirectional(txtSku.textProperty(), c.skuProperty());

        TableColumn<Products, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(itemData -> new ReadOnlyStringWrapper(String.valueOf(itemData.getValue().getId())));
        TableColumn<Products, String> colPrice = new TableColumn<>("Preço");
        colPrice.setCellValueFactory(
                itemData -> new ReadOnlyStringWrapper(String.valueOf(itemData.getValue().getPrice())));
        TableColumn<Products, String> colName = new TableColumn<>("Name");
        colName.setCellValueFactory(itemData -> new ReadOnlyStringWrapper(itemData.getValue().getName()));
        TableColumn<Products, String> colQuantity = new TableColumn<>("Quamtidade");
        colQuantity.setCellValueFactory(
                itemData -> new ReadOnlyStringWrapper(String.valueOf(itemData.getValue().getQuantity())));
        TableColumn<Products, String> colSku = new TableColumn<>("SKU");
        colSku.setCellValueFactory(itemData -> new ReadOnlyStringWrapper(itemData.getValue().getSku()));

        TableColumn<Products, Void> colAction = new TableColumn<>("Açoes");

        table.getSelectionModel().selectedItemProperty().addListener((obj, anttigo, novo) -> c.fromEntity(novo));
        table.getColumns().add(colId);
        table.getColumns().add(colName);
        table.getColumns().add(colPrice);
        table.getColumns().add(colQuantity);
        table.getColumns().add(colSku);

        table.getColumns().add(colAction);

        table.setItems(c.getLista());

        Callback<TableColumn<Products, Void>, TableCell<Products, Void>> callback = new Callback<>() {

            @Override
            public TableCell<Products, Void> call(TableColumn<Products, Void> column) {
                return new TableCell<Products, Void>() {

                    Button btnApagar = new Button();
                    Button btnEditar = new Button();

                    {
                        Image iconDelete = new Image(getClass().getResourceAsStream("/images/bin.png"));
                        ImageView imgViewDelete = new ImageView(iconDelete);
                        imgViewDelete.setFitWidth(25);
                        imgViewDelete.setFitHeight(25);
                        btnApagar.setGraphic(imgViewDelete);

                        btnApagar.setOnAction(e -> {
                            Products produto = getTableView().getItems().get(getIndex());

                            Alert alert = new Alert(
                                    AlertType.CONFIRMATION,
                                    "Deletar produto?",
                                    ButtonType.YES,
                                    ButtonType.NO);

                            Optional<ButtonType> result = alert.showAndWait();

                            if (result.isPresent() && result.get() == ButtonType.YES) {
                                try {
                                    c.apagar(produto);
                                } catch (SQLException e1) {
                                    e1.printStackTrace();
                                }
                            }
                        });

                        Image img = new Image(getClass().getResourceAsStream("/images/editar.png"));
                        ImageView imgViewEdit = new ImageView(img);
                        imgViewEdit.setFitWidth(25);
                        imgViewEdit.setFitHeight(25);
                        btnEditar.setGraphic(imgViewEdit);

                        btnEditar.setOnAction(e -> {
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Confirmação de edição");
                            alert.setHeaderText(null);
                            alert.setContentText("Deseja salvar as alterações deste produto?");

                            Optional<ButtonType> result = alert.showAndWait();

                            if (result.isPresent() && result.get() == ButtonType.OK) {
                                try {
                                    c.salvar();
                                } catch (Exception e1) {
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

        colAction.setCellFactory(callback);

        return principal;
    }

}
