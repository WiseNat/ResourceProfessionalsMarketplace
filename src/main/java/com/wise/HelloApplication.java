package com.wise;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.SneakyThrows;

import java.util.Objects;

public class HelloApplication extends Application {
    @Override
    @SneakyThrows
    public void start(Stage stage) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("fxml/create-an-account.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("styles/application.css")).toExternalForm());
        stage.setTitle("Resource Professionals Marketplace");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}