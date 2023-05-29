package com.example.javafxpartscatalog.controllers.admin;

import com.example.javafxpartscatalog.dao.ManufacturerDAO;
import com.example.javafxpartscatalog.models.Manufacturer;
import com.example.javafxpartscatalog.models.forJavaFxTableView.FXPart;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ManufacturerUpdatingController implements Initializable {

    @FXML
    private Button addingButton;

    @FXML
    private Text idOfSelectedManufacturer;

    @FXML
    private ListView<Manufacturer> listOfManufacturers;

    @FXML
    private TextField name;

    private final ManufacturerDAO manufacturerDAO = new ManufacturerDAO();

    private final String redStyle = "-fx-border-color: red";

    private int manufacturerId = -1;
    private String manufacturerName;

    private StringBuilder stringBuilder = new StringBuilder();
    private boolean errorsInFieldsValidation;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Manufacturer> manufacturers = FXCollections.observableList(manufacturerDAO.getAllManufacturers());
        listOfManufacturers.setItems(manufacturers);



        listOfManufacturers.getSelectionModel().selectedItemProperty().addListener((observableValue, manufacturer, t1) -> {
            if (listOfManufacturers.getSelectionModel().getSelectedItem()!=null){
                idOfSelectedManufacturer.setText(String.valueOf(listOfManufacturers.getSelectionModel().getSelectedItem().getId()));
            }
        });

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Помилка");
        alert.setHeaderText("Помилка вводу");

        name.setOnMouseClicked(mouseEvent -> name.setStyle(""));
        listOfManufacturers.setOnMouseClicked(mouseEvent -> listOfManufacturers.setStyle(""));

        addingButton.setOnMouseClicked(mouseEvent -> {
            stringBuilder = new StringBuilder();
            errorsInFieldsValidation = false;

            try {
                manufacturerId = Integer.parseInt(idOfSelectedManufacturer.getText());
            } catch (NumberFormatException e) {
                listOfManufacturers.setStyle(redStyle);
                errorsInFieldsValidation = true;
                stringBuilder.append("Виберіть виробника зі списку\n");
            }

            if (!idOfSelectedManufacturer.getText().equals("") && name.getText().equals("")){
                manufacturerName = listOfManufacturers.getSelectionModel().getSelectedItem().getName();
            }else if (name.getText().trim().length()!=0){
                manufacturerName =  name.getText().trim();
            }else {
                stringBuilder.append("Заповніть поле \"Назва\"");
                name.setStyle(redStyle);
                errorsInFieldsValidation = true;
            }

            if(!errorsInFieldsValidation){
                try {
                    manufacturerDAO.updateManufacturerById(manufacturerId, new Manufacturer(manufacturerName));
                    name.setText("");
                    ObservableList<Manufacturer> manufacturersAfterUpdating = FXCollections.observableList(manufacturerDAO.getAllManufacturers());
                    listOfManufacturers.setItems(manufacturersAfterUpdating);
                    idOfSelectedManufacturer.setText("");
                    Alert success = new Alert(Alert.AlertType.NONE, "Запчастину успішно змінено", ButtonType.OK);
                    success.show();
                }catch (RuntimeException e){
                    name.setStyle("-fx-border-color: red");
                    alert.setContentText("Виробник з такою назвою вже існує");
                    alert.show();
                }
            }else {
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setTitle("Помилка");
                if (idOfSelectedManufacturer.getText().equals("") && name.getText().equals("")){
                    name.setStyle("");
                    listOfManufacturers.setStyle(redStyle);
                    alert.setHeaderText("Помилка");
                    alert.setContentText("Виберіть виробника зі списку");
                    alert.show();
                }else {
                    alert.setHeaderText("Неправильно введені значення");
                    alert.setContentText(stringBuilder.toString());
                    alert.show();
                }
            }
        });
    }
}
