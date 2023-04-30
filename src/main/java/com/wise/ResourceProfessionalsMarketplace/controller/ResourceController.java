package com.wise.ResourceProfessionalsMarketplace.controller;

import com.wise.ResourceProfessionalsMarketplace.application.StageHandler;
import com.wise.ResourceProfessionalsMarketplace.component.MainSkeleton;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@FxmlView("ResourceView.fxml")
public class ResourceController {

    @Autowired
    private FxWeaver fxWeaver;

    @Autowired
    private StageHandler stageHandler;

    private final FxControllerAndView<MainSkeleton, BorderPane> mainSkeleton;
    private final FxControllerAndView<UpdateDetails, VBox> updateDetails;


    public ResourceController(FxWeaver fxWeaver, FxControllerAndView<MainSkeleton, BorderPane> mainSkeleton, FxControllerAndView<UpdateDetails, VBox> updateDetails) {
        this.fxWeaver = fxWeaver;
        this.mainSkeleton = mainSkeleton;
        this.updateDetails = updateDetails;

        this.mainSkeleton.getController().initialize();
    }

    @FXML
    public void initialize() {
        mainSkeleton.getController().setTitle("FOO");

        // TODO: Figure out which line is better...
//        mainSkeleton.getController().setMainContent(stageHandler.getScene(UpdateDetails.class).getRoot());
//        mainSkeleton.getController().setMainContent(fxWeaver.loadView(UpdateDetails.class));
        mainSkeleton.getController().setMainContent(updateDetails.getView().get());
    }
}
