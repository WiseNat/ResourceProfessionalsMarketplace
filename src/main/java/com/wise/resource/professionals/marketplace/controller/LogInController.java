package com.wise.resource.professionals.marketplace.controller;

import com.wise.resource.professionals.marketplace.application.StageHandler;
import com.wise.resource.professionals.marketplace.constant.AccountTypeEnum;
import com.wise.resource.professionals.marketplace.to.LogInAccountTO;
import com.wise.resource.professionals.marketplace.util.AccountUtil;
import com.wise.resource.professionals.marketplace.util.ValidatorUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.wise.resource.professionals.marketplace.constant.StyleEnum.NEGATIVE_CONTROL;

/**
 * Controller class for LogIn.fxml
 */
@Component
@FxmlView("LogIn.fxml")
public class LogInController {

    @Autowired
    private StageHandler stageHandler;

    @Autowired
    private AccountUtil accountUtil;

    @Autowired
    private ValidatorUtil validatorUtil;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private ChoiceBox<String> accountTypeField;

    @FXML
    public void initialize() {
        ObservableList<String> items = FXCollections.observableArrayList(AccountTypeEnum.getAllValues());

        accountTypeField.setItems(items);
        accountTypeField.setValue(items.get(0));
    }

    @FXML
    public void onHyperLinkClick() {
        stageHandler.swapScene(CreateAnAccountController.class);
    }

    /**
     * Method for when the login button is clicked. Shouldn't be directly called.
     * <p>
     * Calls {@link LogInController#login()}
     */
    @FXML
    public void onLogInButtonClick() {
        login();
    }

    /**
     * Attempts to log in a user with the current user inputs.
     * <p>
     * Populates a {@link LogInAccountTO} with the user inputs and authenticates this with
     * {@link AccountUtil#authenticate(LogInAccountTO)}. If the user is successfully authenticated then the scene is
     * swapped to the scene associated with their account type; otherwise the user input fields are marked.
     */
    private void login() {
        String email = emailField.getText();
        String password = passwordField.getText();
        String accountType = accountTypeField.getValue();

        LogInAccountTO loginAccount = new LogInAccountTO(email, password, AccountTypeEnum.valueToEnum(accountType));
        boolean isAuthenticated = accountUtil.authenticate(loginAccount);

        if (isAuthenticated) {
            MainView sceneController = accountUtil.getAccountViewController(loginAccount.getAccountType());
            Scene scene = new Scene(stageHandler.getFxWeaver().loadView(sceneController.getClass()));

            sceneController.setAccountTO(loginAccount);

            stageHandler.swapScene(scene);
        } else {
            validatorUtil.markControlNegative(emailField, NEGATIVE_CONTROL.value);
            validatorUtil.markControlNegative(accountTypeField, NEGATIVE_CONTROL.value);
            validatorUtil.markControlNegative(passwordField, NEGATIVE_CONTROL.value);
        }
    }
}