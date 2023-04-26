package com.wise.ResourceProfessionalsMarketplace.controller;

import com.wise.ResourceProfessionalsMarketplace.application.StageHandler;
import com.wise.ResourceProfessionalsMarketplace.constant.AccountTypeEnum;
import com.wise.ResourceProfessionalsMarketplace.entity.AccountEntity;
import com.wise.ResourceProfessionalsMarketplace.entity.AccountTypeEntity;
import com.wise.ResourceProfessionalsMarketplace.repository.AccountRepository;
import com.wise.ResourceProfessionalsMarketplace.repository.AccountTypeRepository;
import com.wise.ResourceProfessionalsMarketplace.util.PasswordUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@FxmlView("LogIn.fxml")
public class LogInController {
    // TODO: Add AccountType to login

    @Autowired
    private StageHandler stageHandler;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountTypeRepository accountTypeRepository;

    @Autowired
    private PasswordUtil passwordUtil;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private ChoiceBox<String> accountTypeField;

    @FXML
    private Button logInButton;

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
    public void onLogInButtonClick() {
        // TODO: Change to just use javax bean validation...
        String email = emailField.getText();
        String password = passwordField.getText();
        String accountType = accountTypeField.getValue();

        AccountTypeEntity accountTypeEntity = accountTypeRepository.findAccountTypeByString(accountType);

        AccountEntity account = accountRepository.findAccountByEmailAndAccountType(email, accountTypeEntity);

        if (account != null) {
            String encodedPassword = accountRepository.

            passwordUtil.authenticate(password, "");
        } else {
            System.out.println("No account exists");
            // Raise error?
        }

        String encodedPassword = account.getEncodedPassword();

        return passwordUtil.authenticate(password, encodedPassword);
    }

    @FXML
    public void onHyperLinkClick() {
        stageHandler.swapScene(CreateAnAccountController.class);
    }
}