package com.wise.resource.professionals.marketplace.modules;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lombok.Getter;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@Getter
@FxmlView("ApprovalsSearch.fxml")
public class ApprovalsSearch {
    @FXML
    private Label title;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField emailField;

    @FXML
    private CheckBox resourceBox;

    @FXML
    private CheckBox projectManagerBox;

    @FXML
    private Button applyButton;

    @FXML
    public void initialize() {
    }
}
