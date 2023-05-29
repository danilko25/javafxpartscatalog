package com.example.javafxpartscatalog.controllers.admin;

import com.example.javafxpartscatalog.dao.ManufacturerDAO;
import com.example.javafxpartscatalog.dao.PartDAO;
import com.example.javafxpartscatalog.enums.PartCondition;
import com.example.javafxpartscatalog.models.Manufacturer;
import com.example.javafxpartscatalog.models.Part;
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
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class PartUpdatingController implements Initializable {

    @FXML
    private ChoiceBox<PartCondition> conditionToUpdateField;

    @FXML
    private ListView<FXPart> listOfParts;

    @FXML
    private TextField manufacturerToUpdateField;

    @FXML
    private TextField nameToUpdateField;

    @FXML
    private TextField partCodeToUpdateField;

    @FXML
    private TextField priceToUpdateField;

    @FXML
    private Button saveButton;

    @FXML
    private Text selectedManufacturerId;

    private final PartDAO partDAO = new PartDAO();
    private final ManufacturerDAO manufacturerDAO = new ManufacturerDAO();

    private StringBuilder errorMessage;
    private Alert alert = new Alert(Alert.AlertType.NONE);
    private boolean errorsInFieldsValidation;
    private final String redStyle = "-fx-border-color: red";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<FXPart> parts = FXCollections.observableList(partDAO.getAllParts().stream().map(FXPart::new).collect(Collectors.toList()));
        listOfParts.setItems(parts);


            listOfParts.getSelectionModel().selectedItemProperty().addListener((observableValue, fxPart, t1) -> {
                if(listOfParts.getSelectionModel().getSelectedItem()!=null){
                    selectedManufacturerId.setText(String.valueOf(listOfParts.getSelectionModel().getSelectedItem().getId()));
                }
            });



        listOfParts.setOnMouseClicked(mouseEvent -> listOfParts.setStyle(""));
        manufacturerToUpdateField.setOnMouseClicked(mouseEvent -> manufacturerToUpdateField.setStyle(""));
        nameToUpdateField.setOnMouseClicked(mouseEvent -> nameToUpdateField.setStyle(""));
        partCodeToUpdateField.setOnMouseClicked(mouseEvent -> partCodeToUpdateField.setStyle(""));
        conditionToUpdateField.setOnMouseClicked(mouseEvent -> conditionToUpdateField.setStyle(""));
        priceToUpdateField.setOnMouseClicked(mouseEvent -> priceToUpdateField.setStyle(""));



        saveButton.setOnMouseClicked(mouseEvent -> {

            errorMessage = new StringBuilder();
            errorsInFieldsValidation = false;

            int idOfPartToUpdate = -1;
            int manufacturerId = -1;
            String originalPartCode;
            String partName;
            PartCondition partCondition;
            int price = 0;

            try {
                idOfPartToUpdate = Integer.parseInt(selectedManufacturerId.getText());
            } catch (NumberFormatException e) {
                errorMessage.append("Виберіть запчастину зі списку\n");
                listOfParts.setStyle(redStyle);
                errorsInFieldsValidation = true;
            }

            if (manufacturerToUpdateField.getText().equals("") && !selectedManufacturerId.getText().equals("")) {
                //Don't set new value if field is empty
                manufacturerId = listOfParts.getSelectionModel().getSelectedItem().getManufacturerId();
            } else {
                Optional<Manufacturer> manufacturerOptional = manufacturerDAO.getManufacturerByName(manufacturerToUpdateField.getText());
                if (manufacturerOptional.isPresent()) {
                    manufacturerId = manufacturerOptional.get().getId();
                } else {
                    manufacturerToUpdateField.setStyle(redStyle);
                    errorMessage.append("Виробника з такою назвою не знайдено\n");
                    errorsInFieldsValidation = true;
                }
            }

            if (partCodeToUpdateField.getText().equals("") && !selectedManufacturerId.getText().equals("")) {
                //Don't set new value if field is empty
                originalPartCode = listOfParts.getSelectionModel().getSelectedItem().getOriginalPartCode();
            } else {
                originalPartCode = validateTextField(partCodeToUpdateField, "Код запчастини");
            }

            if (nameToUpdateField.getText().equals("") && !selectedManufacturerId.getText().equals("")) {
                //Don't set new value if field is empty
                partName = listOfParts.getSelectionModel().getSelectedItem().getName();
            } else {
                partName = validateTextField(nameToUpdateField, "Назва");
            }

            partCondition = conditionToUpdateField.getValue();
            if (partCondition == null && !selectedManufacturerId.getText().equals("")){
                partCondition = PartCondition.valueOf(listOfParts.getSelectionModel().getSelectedItem().getCondition());
            }else {
                errorMessage.append("Виберіть стан запчастини");
                conditionToUpdateField.setStyle(redStyle);
                errorsInFieldsValidation = true;
            }

            if (priceToUpdateField.getText().equals("") && !selectedManufacturerId.getText().equals("")){
                price = listOfParts.getSelectionModel().getSelectedItem().getPrice();
            }else {
                try {
                    price = Integer.parseInt(priceToUpdateField.getText());
                }catch (NumberFormatException e){
                    errorMessage.append("Невірний формат вводу в полі \"Ціна\"\n");
                    priceToUpdateField.setStyle(redStyle);
                    errorsInFieldsValidation = true;
                }
            }


            if(!errorsInFieldsValidation){
                Part partForAdding = new Part(manufacturerId, originalPartCode, partName, partCondition, price);
                partDAO.updateById(idOfPartToUpdate, partForAdding);
                manufacturerToUpdateField.setText("");
                partCodeToUpdateField.setText("");
                nameToUpdateField.setText("");
                conditionToUpdateField.setValue(null);
                priceToUpdateField.setText("");
                allFieldToDefaultStyle();
                ObservableList<FXPart> updatedParts = FXCollections.observableList(partDAO.getAllParts().stream().map(FXPart::new).collect(Collectors.toList()));
                listOfParts.setItems(updatedParts);
                selectedManufacturerId.setText("");
                alert = new Alert(Alert.AlertType.NONE, "Запчастину успішно змінено", ButtonType.OK);
                alert.show();
            }else {
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setTitle("Помилка");
                if (selectedManufacturerId.getText().equals("") && manufacturerToUpdateField.getText().equals("") && partCodeToUpdateField.getText().equals("") && nameToUpdateField.getText().equals("") && conditionToUpdateField.getValue()==null && priceToUpdateField.getText().equals("")){
                    allFieldToDefaultStyle();
                    listOfParts.setStyle(redStyle);
                    alert.setHeaderText("Помилка");
                    alert.setContentText("Виберіть запчастину");
                    alert.show();
                }else {
                    alert.setHeaderText("Неправильно введені значення");
                    alert.setContentText(errorMessage.toString());
                    alert.show();
                }

            }
        });
    }

    String validateTextField(TextField field, String fieldNameForErrorMessage){
        if (field.getText().trim().length()!=0){
            return field.getText().trim();
        }
        errorMessage.append("Заповніть поле \"").append(fieldNameForErrorMessage).append("\"\n");
        field.setStyle(redStyle);
        errorsInFieldsValidation = true;
        return "";
    }

    void allFieldToDefaultStyle(){
        listOfParts.setStyle("");
        manufacturerToUpdateField.setStyle("");
        nameToUpdateField.setStyle("");
        partCodeToUpdateField.setStyle("");
        conditionToUpdateField.setStyle("");
        priceToUpdateField.setStyle("");
    }
}

