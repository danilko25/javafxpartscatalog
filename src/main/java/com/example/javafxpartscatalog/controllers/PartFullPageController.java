package com.example.javafxpartscatalog.controllers;
import com.example.javafxpartscatalog.dao.ManufacturerDAO;
import com.example.javafxpartscatalog.dao.interfaces.IManufacturerDAO;
import com.example.javafxpartscatalog.models.Manufacturer;
import com.example.javafxpartscatalog.models.Part;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class PartFullPageController implements Initializable {

    @FXML
    private Text conditionText;

    @FXML
    private ImageView image;

    @FXML
    private Text manufactrurerText;

    @FXML
    private Text nameText;

    @FXML
    private Text partCodeText;

    @FXML
    private Text priceText;

    private static Part partForPage;
    private IManufacturerDAO manufacturerDAO = new ManufacturerDAO();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Optional<Manufacturer> manufacturer = manufacturerDAO.getManufacturerById(partForPage.getManufacturerId());
        String manufacturerName = "null";
        if(manufacturer.isPresent()){
            manufacturerName = manufacturer.get().getName();
        }
        String pathForImage = "src/main/resources/partsPhoto/" + manufacturerName + "/" + partForPage.getId() +".jpg";
        File file = new File(pathForImage);
        if(!file.exists()){
            file = new File("src/main/resources/icons/no-image.png");
        }
        image.setImage(new Image(file.toURI().toString()));
        manufactrurerText.setText(manufacturerName);
        partCodeText.setText(partForPage.getOriginalPartCode());
        nameText.setText(partForPage.getName());
        conditionText.setText(partForPage.getCondition().toString());
        priceText.setText(String.valueOf(partForPage.getPrice()));
    }

    public static void setPartForPage(Part partForPage) {
        PartFullPageController.partForPage = partForPage;
    }
}
