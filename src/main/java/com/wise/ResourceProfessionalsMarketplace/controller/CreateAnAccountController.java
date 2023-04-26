package com.wise.ResourceProfessionalsMarketplace.controller;

import com.wise.ResourceProfessionalsMarketplace.application.StageHandler;
import com.wise.ResourceProfessionalsMarketplace.constant.AccountTypeEnum;
import com.wise.ResourceProfessionalsMarketplace.entity.AccountEntity;
import com.wise.ResourceProfessionalsMarketplace.entity.AccountTypeEntity;
import com.wise.ResourceProfessionalsMarketplace.entity.ApprovalEntity;
import com.wise.ResourceProfessionalsMarketplace.repository.AccountRepository;
import com.wise.ResourceProfessionalsMarketplace.repository.AccountTypeRepository;
import com.wise.ResourceProfessionalsMarketplace.repository.ApprovalRepository;
import com.wise.ResourceProfessionalsMarketplace.to.AccountTO;
import com.wise.ResourceProfessionalsMarketplace.to.ApprovalTO;
import com.wise.ResourceProfessionalsMarketplace.util.AccountUtil;
import com.wise.ResourceProfessionalsMarketplace.util.ComponentUtil;
import com.wise.ResourceProfessionalsMarketplace.util.PersistUtil;
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
import java.sql.Date;
import java.util.HashMap;
import java.util.Set;

@Component
@FxmlView("CreateAnAccount.fxml")
public class CreateAnAccountController {

    @Autowired
    private Validator validator;

    @Autowired
    private StageHandler stageHandler;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountTypeRepository accountTypeRepository;

    @Autowired
    private ApprovalRepository approvalRepository;

    @Autowired
    private ValidatorUtil validatorUtil;

    @Autowired
    private PersistUtil persistUtil;

    @Autowired
    private ComponentUtil componentUtil;

    @Autowired
    private AccountUtil accountUtil;

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
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String accountType = accountTypeField.getValue();

        if (AccountTypeEnum.valueToEnum(accountType) == AccountTypeEnum.Admin) {
            componentUtil.markControlNegative(accountTypeField, "negative-control");
            return;
        } else {
            componentUtil.markControlPositive(accountTypeField, "negative-control");
        }

        AccountTypeEntity accountTypeEntity = accountTypeRepository.findByName(accountType);
        AccountTO accountTO = new AccountTO();
        accountTO.setFirstName(firstName);
        accountTO.setLastName(lastName);
        accountTO.setEmail(email);
        accountTO.setIs_approved(false);
        accountTO.setPassword(password, accountUtil.hashPassword(password));
        accountTO.setAccountType(accountTypeEntity);

        // TODO: Move validation to Control side rather than relying on Bean validation?
        Set<ConstraintViolation<AccountTO>> violations = validator.validate(accountTO);

        if (violations.size() > 0) {
            this.markTextFieldsInvalid(violations);
            return;
        }

        AccountEntity existingAccountEntity = accountRepository.findByEmailAndAccountType(email, accountTypeEntity);

        if (existingAccountEntity != null) {
            System.out.println("Account already exists. If you've already submitted a create account request then please wait.");
            return;
            // raise error?
        }

        AccountEntity accountEntity = persistUtil.persistTo(accountTO, new AccountEntity(), accountRepository);

        ApprovalTO approvalTO = new ApprovalTO();
        approvalTO.setAccount(accountEntity);
        approvalTO.setDate(new Date(System.currentTimeMillis()));

        persistUtil.persistTo(approvalTO, new ApprovalEntity(), approvalRepository);

        // TODO: Indicate to user an account approval has been created
        // TODO: Modal? Hint? Who knows. I don't atm because it's midnight and I'm tired
    }

    private void markTextFieldsInvalid(Set<ConstraintViolation<AccountTO>> violations) {
        HashMap<String, Control> toFieldToControl = new HashMap<String, Control>() {{
            put("firstName", firstNameField);
            put("lastName", lastNameField);
            put("email", emailField);
            put("password", passwordField);
        }};

        componentUtil.markControlAgainstValidatedTO(violations, toFieldToControl, "negative-control");
    }
}