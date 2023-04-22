package com.wise.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LogInController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button logInButton;

    @FXML
    public void onLogInButtonClick() {
        System.out.println("foo");
    }
}