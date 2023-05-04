package com.wise.resource.professionals.marketplace.modules;

import com.wise.resource.professionals.marketplace.component.MinMaxField;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import lombok.Getter;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@Getter
@FxmlView("LoanSearch.fxml")
public class LoanSearch {
    @FXML
    private Label title;

    @FXML
    private ChoiceBox<String> mainRoleField;

    @FXML
    private ChoiceBox<String> subRoleField;

    @FXML
    private MinMaxField bandField;

    @FXML
    private MinMaxField costPerHourField;

    @FXML
    private Button applyButton;

    @FXML
    public void initialize() {
        bandField.setPromptText("Band");
        costPerHourField.setPromptText("Cost Per Hour");
    }
}
