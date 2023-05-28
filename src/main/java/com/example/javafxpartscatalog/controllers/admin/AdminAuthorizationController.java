package com.example.javafxpartscatalog.controllers.admin;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class AdminAuthorizationController implements Initializable {

    @FXML
    private TextField loginArea;

    @FXML
    private TextField passwordArea;

    @FXML
    private Button signUpBtn;


    @FXML
    private BorderPane rootScene;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        signUpBtn.setOnMouseClicked(mouseEvent -> {
            try {
                rootScene.setCenter(FXMLLoader.load(getClass().getResource("/com/example/javafxpartscatalog/adminPage.fxml")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
