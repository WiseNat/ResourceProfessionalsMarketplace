package com.wise.resource.professionals.marketplace.modules;

import com.wise.resource.professionals.marketplace.util.ComponentUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lombok.Getter;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Getter
@FxmlView("ReturnSearch.fxml")
public class ReturnSearch {

    @FXML
    private Label title;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField clientField;

    @FXML
    private ChoiceBox<String> mainRoleField;

    @FXML
    private ChoiceBox<String> subRoleField;

    @FXML
    private ChoiceBox<String> bandField;

    @FXML
    private TextField costPerHourField;

    @FXML
    private Button applyButton;

    @FXML
    private Button resetButton;

    @FXML
    public void initialize() {
    }

}
