package com.wise.resource.professionals.marketplace.component;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import lombok.Getter;
import lombok.SneakyThrows;

@Getter
public class ListBox extends BorderPane {

    @FXML
    private Label title;

    @FXML
    private Label leftSubtext;

    @FXML
    private Label rightSubtext;

    @SneakyThrows
    public ListBox() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ListBox.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.load();
    }

    public void setTitleText(String text) {
        title.setText(text);
    }

    public void setLeftSubtext(String text) {
        leftSubtext.setText(text);
    }

    public void setRightSubtext(String text) {
        rightSubtext.setText(text);
    }
}
