package com.wise.resource.professionals.marketplace.controller;

import com.wise.resource.professionals.marketplace.modules.MainSkeleton;
import com.wise.resource.professionals.marketplace.to.LogInAccountTO;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import lombok.SneakyThrows;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@FxmlView("ProjectManagerView.fxml")
public class ProjectManagerController implements MainView {


    private final FxControllerAndView<MainSkeleton, BorderPane> mainSkeleton;

    public ProjectManagerController(
            FxControllerAndView<MainSkeleton, BorderPane> mainSkeleton) {
        this.mainSkeleton = mainSkeleton;
        this.mainSkeleton.getController().initialize();
    }

    @FXML
    @SneakyThrows
    private void initialize() {

    }

    @Override
    public void setAccountTO(LogInAccountTO logInAccountTO) {

    }
}
