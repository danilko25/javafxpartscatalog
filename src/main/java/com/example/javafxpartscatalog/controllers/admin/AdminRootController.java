package com.example.javafxpartscatalog.controllers.admin;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminRootController implements Initializable {

    @FXML
    private ToggleButton allParts;

    @FXML
    private ToggleButton deletePart;

    @FXML
    private ToggleButton partAdding;

    @FXML
    private BorderPane rootScene;

    @FXML
    private ToggleButton updatePart;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            rootScene.setCenter(FXMLLoader.load(getClass().getResource("/com/example/javafxpartscatalog/admin_allParts_page.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
