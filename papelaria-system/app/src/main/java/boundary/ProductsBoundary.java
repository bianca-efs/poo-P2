package boundary;

import java.util.Optional;
import application.Tela;
import control.ProductsControl;
import entity.Products;
import javafx.stage.Stage;

import javafx.scene.control.TableView;




public class ProductsBoundary implements Tela{
    private Stage stage;
    private ProductsControl control = new ProductsControl();
    private TableView<Products> table = new TableView<>();

    private textField txtName = new textFiel(); 
	
    
	public ProductsBoundary(Stage stage) {
		this.stage = stage;
	}
}
