package ru.vlsoft;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class MainClass extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        URL resource = getClass().getResource("/fxml/MainForm.fxml");
        if (resource != null) {

            Parent root = FXMLLoader.load(resource);
            Scene scene = new Scene(root, 840, 480);
            stage.setScene(scene);
            stage.setTitle("JavaFX Selection Master");
            stage.onCloseRequestProperty().setValue(e -> Platform.exit());
            stage.show();

        }

    }

    public static void main(String[] args) {
        Application.launch(MainClass.class, args);
        Platform.exit();
    }
}
