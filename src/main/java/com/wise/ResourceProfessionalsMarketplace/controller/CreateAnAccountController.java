package com.wise.ResourceProfessionalsMarketplace.controller;

import com.wise.ResourceProfessionalsMarketplace.application.StageHandler;
import com.wise.ResourceProfessionalsMarketplace.constant.AccountTypeEnum;
import com.wise.ResourceProfessionalsMarketplace.to.AccountTO;
import com.wise.ResourceProfessionalsMarketplace.util.PasswordUtil;
import com.wise.ResourceProfessionalsMarketplace.util.ValidatorUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
@FxmlView("CreateAnAccount.fxml")
public class CreateAnAccountController {

    @Autowired
    private Validator validator;

    @Autowired
    private ValidatorUtil validatorUtil;

    @Autowired
    private PasswordUtil passwordUtil;

    @Autowired
    private StageHandler stageHandler;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField passwordField;

    @FXML
    private ChoiceBox<String> accountTypeField;

    @FXML
    private Button createAccountButton;

    @FXML
    private Hyperlink hyperlink;

    @FXML
    public void initialize() {
        ObservableList<String> values = FXCollections.observableArrayList(
                AccountTypeEnum.ProjectManager.value, AccountTypeEnum.Resource.value
        );

        accountTypeField.setItems(values);
        accountTypeField.setValue(values.get(0));
    }

    @FXML
    public void onHyperLinkClick() {
        stageHandler.swapScene(LogInController.class);
    }

    @FXML
    public void onCreateAccountButtonClick() {
        AccountTO accountTO = new AccountTO();
        accountTO.setFirstName(firstNameField.getText());
        accountTO.setLastName(lastNameField.getText());
        accountTO.setEmail(emailField.getText());
        accountTO.setIs_approved(false);
        accountTO.setPassword(passwordField.getText(), passwordUtil.hashPassword(passwordField.getText()));

        Set<ConstraintViolation<AccountTO>> violations = validator.validate(accountTO);

        HashMap<TextField, Boolean> textFieldViolations = new HashMap<TextField, Boolean>() {{
            put(firstNameField, false);
            put(lastNameField, false);
            put(emailField, false);
            put(passwordField, false);
        }};

        // Invalid Data
        if (violations.size() > 0) {
            for (ConstraintViolation<AccountTO> violation : violations) {
                String field = validatorUtil.getFieldFromConstraintViolation(violation);

                switch (field) {
                    case "firstName":
                        textFieldViolations.replace(firstNameField, true);
                        break;
                    case "lastName":
                        textFieldViolations.replace(lastNameField, true);
                        break;
                    case "email":
                        textFieldViolations.replace(emailField, true);
                        break;
                    case "password":
                    case "encodedPassword":
                        textFieldViolations.replace(passwordField, true);
                        break;
                }
            }

            final String NEGATIVE_TEXT_FIELD = "negative-text-field";

            for (Map.Entry<TextField, Boolean> entry : textFieldViolations.entrySet()) {
                TextField textField = entry.getKey();
                Boolean hasViolation = entry.getValue();

                if (!hasViolation) {
                    textField.getStyleClass().remove(NEGATIVE_TEXT_FIELD);
                } else if (!textField.getStyleClass().contains(NEGATIVE_TEXT_FIELD)) {
                    textField.getStyleClass().add(NEGATIVE_TEXT_FIELD);
                }
            }
        }
        // Valid Data
        else {
            System.out.println("VALID");
        }

        // Check if account exists (for given account type)

        // Check if approval exists (for given account type)

        // Create Approval + Account
    }

    private void validate() {

    }
}