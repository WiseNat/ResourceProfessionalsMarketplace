package com.wise.resource.professionals.marketplace.component;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.SneakyThrows;

@Getter
public class ListView extends VBox {

    @SneakyThrows
    public ListView() {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ListView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.load();
    }

    public void clearAllChildren() {
        this.getChildren().clear();
    }

    public void addChild(Node node) {
        this.getChildren().add(node);
    }
}
