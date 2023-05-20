package com.wise.resource.professionals.marketplace.component;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.SneakyThrows;

/**
 * An extension of {@link VBox} which provides some simple helper methods.
 */
@Getter
public class ListView extends VBox {

    @SneakyThrows
    public ListView() {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ListView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.load();
    }

    /**
     * Removes all JavaFX child nodes within this {@code ListView}
     */
    public void clearAllChildren() {
        this.getChildren().clear();
    }

    /**
     * Adds a child node within this {@code ListView}
     *
     * @param node the node to be added. Can be any JavaFX component.
     */
    public void addChild(Node node) {
        this.getChildren().add(node);
    }
}
