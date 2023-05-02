package com.wise.resource.professionals.marketplace.component;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.StageStyle;
import lombok.SneakyThrows;

import java.util.Objects;

import static javafx.scene.paint.Color.TRANSPARENT;

public class Modal<T> extends DialogPane {

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

    public void showAndWait() {
        Dialog<String> dialog = new Dialog<>();
        dialog.initStyle(StageStyle.TRANSPARENT);
        dialog.setDialogPane(this);

        this.getScene().getStylesheets().add(
                Objects.requireNonNull(getClass().getResource("../styles/application.css")).toExternalForm()
        );

        this.getScene().setFill(TRANSPARENT);

        dialog.showAndWait();
    }

}
