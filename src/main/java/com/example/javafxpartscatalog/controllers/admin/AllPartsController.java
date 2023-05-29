package com.example.javafxpartscatalog.controllers.admin;

import com.example.javafxpartscatalog.dao.PartDAO;
import com.example.javafxpartscatalog.dao.interfaces.IPartDAO;
import com.example.javafxpartscatalog.models.forJavaFxTableView.FXPart;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AllPartsController implements Initializable {

    @FXML
    private TableView<FXPart> partsTable;

    @FXML
    private TableColumn<FXPart, String> condition;

    @FXML
    private TableColumn<FXPart, Integer> id;

    @FXML
    private TableColumn<FXPart, Integer> manufacturerId;

    @FXML
    private TableColumn<FXPart, String> name;

    @FXML
    private TableColumn<FXPart, String> originalPartCode;

    @FXML
    private TableColumn<FXPart, Integer> price;

    private IPartDAO partDAO = new PartDAO();

    ObservableList<FXPart> listOfFXParts = FXCollections.observableList(partDAO.getAllParts().stream().map(FXPart::new).collect(Collectors.toList()));

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        id.setCellValueFactory(new PropertyValueFactory<FXPart, Integer>("id"));
        manufacturerId.setCellValueFactory(new PropertyValueFactory<FXPart, Integer>("manufacturerId"));
        originalPartCode.setCellValueFactory(new PropertyValueFactory<FXPart, String>("originalPartCode"));
        name.setCellValueFactory(new PropertyValueFactory<FXPart, String>("name"));
        condition.setCellValueFactory(new PropertyValueFactory<FXPart, String>("condition"));
        price.setCellValueFactory(new PropertyValueFactory<FXPart, Integer>("price"));

        partsTable.setItems(listOfFXParts);
    }
}
