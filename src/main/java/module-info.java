module com.example.javafxpartscatalog {
    requires javafx.controls;
    requires javafx.fxml;




    opens com.example.javafxpartscatalog to javafx.fxml;
    opens com.example.javafxpartscatalog.controllers to javafx.fxml;

    exports com.example.javafxpartscatalog;
    exports com.example.javafxpartscatalog.controllers;


}

//module com.example.demo {
//        requires javafx.controls;
//        requires javafx.fxml;
//        requires java.sql;
//
//
//        opens com.example.demo to javafx.fxml;
//        opens com.example.demo.controllers to javafx.fxml;
//
//        exports com.example.demo;
//        exports com.example.demo.controllers;
//        exports com.example.demo.models.forFXLists;
//        exports com.example.demo.models;
//        exports com.example.demo.utils;
//        }