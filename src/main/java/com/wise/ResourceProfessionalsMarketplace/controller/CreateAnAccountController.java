package com.wise.ResourceProfessionalsMarketplace.controller;

import com.wise.ResourceProfessionalsMarketplace.application.StageHandler;
import com.wise.ResourceProfessionalsMarketplace.constant.AccountTypeEnum;
import com.wise.ResourceProfessionalsMarketplace.entity.AccountEntity;
import com.wise.ResourceProfessionalsMarketplace.repository.AccountRepository;
import com.wise.ResourceProfessionalsMarketplace.to.CreateAccountTO;
import com.wise.ResourceProfessionalsMarketplace.util.AccountUtil;
import com.wise.ResourceProfessionalsMarketplace.util.CreateAccountUtil;
import com.wise.ResourceProfessionalsMarketplace.util.EnumUtil;
import com.wise.ResourceProfessionalsMarketplace.util.ValidatorUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.HashMap;
import java.util.Set;

@Component
@FxmlView("CreateAnAccount.fxml")
public class CreateAnAccountController {

    @Autowired
    StageHandler stageHandler;

    @Autowired
    Validator validator;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountUtil accountUtil;

    @Autowired
    CreateAccountUtil createAccountUtil;

    @Autowired
    ValidatorUtil validatorUtil;

    @Autowired
    EnumUtil enumUtil;

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

        CreateAccountTO accountTO = new CreateAccountTO();
        accountTO.setFirstName(firstNameField.getText());
        accountTO.setLastName(lastNameField.getText());
        accountTO.setEmail(emailField.getText());
        accountTO.setIsApproved(false);
        accountTO.setPassword(passwordField.getText());
        accountTO.setEncodedPassword(accountUtil.hashPassword(passwordField.getText()));
        accountTO.setAccountType(AccountTypeEnum.valueToEnum(accountTypeField.getValue()));

        if (accountTO.getAccountType() == AccountTypeEnum.Admin) {
            validatorUtil.markControlNegative(accountTypeField, "negative-control");
            return;
        } else {
            validatorUtil.markControlPositive(accountTypeField, "negative-control");
        }

        Set<ConstraintViolation<CreateAccountTO>> violations = validator.validate(accountTO);
        this.markTextFields(violations);

        if (violations.size() > 0) {
            return;
        }

        AccountEntity existingAccountEntity = accountRepository.findByEmailAndAccountType(
                accountTO.getEmail(), enumUtil.accountTypeToEntity(accountTO.getAccountType()));

        if (existingAccountEntity != null) {
            System.out.println("Account already exists. If you've already submitted a create account request then please wait.");
            return;
        }

        createAccountUtil.persistAccountAndApproval(accountTO);

        // TODO: Indicate to user an account approval has been created
        // TODO: Modal? Hint? Who knows. I don't atm because it's midnight and I'm tired
    }

    private void markTextFields(Set<ConstraintViolation<CreateAccountTO>> violations) {
        HashMap<String, Control> toFieldToControl = new HashMap<String, Control>() {{
            put("firstName", firstNameField);
            put("lastName", lastNameField);
            put("email", emailField);
            put("password", passwordField);
        }};

        validatorUtil.markControlAgainstValidatedTO(violations, toFieldToControl, "negative-control");
    }
}