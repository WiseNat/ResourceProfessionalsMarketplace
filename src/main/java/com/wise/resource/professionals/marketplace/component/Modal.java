package com.wise.resource.professionals.marketplace.component;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.StageStyle;
import lombok.SneakyThrows;

import java.util.Objects;

import static javafx.scene.paint.Color.TRANSPARENT;

public class Modal extends DialogPane {

    private final Dialog<Boolean> dialog;
    @FXML
    private GridPane leftContent;
    @FXML
    private GridPane rightContent;
    @FXML
    private Label title;
    @FXML
    private Button closeButton;
    private Node[] blurNodes;

    @SneakyThrows
    public Modal() {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Modal.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.load();

        dialog = new Dialog<>();
        dialog.initStyle(StageStyle.TRANSPARENT);
        dialog.setDialogPane(this);

        this.getScene().getStylesheets().add(
                Objects.requireNonNull(getClass().getResource("../styles/application.css")).toExternalForm()
        );

        this.getScene().setFill(TRANSPARENT);

        closeButton.setOnMouseClicked(this::closeButtonClicked);
        this.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                this.closeDialog();
            }
        });
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
        BoxBlur blur = new BoxBlur();
        blur.setWidth(6);
        blur.setHeight(6);
        blur.setIterations(10);

        for (Node node : blurNodes) {
            node.setEffect(blur);
        }

        dialog.showAndWait();
    }

    public void setBlurNodes(Node[] nodes) {
        this.blurNodes = nodes;
    }

    public void closeButtonClicked(MouseEvent mouseEvent) {
        closeDialog();
    }

    public void closeDialog() {
        dialog.setResult(false);
        dialog.close();

        for (Node node : blurNodes) {
            node.setEffect(null);
        }
    }
}
