package com.example.javafxpartscatalog.controllers;

import com.example.javafxpartscatalog.dao.ManufacturerDAO;
import com.example.javafxpartscatalog.dao.interfaces.IManufacturerDAO;
import com.example.javafxpartscatalog.models.Manufacturer;
import com.example.javafxpartscatalog.models.Part;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class PartItemController implements Initializable {

    @FXML
    private Text carMarkOfPart;

    @FXML
    private Text conditionOfPart;

    @FXML
    private Text nameOfPart;

    @FXML
    private Text originalCodeOfPart;

    @FXML
    private Text priceOfPart;

    public static Part partItem;

    private final IManufacturerDAO manufacturerDAO = new ManufacturerDAO();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Part thisPart = new Part(partItem.getId(), partItem.getManufacturerId(), partItem.getOriginalPartCode(), partItem.getName(), partItem.getCondition(), partItem.getPrice());
        Optional<Manufacturer> manufacturer = manufacturerDAO.getManufacturerById(thisPart.getManufacturerId());
        String name = "null";
        if(manufacturer.isPresent()){
           name = manufacturer.get().getName();
        }
        carMarkOfPart.setText(name);
        originalCodeOfPart.setText(thisPart.getOriginalPartCode());
        nameOfPart.setText(thisPart.getName());
        conditionOfPart.setText(thisPart.getCondition().toString());
        priceOfPart.setText(String.valueOf(thisPart.getPrice()));
    }
}
