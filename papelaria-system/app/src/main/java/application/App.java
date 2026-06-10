package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import boundary.FuncionarioBoundary;

public class App extends Application {
    @Override
    public void start(Stage stage) {
        FuncionarioBoundary fb = new FuncionarioBoundary();
        Scene scn = new Scene(fb.render(), 900, 600);

        stage.setScene(scn);
        stage.setTitle("Sistema Papelaria");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}