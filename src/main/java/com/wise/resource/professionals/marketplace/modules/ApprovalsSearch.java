package com.wise.resource.professionals.marketplace.modules;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
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
    private Button resetButton;

    @FXML
    private Button applyButton;

    @FXML
    public void initialize() {
        resetButton.setOnMouseClicked(this::resetButtonClicked);
    }

    private void resetButtonClicked(MouseEvent mouseEvent) {
        resetFields();
    }

    private void resetFields() {
        firstNameField.setText("");
        lastNameField.setText("");
        emailField.setText("");

        resourceBox.setSelected(true);
        projectManagerBox.setSelected(true);
    }
}
