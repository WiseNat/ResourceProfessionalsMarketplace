package com.wise.ResourceProfessionalsMarketplace.controller;

import com.wise.ResourceProfessionalsMarketplace.application.StageHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@FxmlView("CreateAnAccount.fxml")
public class CreateAnAccountController {

    @Autowired
    private StageHandler stageHandler;

    @FXML
    private Hyperlink hyperlink;

    @FXML
    public void onHyperLinkClick() {
        stageHandler.swapScene(LogInController.class);
    }
}