package com.example.javafxpartscatalog.controllers.admin;

import com.example.javafxpartscatalog.dao.ManufacturerDAO;
import com.example.javafxpartscatalog.dao.PartDAO;
import com.example.javafxpartscatalog.enums.PartCondition;
import com.example.javafxpartscatalog.models.Manufacturer;
import com.example.javafxpartscatalog.models.Part;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;

public class PartAddingController implements Initializable {

    @FXML
    private Button addingButton;

    @FXML
    private TextField manufacturerNameField;

    @FXML
    private ChoiceBox<PartCondition> partConditionField;

    @FXML
    private TextField partNameField;

    @FXML
    private TextField partPriceField;

    @FXML
    private TextField originalPartCodeField;

    private StringBuilder errorMessage;
    private Alert alert = new Alert(Alert.AlertType.NONE);
    private boolean errorsInFieldsValidation;
    private final String redStyle = "-fx-border-color: red";

    private final PartDAO partDAO = new PartDAO();
    private final ManufacturerDAO manufacturerDAO = new ManufacturerDAO();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<PartCondition> conditions = FXCollections.observableList(Arrays.asList(PartCondition.NEW, PartCondition.USED, PartCondition.DAMAGED));
        partConditionField.setItems(conditions);

        manufacturerNameField.setOnMouseClicked(mouseEvent -> manufacturerNameField.setStyle(""));
        partNameField.setOnMouseClicked(mouseEvent -> partNameField.setStyle(""));
        originalPartCodeField.setOnMouseClicked(mouseEvent -> originalPartCodeField.setStyle(""));
        partConditionField.setOnMouseClicked(mouseEvent -> partConditionField.setStyle(""));
        partPriceField.setOnMouseClicked(mouseEvent -> partPriceField.setStyle(""));



        addingButton.setOnMouseClicked(mouseEvent -> {
            errorMessage = new StringBuilder();
            errorsInFieldsValidation=false;

            int manufacturerId = -1;
            String originalPartCode;
            String partName;
            PartCondition partCondition;
            int price = 0;


            Optional<Manufacturer> manufacturerOptional = manufacturerDAO.getManufacturerByName(manufacturerNameField.getText());
            if (manufacturerOptional.isPresent()){
                manufacturerId = manufacturerOptional.get().getId();
            }else {
                manufacturerNameField.setStyle(redStyle);
                errorMessage.append("Виробника з такою назвою не знайдено\n");
                errorsInFieldsValidation = true;
            }


            originalPartCode = validateTextField(originalPartCodeField, "Код запчастини");


            partName = validateTextField(partNameField, "Назва");


            partCondition = partConditionField.getValue();
            if (partCondition == null){
                errorMessage.append("Виберіть стан запчастини\n");
                partConditionField.setStyle(redStyle);
                errorsInFieldsValidation = true;
            }

            try {
                price = Integer.parseInt(partPriceField.getText());
            }catch (NumberFormatException e){
                errorMessage.append("Невірний формат вводу в полі \"Ціна\"\n");
                partPriceField.setStyle(redStyle);
                errorsInFieldsValidation = true;
            }

            if(!errorsInFieldsValidation){
                Part partForAdding = new Part(manufacturerId, originalPartCode, partName, partCondition, price);
                partDAO.addPart(partForAdding);
                manufacturerNameField.setText("");
                originalPartCodeField.setText("");
                partNameField.setText("");
                partConditionField.setValue(null);
                partPriceField.setText("");
                allFieldToDefaultStyle();
                alert = new Alert(Alert.AlertType.NONE, "Запчастину успішно додано", ButtonType.OK);
                alert.show();
            }else {
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setTitle("Помилка");
                alert.setHeaderText("Неправильно введені значення");
                alert.setContentText(errorMessage.toString());
                alert.show();
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
        manufacturerNameField.setStyle("");
        partNameField.setStyle("");
        originalPartCodeField.setStyle("");
        partConditionField.setStyle("");
        partPriceField.setStyle("");
    }

}
