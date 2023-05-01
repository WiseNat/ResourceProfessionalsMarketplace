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

        // TODO: Remove
        emailField.setText("dev@resource");
        passwordField.setText("password");
        accountTypeField.setValue(AccountTypeEnum.Resource.value);
    }

    @FXML
    public void onLogInButtonClick() {
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
            validatorUtil.markControlNegative(emailField, "negative-control");
            validatorUtil.markControlNegative(passwordField, "negative-control");
            validatorUtil.markControlNegative(accountTypeField, "negative-control");
            System.out.println("Invalid email or password");
        }

    }

    @FXML
    public void onHyperLinkClick() {
        stageHandler.swapScene(CreateAnAccountController.class);
    }
}