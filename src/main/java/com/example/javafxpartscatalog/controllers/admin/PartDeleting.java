package com.example.javafxpartscatalog.controllers.admin;

import com.example.javafxpartscatalog.dao.PartDAO;
import com.example.javafxpartscatalog.dao.interfaces.IPartDAO;
import com.example.javafxpartscatalog.models.forJavaFxTableView.FXPart;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;

import java.net.URL;
import java.nio.file.OpenOption;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class PartDeleting implements Initializable {

    @FXML
    private Button deleteBtn;

    @FXML
    private ListView<FXPart> listOfParts;

    private final IPartDAO partDAO = new PartDAO();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<FXPart> parts = FXCollections.observableList(partDAO.getAllParts().stream().map(FXPart::new).collect(Collectors.toList()));
        listOfParts.setItems(parts);

        listOfParts.setOnMouseClicked(mouseEvent -> listOfParts.setStyle(""));

        deleteBtn.setOnMouseClicked(mouseEvent -> {
            if (listOfParts.getSelectionModel().getSelectedItem()!=null){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Підтвердіть дію");
                alert.setHeaderText("Видалити дану запчастину?\n");
                alert.setContentText(listOfParts.getSelectionModel().getSelectedItem().toString());
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get()==ButtonType.OK){
                    partDAO.deleteById(listOfParts.getSelectionModel().getSelectedItem().getId());
                }
                ObservableList<FXPart> partsAfterDeleting = FXCollections.observableList(partDAO.getAllParts().stream().map(FXPart::new).collect(Collectors.toList()));
                listOfParts.setItems(partsAfterDeleting);
            }else {
                listOfParts.setStyle("-fx-border-color: red");
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Помилка");
                errorAlert.setHeaderText("");
                errorAlert.setContentText("Виберіть запчастину яку хочете видалити");
                errorAlert.show();
            }
        });
    }
}
