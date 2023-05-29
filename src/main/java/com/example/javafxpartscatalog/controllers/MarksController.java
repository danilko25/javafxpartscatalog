package com.example.javafxpartscatalog.controllers;

import com.example.javafxpartscatalog.dao.ManufacturerDAO;
import com.example.javafxpartscatalog.dao.PartDAO;
import com.example.javafxpartscatalog.dao.interfaces.IManufacturerDAO;
import com.example.javafxpartscatalog.dao.interfaces.IPartDAO;
import com.example.javafxpartscatalog.models.Manufacturer;
import com.example.javafxpartscatalog.models.Part;
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

    private final IManufacturerDAO manufacturerDAO = new ManufacturerDAO();

    private final IPartDAO partDAO = new PartDAO();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        List<Manufacturer> manufacturers = manufacturerDAO.getAllManufacturers();
        Node[] nodes = new Node[manufacturers.size()];
        String manufacturerName;
        Node currentNode;

        for (int i = 0; i<nodes.length; i++){
            try {
                manufacturerName = manufacturers.get(i).getName();
                currentNode = FXMLLoader.load(getClass().getResource("/com/example/javafxpartscatalog/manufacturer_item.fxml"));
                Node finalCurrentNode = currentNode;
                String imagePath = "src/main/resources/markIcons/" + manufacturerName + "-Logo.png";
                File file = new File(imagePath);
                if (!file.exists()){
                    file = new File("src/main/resources/icons/no-image.png");
                }
                Image image = new Image(file.toURI().toString());
                ImageView imageView = (ImageView) ((BorderPane)((VBox) currentNode).getChildren().get(0)).getCenter();
                imageView.setImage(image);
                Text text = (Text) ((VBox)currentNode).getChildren().get(1);
                text.setText(manufacturerName);

                currentNode.setOnMouseEntered(mouseEvent -> {
                    finalCurrentNode.setStyle("-fx-background-color: #CFCFCF");
                });

                currentNode.setOnMouseExited(mouseEvent -> {
                    finalCurrentNode.setStyle("-fx-background-color: #FFFFFF");
                });
                currentNode.setOnMouseClicked(mouseEvent -> {
                    try {
                        getPartsListByMark(text.getText());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            flexItems.getChildren().add(currentNode);
        }
    }

    void getPartsListByMark(String mark) throws IOException {

        flexItems.getChildren().clear();
        List<Part> partsByMark = partDAO.getPartsByManufacturerName(mark);

        if (partsByMark.isEmpty()){
            try {
                flexItems.getChildren().add(FXMLLoader.load(getClass().getResource("/com/example/javafxpartscatalog/emptyList.fxml")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else {
            Node[] partItems = new Node[partsByMark.size()];
            Node currentNode;
            for (int i = 0; i<partItems.length; i++){
                Part currentPart = partsByMark.get(i);
                PartItemController.partItem = currentPart;
                currentNode = FXMLLoader.load(getClass().getResource("/com/example/javafxpartscatalog/part_listitem.fxml"));
                Node finalCurrentNode = currentNode;
                currentNode.setOnMouseEntered(mouseEvent -> {
                    finalCurrentNode.setStyle("-fx-border-color: black; -fx-background-color: #CFCFCF");

                });
                currentNode.setOnMouseExited(mouseEvent -> {
                    finalCurrentNode.setStyle("-fx-border-color: grey; -fx-background-color: #FFFFFF");
                });
                currentNode.setOnMouseClicked(mouseEvent -> {
                    flexItems.getChildren().clear();
                    try {
                        PartFullPageController.setPartForPage(currentPart);
                        Node node = FXMLLoader.load(getClass().getResource("/com/example/javafxpartscatalog/partFullPage.fxml"));
                        flexItems.getChildren().add(node);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
                flexItems.getChildren().add(currentNode);
            }
        }

    }
}