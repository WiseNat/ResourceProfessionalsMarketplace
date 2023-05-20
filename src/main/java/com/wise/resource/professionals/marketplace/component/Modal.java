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
import javafx.stage.FileChooser;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import lombok.SneakyThrows;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import static com.wise.resource.professionals.marketplace.constant.ApplicationEnum.DEFAULT_STYLESHEET_PATH;
import static javafx.scene.paint.Color.TRANSPARENT;

/**
 * A custom JavaFX component which acts as a modal for popups. This modal blocks control for the application and is
 * only closable through the close button or through calling {@link Modal#closeModal}.
 * <p>
 * This also blurs given nodes, provides helper methods for setting content and the title, and provides an easy way
 * to save text content to a file.
 */
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

        this.getScene().getStylesheets().add(DEFAULT_STYLESHEET_PATH.value);
        this.getScene().setFill(TRANSPARENT);

        closeButton.setOnMouseClicked(this::closeButtonClicked);
        this.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                this.closeModal();
            }
        });
    }

    /**
     * Sets the left hand side content as the given node.
     *
     * @param content the node to be set to.
     */
    public void setLeftContent(Node content) {
        leftContent.add(content, 0, 0);
    }

    /**
     * Sets the right hand side content as the given node.
     *
     * @param content the node to be set to.
     */
    public void setRightContent(Node content) {
        rightContent.add(content, 0, 0);
    }

    /**
     * Sets the inner title text to the given text.
     *
     * @param text the text to be set to.
     */
    public void setInnerTitle(String text) {
        title.setText(text);
    }

    /**
     * Blurs the background and then shows the modal and waits for the user response.
     *
     * @see Dialog#showAndWait()
     */
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

    /**
     * Sets the nodes which will be blurred later when calling {@link Modal#showAndWait()}
     *
     * @param nodes an array of nodes to be later blurred.
     */
    public void setBlurNodes(Node[] nodes) {
        this.blurNodes = nodes;
    }

    /**
     * Method for when the close button is clicked. Shouldn't be directly called.
     * <p>
     * Calls {@link Modal#closeModal()}
     */
    public void closeButtonClicked(MouseEvent mouseEvent) {
        closeModal();
    }

    /**
     * Removes the blur effect from the {@link Modal#blurNodes} and closes the modal.
     */
    public void closeModal() {
        dialog.setResult(false);
        dialog.close();

        for (Node node : blurNodes) {
            node.setEffect(null);
        }
    }

    /**
     * Helper method for creating a save dialog, creating the chosen file and saving the given string content to it.
     *
     * @param window  the owner window of the displayed file dialog. Used in {@link FileChooser#showSaveDialog(Window)}
     * @param content the file content that will be saved.
     */
    @SneakyThrows
    protected void saveDetailsToFile(Window window, String content) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName("output.txt");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));

        File selectedFile = fileChooser.showSaveDialog(window);

        if (selectedFile != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(selectedFile))) {
                writer.write(content);
            }
        }
    }
}
