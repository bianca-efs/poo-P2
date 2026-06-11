/*package control;

import java.sql.SQLException;
import java.time.LocalDate;

import dao.ProductsDAO;
import dao.ProductsDAOImplementation;
import entity.Products;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ProductsControl {
    private ObservableList<Products> l = FXCollections.observableArrayList();

    private IntegerProperty id = new SimpleIntegerProperty(0);
    private StringProperty name = new SimpleStringProperty("");
    private DoubleProperty price = new SimpleDoubleProperty(0);
    private IntegerProperty quantity = new SimpleIntegerProperty(0);
    private StringProperty sku = new SimpleStringProperty("");

    private ProductsDAO prodao = new ProductsDAOImplementation();

    public ProductsControl() {
        try {
            carregar();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void fromEntity(Products p) {
        if (p != null) {
            id.set(p.getId());
            name.set(p.getName());
            price.set(p.getPrice());
            quantity.set(p.getQuantity());
            sku.set(p.getSku());
        }
    }

    public Products toEntity() {
        Products p = new Products();

        p.setId(id.getId());
        p.setName(name.getName());
        p.setPrice(price.getPrice());
        p.setQuantity(quantity.getQuantity());
        p.setSku(sku.getSku());
        return p;
    }

    public StringProperty nameProperty() {
        return name;
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public IntegerProperty quantityProperty() {
        return quantity;
    }

    public StringProperty skuProperty() {
        return sku;
    }

    public void salvar() throws SQLException {
        if (name.get().isEmpty()) {
            mostrarErro("Coloque o nome");
            return;
        } else if (price.get() <= 0) {
            mostrarErro("Coloque o Valor do Produto");
            return;
        } else if (quantity.get() <= 0) {
            mostrarErro("Coloque a quantidade do estoque");
            return;
        } else if (sku.get().isEmpty()) {
            mostrarErro("É necessário preencher o sku");
            return;
        } else {
            Products p = toEntity();
            if (id.get() > 0) {
                prodao.atualizar(id.get(), p);
            } else {
                prodao.cadastrar(p);
            }
            new Alert(AlertType.INFORMATION, "Produto adicionado!").show();
        }

        limpar();
        carregar();
    }

    public void limpar() {
        id.set(0);
        name.set("");
        price.set(0);
        quantity.set(0);
        sku.set("");
    }

    public void carregar() throws SQLException {
        l.clear();
        l.addAll(prodao.pesquisar(""));
    }

    public void apagar(Products p) throws SQLException {
        prodao.apagar(p);
        carregar();
    }

    public void pesquisar() throws SQLException {
        l.clear();
        l.addAll(prodao.pesquisar(sku.get()));
    }

    private void mostrarErro(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    public ObservableList<Products> getLista() {
        return l;
    }
}*/
