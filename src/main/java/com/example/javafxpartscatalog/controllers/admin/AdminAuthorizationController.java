package com.example.javafxpartscatalog.controllers.admin;

import com.example.javafxpartscatalog.utils.PropertiesLoader;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class AdminAuthorizationController implements Initializable {

    @FXML
    private TextField loginArea;

    @FXML
    private PasswordField passwordArea;

    @FXML
    private Button signUpBtn;


    @FXML
    private BorderPane rootScene;

    private Properties conf;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        signUpBtn.setOnMouseClicked(mouseEvent -> {
            try {
                conf = PropertiesLoader.loadProperties();
                String adminLogin = conf.getProperty("admin.login");
                String adminPassword = conf.getProperty("admin.password");
                if (loginArea.getText().equals(adminLogin)&&passwordArea.getText().equals(adminPassword)){
                    rootScene.setCenter(FXMLLoader.load(getClass().getResource("/com/example/javafxpartscatalog/adminPage.fxml")));
                }else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Помилка");
                    alert.setHeaderText("Невірний логін або пароль");
                    alert.setContentText("");
                    alert.show();
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
