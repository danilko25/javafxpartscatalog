module com.example.javafxpartscatalog {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;




    opens com.example.javafxpartscatalog to javafx.fxml;
    opens com.example.javafxpartscatalog.controllers to javafx.fxml;
    opens com.example.javafxpartscatalog.controllers.admin to javafx.fxml;

    exports com.example.javafxpartscatalog.models.forJavaFxTableView;
    exports com.example.javafxpartscatalog;
    exports com.example.javafxpartscatalog.controllers;
    exports com.example.javafxpartscatalog.controllers.admin;


}