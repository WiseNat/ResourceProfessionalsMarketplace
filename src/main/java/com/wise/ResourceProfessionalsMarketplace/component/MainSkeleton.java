package com.wise.ResourceProfessionalsMarketplace.component;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@FxmlView("MainSkeleton.fxml")
public class MainSkeleton extends BorderPane {

    @FXML
    private VBox topNavbar;

    @FXML
    private Label title;

    @FXML
    private GridPane mainContent;

    @FXML
    public void initialize() {
    }

    public void setMainContent(Node content) {
        mainContent.add(content, 0, 0);
    }

    public void setTitle(String text) {
        title.setText(text);
    }
}
