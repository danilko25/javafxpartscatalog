package com.example.javafxpartscatalog.controllers.admin;

import com.example.javafxpartscatalog.dao.ManufacturerDAO;
import com.example.javafxpartscatalog.dao.interfaces.IManufacturerDAO;
import com.example.javafxpartscatalog.models.Manufacturer;
import com.example.javafxpartscatalog.models.forJavaFxTableView.FXPart;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class AllManufacturersController implements Initializable {

    @FXML
    private TableColumn<Manufacturer, Integer> id;

    @FXML
    private TableView<Manufacturer> manufacturersTable;

    @FXML
    private TableColumn<Manufacturer, String> name;

    private final IManufacturerDAO manufacturerDAO = new ManufacturerDAO();

    private final ObservableList<Manufacturer> allManufacturers = FXCollections.observableList(manufacturerDAO.getAllManufacturers());

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        id.setCellValueFactory(new PropertyValueFactory<Manufacturer, Integer>("id"));
        name.setCellValueFactory(new PropertyValueFactory<Manufacturer, String>("name"));

        manufacturersTable.setItems(allManufacturers);
    }
}
