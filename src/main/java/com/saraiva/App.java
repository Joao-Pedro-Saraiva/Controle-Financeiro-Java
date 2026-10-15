package com.saraiva;

import com.saraiva.controller.Main;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application{
    @Override
    public void start(Stage stage) {
        Main controller = new Main();
        controller.mostrarTela(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
