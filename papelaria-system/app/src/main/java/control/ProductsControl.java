package control;

import java.sql.SQLException;

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

public class ProductsControl {
    private ObservableList<Products> l = FXCollections.observableArrayList();

    private IntegerProperty id = new SimpleIntegerProperty(0);
    private StringProperty name = new SimpleStringProperty("");
    private DoubleProperty price = new SimpleDoubleProperty(0);
    private IntegerProperty quantity = new SimpleIntegerProperty(0);
    private StringProperty sku = new SimpleStringProperty("");
    

    private ProductsDAO prodao = new ProductsDAOImplementation();

    public ProductsControl(){
        try{
            carregar();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void fromEntity (Products p){
        if(p != null){
            id.set(p.getId());
            nome.set(p..getName());
            
        }
    }
}
