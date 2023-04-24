package com.wise.ResourceProfessionalsMarketplace.controller;

import com.wise.ResourceProfessionalsMarketplace.application.StageHandler;
import com.wise.ResourceProfessionalsMarketplace.constant.AccountTypeEnum;
import com.wise.ResourceProfessionalsMarketplace.to.AccountTO;
import com.wise.ResourceProfessionalsMarketplace.util.PasswordUtil;
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
import java.util.Arrays;
import java.util.Set;

@Component
@FxmlView("CreateAnAccount.fxml")
public class CreateAnAccountController {

    @Autowired
    private Validator validator;

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
        accountTO.setEncodedPassword(passwordUtil.hashPassword(passwordField.getText()));

        Set<ConstraintViolation<AccountTO>> violations = validator.validate(accountTO);
        if (violations.size() > 0) {
            System.out.println("ERRORS");
            for (ConstraintViolation<AccountTO> violation : violations) {
                System.out.println(violation.getMessage());
            }
        } else {
            System.out.println("VALID");
        }

        // Check if account exists (for given account type)

        // Check if approval exists (for given account type)

        // Create Approval + Account
    }

    private void validate() {

    }
}