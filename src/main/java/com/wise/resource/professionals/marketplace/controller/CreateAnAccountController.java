package com.wise.resource.professionals.marketplace.controller;

import com.wise.resource.professionals.marketplace.application.StageHandler;
import com.wise.resource.professionals.marketplace.constant.AccountTypeEnum;
import com.wise.resource.professionals.marketplace.repository.AccountRepository;
import com.wise.resource.professionals.marketplace.service.CreateAnAccountService;
import com.wise.resource.professionals.marketplace.to.CreateAccountTO;
import com.wise.resource.professionals.marketplace.util.EnumUtil;
import com.wise.resource.professionals.marketplace.util.ValidatorUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import lombok.SneakyThrows;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Validator;
import java.util.HashMap;

import static com.wise.resource.professionals.marketplace.constant.StyleEnum.NEGATIVE_CONTROL;

@Component
@FxmlView("CreateAnAccount.fxml")
public class CreateAnAccountController {

    @Autowired
    private StageHandler stageHandler;

    @Autowired
    private Validator validator;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CreateAnAccountService createAnAccountService;

    @Autowired
    private ValidatorUtil validatorUtil;

    @Autowired
    private EnumUtil enumUtil;

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
        ObservableList<String> values = FXCollections.observableArrayList(
                AccountTypeEnum.PROJECT_MANAGER.value, AccountTypeEnum.RESOURCE.value
        );

        accountTypeField.setItems(values);
        accountTypeField.setValue(values.get(0));
    }

    @FXML
    public void onHyperLinkClick() {
        stageHandler.swapScene(LogInController.class);
    }

    @FXML
    @SneakyThrows
    public void onCreateAccountButtonClick() {

        CreateAccountTO accountTO = new CreateAccountTO();
        accountTO.setFirstName(firstNameField.getText());
        accountTO.setLastName(lastNameField.getText());
        accountTO.setEmail(emailField.getText());
        accountTO.setPassword(passwordField.getText());
        accountTO.setAccountType(AccountTypeEnum.valueToEnum(accountTypeField.getValue()));

        String[] negativeFields = createAnAccountService.createAccount(accountTO);

        if (negativeFields.length > 0) {
            markTextFields(negativeFields);
            return;
        }

        stageHandler.swapScene(LogInController.class);
    }

    private void markTextFields(String[] fields) {
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