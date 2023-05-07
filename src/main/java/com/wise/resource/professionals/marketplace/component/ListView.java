package com.wise.resource.professionals.marketplace.component;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.SneakyThrows;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

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
