package boundary;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PrincipalBoundary extends Application {

    @Override
    public void start(Stage stage) {
        FuncionarioBoundary fb = new FuncionarioBoundary(stage);
        Scene scene = new Scene(fb.render(), 900, 600);
        stage.setScene(scene);
        stage.setTitle("Sistema Papelaria");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}