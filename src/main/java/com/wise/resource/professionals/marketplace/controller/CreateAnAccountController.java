package com.wise.resource.professionals.marketplace.controller;

import com.wise.resource.professionals.marketplace.application.StageHandler;
import com.wise.resource.professionals.marketplace.constant.AccountTypeEnum;
import com.wise.resource.professionals.marketplace.repository.AccountRepository;
import com.wise.resource.professionals.marketplace.service.CreateAnAccountService;
import com.wise.resource.professionals.marketplace.to.CreateAccountTO;
import com.wise.resource.professionals.marketplace.util.ValidatorUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

import static com.wise.resource.professionals.marketplace.constant.StyleEnum.NEGATIVE_CONTROL;

/**
 * Controller class for CreateAnAccount.fxml
 */
@Component
@FxmlView("CreateAnAccount.fxml")
public class CreateAnAccountController {

    @Autowired
    private StageHandler stageHandler;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CreateAnAccountService createAnAccountService;

    @Autowired
    private ValidatorUtil validatorUtil;

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
    public void initialize() {
        ObservableList<String> items = FXCollections.observableArrayList(AccountTypeEnum.getAllValues());
        items.remove(AccountTypeEnum.ADMIN.value);

        accountTypeField.setItems(items);
        accountTypeField.setValue(items.get(0));
    }

    /**
     * Method for when the login hyperlink is clicked. Shouldn't be directly called.
     * <p>
     * This swaps the scene to the one associated with the {@link LogInController}
     */
    @FXML
    public void onHyperLinkClick() {
        stageHandler.swapScene(LogInController.class);
    }

    /**
     * Method for when the search button is clicked. Shouldn't be directly called.
     * <p>
     * Calls {@link CreateAnAccountController#createAccount()}
     */
    @FXML
    public void onCreateAccountButtonClick() {
        createAccount();
    }

    /**
     * Attempts to create a create an account request with the current user inputs.
     * <p>
     * Populates a {@link CreateAccountTO} with the user inputs and tries to create an account with using these details.
     * If the given details are invalid, then the violating fields are then marked. Otherwise, an account approval
     * request is created.
     */
    private void createAccount() {
        CreateAccountTO accountTO = new CreateAccountTO();
        accountTO.setFirstName(firstNameField.getText());
        accountTO.setLastName(lastNameField.getText());
        accountTO.setEmail(emailField.getText());
        accountTO.setPassword(passwordField.getText());
        accountTO.setAccountType(AccountTypeEnum.valueToEnum(accountTypeField.getValue()));

        String[] negativeFields = createAnAccountService.createAccount(accountTO);

        if (negativeFields.length > 0) {
            markFields(negativeFields);
            return;
        }

        stageHandler.swapScene(LogInController.class);
    }

    /**
     * Styles the given fields as negative. This is useful for validation.
     *
     * @param fields the fields to be marked.
     */
    private void markFields(String[] fields) {
        HashMap<String, Control> fieldToControl = new HashMap<String, Control>() {{
            put("firstName", firstNameField);
            put("lastName", lastNameField);
            put("email", emailField);
            put("password", passwordField);
            put("accountType", accountTypeField);
        }};

        validatorUtil.markControlAgainstValidatedTO(fields, fieldToControl, NEGATIVE_CONTROL.value);
    }
}