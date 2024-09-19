module com.example.dropthenumber {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.antlr.antlr4.runtime;


    opens com.example.dropthenumber to javafx.fxml;
    exports com.example.dropthenumber;
    exports com.example.dropthenumber.utils;
    opens com.example.dropthenumber.utils to javafx.fxml;
    exports com.example.dropthenumber.model;
    opens com.example.dropthenumber.model to javafx.fxml;
    exports com.example.dropthenumber.view;
    opens com.example.dropthenumber.view to javafx.fxml;
    exports com.example.dropthenumber.controller;
    opens com.example.dropthenumber.controller to javafx.fxml;
}