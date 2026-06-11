package boundary;

import application.Tela;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class MenuBoundary implements Tela{
    private Stage stage;
    public MenuBoundary(Stage stage) {
        this.stage = stage;
    }

    public GridPane render() {
        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(20);
        grid.setPadding(new javafx.geometry.Insets(20));

        Button btnFuncionario = new Button("Funcionários");
        Button btnProduto = new Button("Produtos");
        Button btnCliente = new Button("Clientes");
        Button btnFornecedor = new Button("Fornecedores");

        btnFuncionario.setOnAction(e -> {
        	
            FuncionarioBoundary fb = new FuncionarioBoundary(stage);
            stage.setScene(new Scene(fb.render(), 900, 600));
        });
        
        btnCliente.setOnAction(e -> {
            ClienteBoundary cb = new ClienteBoundary(stage);
            stage.setScene(new Scene(cb.render(), 900, 600));
        });
        
        btnFornecedor.setOnAction(e -> {
            FornecedorBoundary frb = new FornecedorBoundary(stage);
            stage.setScene(new Scene(frb.render(), 900, 600));
        });

        grid.add(btnFuncionario, 0, 0);
        grid.add(btnProduto, 1, 0);
        grid.add(btnCliente, 0, 1);
        grid.add(btnFornecedor, 1, 1);
        
        btnFuncionario.setPrefSize(220, 120);
        btnProduto.setPrefSize(220, 120);
        btnCliente.setPrefSize(220, 120);
        btnFornecedor.setPrefSize(220, 120);
        
        btnFuncionario.setStyle("-fx-font-size: 16px;");
        btnProduto.setStyle("-fx-font-size: 16px;");
        btnFornecedor.setStyle("-fx-font-size: 16px;");
        btnCliente.setStyle("-fx-font-size: 16px;");
        
        grid.setAlignment(javafx.geometry.Pos.CENTER);

        return grid;
    }
}