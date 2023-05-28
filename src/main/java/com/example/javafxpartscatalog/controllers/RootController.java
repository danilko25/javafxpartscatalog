package com.example.javafxpartscatalog.controllers;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RootController implements Initializable {

    @FXML
    private Button adminButton;

    @FXML
    private BorderPane rootScene;

    @FXML
    private Button searchByCodeButton;

    @FXML
    private ImageView searchIcon;

    @FXML
    private Button homePage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            ScrollPane pane = FXMLLoader.load(getClass().getResource("/com/example/javafxpartscatalog/marks.fxml"));
            rootScene.setCenter(pane);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        File fileWithSearchIcon = new File("src/main/resources/icons/search-icon.png");
        ImageView imageView = new ImageView(fileWithSearchIcon.toURI().toString());
        searchByCodeButton.setGraphic(imageView);

        homePage.setOnMouseClicked(mouseEvent -> {
            try {
                ScrollPane pane = FXMLLoader.load(getClass().getResource("/com/example/javafxpartscatalog/marks.fxml"));
                rootScene.setCenter(pane);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        adminButton.setOnMouseClicked(mouseEvent -> {
            try {
                rootScene.setCenter(FXMLLoader.load(getClass().getResource("/com/example/javafxpartscatalog/admin_authorization_page.fxml")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
