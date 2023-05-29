package com.example.javafxpartscatalog.controllers.admin;

import com.example.javafxpartscatalog.dao.ManufacturerDAO;
import com.example.javafxpartscatalog.models.Manufacturer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ResourceBundle;

public class ManufacturerAddingController implements Initializable {
    @FXML
    private Button addingButton;

    @FXML
    private TextField name;

    private final ManufacturerDAO manufacturerDAO = new ManufacturerDAO();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Помилка");
        alert.setHeaderText("Помилка вводу");

        name.setOnMouseClicked(mouseEvent -> name.setStyle(""));
        addingButton.setOnMouseClicked(mouseEvent -> {
            if (name.getText().trim().length()!=0){
                try {
                    manufacturerDAO.addManufacturer(new Manufacturer(name.getText()));
                    name.setText("");
                    Alert alertInfo = new Alert(Alert.AlertType.NONE, "Виробника успішно додано", ButtonType.OK);
                    alertInfo.show();
                }catch (RuntimeException e){
                    name.setStyle("-fx-border-color: red");
                    alert.setContentText("Виробник з такою назвою вже існує");
                    alert.show();
                }
            }else {
                name.setStyle("-fx-border-color: red");
                alert.setContentText("Поле \"Назва\" повинно бути заповнене");
                alert.show();
            }
        });
    }
}
