package com.example.javafxpartscatalog.controllers.admin;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class AdminRootController implements Initializable {

    @FXML
    private ToggleGroup adminFunctions;

    @FXML
    private ToggleButton allManufacturers;

    @FXML
    private ToggleButton allParts;

    @FXML
    private ToggleButton deleteManufacturer;

    @FXML
    private ToggleButton deletePart;

    @FXML
    private ToggleButton manufacturerAdding;

    @FXML
    private ToggleButton partAdding;

    @FXML
    private BorderPane rootScene;

    @FXML
    private ToggleButton updateManufacturer;

    @FXML
    private ToggleButton updatePart;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        rootScene.setCenter(getNodeFromFXML("/com/example/javafxpartscatalog/admin_allParts_page.fxml"));

        allParts.setOnMouseClicked(mouseEvent -> {
            rootScene.setCenter(getNodeFromFXML("/com/example/javafxpartscatalog/admin_allParts_page.fxml"));
        });

        partAdding.setOnMouseClicked(mouseEvent -> {
                rootScene.setCenter(getNodeFromFXML("/com/example/javafxpartscatalog/admin_partAdding.fxml"));
        });

        updatePart.setOnMouseClicked(mouseEvent -> {
            rootScene.setCenter(getNodeFromFXML("/com/example/javafxpartscatalog/admin_partUpdating.fxml"));
        });

        deletePart.setOnMouseClicked(mouseEvent -> {
            rootScene.setCenter(getNodeFromFXML("/com/example/javafxpartscatalog/admin_partDeleting.fxml"));
        });

        allManufacturers.setOnMouseClicked(mouseEvent -> {
            rootScene.setCenter(getNodeFromFXML("/com/example/javafxpartscatalog/admin_allManufacturers_page.fxml"));
        });

        manufacturerAdding.setOnMouseClicked(mouseEvent -> {
            rootScene.setCenter(getNodeFromFXML("/com/example/javafxpartscatalog/admin_manufacturerAdding.fxml"));
        });

        updateManufacturer.setOnMouseClicked(mouseEvent -> {
            rootScene.setCenter(getNodeFromFXML("/com/example/javafxpartscatalog/admin_manufacturerUpdating.fxml"));
        });

    }

    Node getNodeFromFXML(String path){
        try {
            return (FXMLLoader.load(Objects.requireNonNull(getClass().getResource(path))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
