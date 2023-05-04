package com.wise.resource.professionals.marketplace.component;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.SneakyThrows;

@Getter
public class MinMaxField extends VBox {

    @FXML
    private Label promptText;

    @FXML
    private TextField minField;

    @FXML
    private TextField maxField;

    @SneakyThrows
    public MinMaxField() {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MinMaxField.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.load();
    }

    public void setPromptText(String text) {
        promptText.setText(text);
    }

}
