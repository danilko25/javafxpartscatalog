package com.example.javafxpartscatalog;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;



public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("root.fxml"));
        stage.setTitle("Parts Catalog");
        stage.setScene(new Scene(root));
        stage.show();
//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("root.fxml"));
//        Scene scene = new Scene(fxmlLoader.load());
//        scene.getStylesheets().add(0, "style.css");
//        stage.setTitle("Parts Catalog");
//        stage.setScene(scene);
//        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}