package com.example.javafxpartscatalog.controllers;

import com.example.javafxpartscatalog.dao.PartDAO;
import com.example.javafxpartscatalog.dao.interfaces.IPartDAO;
import com.example.javafxpartscatalog.models.Part;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class RootController implements Initializable {

    @FXML
    private Button adminButton;

    @FXML
    private BorderPane rootScene;

    @FXML
    private Button searchByCodeButton;

    @FXML
    private TextField fieldForPartCode;

    @FXML
    private ImageView searchIcon;

    @FXML
    private Button homePage;

    private final IPartDAO partDAO = new PartDAO();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        searchByCodeButton.setOnMouseClicked(mouseEvent -> {
            FlowPane paneForPartsFoundedByCode = new FlowPane();
            List<Part> parts = partDAO.getPartByOriginalPartCode(fieldForPartCode.getText());
            if (parts.isEmpty()){
                try {
                    rootScene.setCenter(FXMLLoader.load(getClass().getResource("/com/example/javafxpartscatalog/emptyList.fxml")));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }else {
                Node[] nodes = new Node[parts.size()];
                Node currentNode;
                for (int i = 0; i<parts.size(); i++){
                    Part currentPart = parts.get(i);
                    PartItemController.partItem = currentPart;
                    try {
                        currentNode = FXMLLoader.load(getClass().getResource("/com/example/javafxpartscatalog/part_listitem.fxml"));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    Node finalCurrentNode = currentNode;
                    currentNode.setOnMouseEntered(mouseEvent2 -> {
                        finalCurrentNode.setStyle("-fx-border-color: black; -fx-background-color: #CFCFCF");

                    });
                    currentNode.setOnMouseExited(mouseEvent2 -> {
                        finalCurrentNode.setStyle("-fx-border-color: grey; -fx-background-color: #FFFFFF");
                    });
                    currentNode.setOnMouseClicked(mouseEvent2 -> {
                        paneForPartsFoundedByCode.getChildren().clear();
                        try {
                            PartFullPageController.setPartForPage(currentPart);
                            Node node = FXMLLoader.load(getClass().getResource("/com/example/javafxpartscatalog/partFullPage.fxml"));
                            paneForPartsFoundedByCode.getChildren().add(node);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                    paneForPartsFoundedByCode.getChildren().add(currentNode);
                }
                rootScene.setCenter(paneForPartsFoundedByCode);
                fieldForPartCode.setText("");
            }
        });

        try {
            ScrollPane pane = FXMLLoader.load(getClass().getResource("/com/example/javafxpartscatalog/marks.fxml"));
            rootScene.setCenter(pane);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Icon for search button
        File fileWithSearchIcon = new File("src/main/resources/icons/search-icon.png");
        ImageView imageView = new ImageView(fileWithSearchIcon.toURI().toString());
        searchByCodeButton.setGraphic(imageView);

        //Icon for admin panel
        adminButton.setStyle("-fx-background-color: transparent; -fx-background-radius: 10; -fx-border-radius: 10");
        adminButton.setOnMouseEntered(mouseEvent -> {adminButton.setStyle("-fx-background-color: white; -fx-background-radius: 100; -fx-border-radius: 100");});
        adminButton.setOnMouseExited(mouseEvent -> adminButton.setStyle("-fx-background-color: transparent; -fx-background-radius: 10; -fx-border-radius: 10"));
        File fileWithAdminIcon = new File("src/main/resources/icons/account-icon.png");
        ImageView imageViewForAdminBtn = new ImageView(fileWithAdminIcon.toURI().toString());
        adminButton.setGraphic(imageViewForAdminBtn);

        //Icon for home page
        homePage.setStyle("-fx-background-color: transparent; -fx-background-radius: 10; -fx-border-radius: 10");
        homePage.setOnMouseEntered(mouseEvent -> {homePage.setStyle("-fx-background-color: white; -fx-background-radius: 100; -fx-border-radius: 100");});
        homePage.setOnMouseExited(mouseEvent -> homePage.setStyle("-fx-background-color: transparent; -fx-background-radius: 10; -fx-border-radius: 10"));
        File fileWithHomeIcon = new File("src/main/resources/icons/home-icon.png");
        ImageView imageViewForHomeBtn = new ImageView(fileWithHomeIcon.toURI().toString());
        homePage.setGraphic(imageViewForHomeBtn);



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
