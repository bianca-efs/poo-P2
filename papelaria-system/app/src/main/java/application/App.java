package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import boundary.MenuBoundary;

public class App extends Application {
    @Override
    public void start(Stage stage) {
    	MenuBoundary menu = new MenuBoundary(stage);
        Scene scn = new Scene(menu.render(), 900, 600);
        stage.setScene(scn);
        stage.setTitle("Sistema Papelaria");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}