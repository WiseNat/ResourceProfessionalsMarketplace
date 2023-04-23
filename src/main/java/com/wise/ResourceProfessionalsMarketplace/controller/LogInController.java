package com.wise.ResourceProfessionalsMarketplace.controller;

import com.wise.ResourceProfessionalsMarketplace.application.StageHandler;
import com.wise.ResourceProfessionalsMarketplace.entity.Account;
import com.wise.ResourceProfessionalsMarketplace.repository.AccountRepository;
import com.wise.ResourceProfessionalsMarketplace.util.PasswordUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
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
    private AccountRepository accountRepository;

    @Autowired
    private PasswordUtil passwordUtil;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button logInButton;

    @FXML
    private Hyperlink hyperlink;


    private boolean validate() {
        String email = emailField.getText();
        String password = passwordField.getText();

        Account account = accountRepository.findAccountByEmail(email);

        if (account == null) {
            return false;
        }

        String encodedPassword = account.getHash();

        return passwordUtil.authenticate(password, encodedPassword);
    }

    @FXML
    public void onLogInButtonClick() {
        boolean valid = this.validate();

        System.out.println(valid);
    }

    @FXML
    public void onHyperLinkClick() {
        stageHandler.swapScene(CreateAnAccountController.class);
    }

}