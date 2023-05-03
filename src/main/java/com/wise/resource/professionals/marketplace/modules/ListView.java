package com.wise.resource.professionals.marketplace.modules;

import com.wise.resource.professionals.marketplace.component.ListBox;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import lombok.Getter;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@Getter
@FxmlView("ListView.fxml")
public class ListView {
    @FXML
    private VBox body;

    @FXML
    public void initialize() {
    }

    public void clearAllChildren() {
        body.getChildren().clear();
    }

    public void addChild(Node node) {
        body.getChildren().add(node);
    }
}
