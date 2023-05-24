package com.example.javafxpartscatalog.controllers;

import com.example.javafxpartscatalog.dao.ManufacturerDAO;
import com.example.javafxpartscatalog.dao.interfaces.IManufacturerDAO;
import com.example.javafxpartscatalog.models.Manufacturer;
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
import java.util.List;
import java.util.ResourceBundle;

public class MarksController implements Initializable {


    @FXML
    private FlowPane flexItems = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        IManufacturerDAO manufacturerDAO = new ManufacturerDAO();
        List<Manufacturer> manufacturers = manufacturerDAO.getAllManufacturers();
        Node[] nodes = new Node[manufacturers.size()];
        String manufacturerName;
        Node currentNode;

        for (int i = 0; i<nodes.length; i++){
            try {
                currentNode = nodes[i];
                manufacturerName = manufacturers.get(i).getName();
                currentNode = FXMLLoader.load(getClass().getResource("/com/example/javafxpartscatalog/manufacturer_item.fxml"));
                Node finalCurrentNode = currentNode;
                currentNode.setOnMouseEntered(mouseEvent -> {
                    finalCurrentNode.setStyle("-fx-background-color: #CFCFCF");
                });

                currentNode.setOnMouseExited(mouseEvent -> {
                    finalCurrentNode.setStyle("-fx-background-color: #FFFFFF");
                });
                String imagePath = "src/main/resources/markIcons/" + manufacturerName + "-Logo.png";
                File file = new File(imagePath);
                Image image = new Image(file.toURI().toString());
                ImageView imageView = (ImageView) ((BorderPane)((VBox) currentNode).getChildren().get(0)).getCenter();
                imageView.setImage(image);
                Text text = (Text) ((VBox)currentNode).getChildren().get(1);
                text.setText(manufacturerName);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            flexItems.getChildren().add(currentNode);
        }
    }
}