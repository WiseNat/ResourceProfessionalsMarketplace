package com.wise.resource.professionals.marketplace.component;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import lombok.SneakyThrows;

public class Modal<T> extends Dialog<T> {

    @FXML
    private GridPane leftContent;

    @FXML
    private GridPane rightContent;

    @FXML
    private Label title;

    @SneakyThrows
    public Modal() {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Modal.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.load();
    }

    public void setLeftContent(Node content) {
        leftContent.add(content, 0, 0);
    }

    public void setRightContent(Node content) {
        rightContent.add(content, 0, 0);
    }

    public void setInnerTitle(String text) {
        title.setText(text);
    }

}
