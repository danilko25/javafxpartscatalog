package com.example.javafxpartscatalog.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MarksController implements Initializable {


    @FXML
    private FlowPane flexItems = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String[] arr = {"one", "two", "three"};
        Node[] nodes = new Node[3];

        for (int i = 0; i<nodes.length; i++){
            try {
                nodes[i] = FXMLLoader.load(getClass().getResource("/com/example/javafxpartscatalog/manufacturer_item.fxml"));
                System.out.println();
                File file = new File("src/main/resources/com/example/javafxpartscatalog/Hyundai-Logo.png");
                Image image = new Image(file.toURI().toString());
                ImageView imageView = (ImageView) ((BorderPane)((VBox) nodes[i]).getChildren().get(0)).getCenter();
                imageView.setImage(image);
                Text text = (Text) ((VBox)nodes[i]).getChildren().get(1);
                text.setText(arr[i]);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            flexItems.getChildren().add(nodes[i]);
        }
    }
}