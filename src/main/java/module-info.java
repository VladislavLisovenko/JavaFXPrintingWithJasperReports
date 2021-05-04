module JavaFXSomeTricks {
    exports ru.vlsoft;
    exports ru.vlsoft.models;
    opens ru.vlsoft.controllers;

    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires jasperreports;
    requires java.sql;
}