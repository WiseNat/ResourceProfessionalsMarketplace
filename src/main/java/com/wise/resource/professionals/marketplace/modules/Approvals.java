package com.wise.resource.professionals.marketplace.modules;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import lombok.Getter;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@Getter
@FxmlView("Approvals.fxml")
public class Approvals {
    @FXML
    private VBox body;

    @FXML
    public void initialize() {
    }
}
