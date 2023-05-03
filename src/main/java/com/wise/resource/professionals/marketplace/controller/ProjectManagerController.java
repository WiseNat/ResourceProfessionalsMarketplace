package com.wise.resource.professionals.marketplace.controller;

import com.wise.resource.professionals.marketplace.component.NavbarButton;
import com.wise.resource.professionals.marketplace.modules.ListView;
import com.wise.resource.professionals.marketplace.modules.MainSkeleton;
import com.wise.resource.professionals.marketplace.to.LogInAccountTO;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import lombok.SneakyThrows;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@FxmlView("ProjectManagerView.fxml")
public class ProjectManagerController implements MainView {

    private final FxControllerAndView<MainSkeleton, BorderPane> mainSkeleton;
    private final FxControllerAndView<ListView, VBox> listView;


    public ProjectManagerController(
            FxControllerAndView<MainSkeleton, BorderPane> mainSkeleton,
            FxControllerAndView<ListView, VBox> listView) {
        this.mainSkeleton = mainSkeleton;
        this.listView = listView;

        this.mainSkeleton.getController().initialize();
    }

    @FXML
    @SneakyThrows
    private void initialize() {
        if (!(listView.getView().isPresent())) {
            throw new IllegalAccessException("A necessary view was not found");
        }

        mainSkeleton.getController().setMainContent(listView.getView().get());

        mainSkeleton.getController().setTitle("Loan an Individual");
        mainSkeleton.getController().removeSubtitle();

        NavbarButton navbarButton = mainSkeleton.getController().addNavbarButton(
                Objects.requireNonNull(getClass().getResource("../images/handshake.png")));
        navbarButton.setActive(true);

        mainSkeleton.getController().addNavbarButton(
                Objects.requireNonNull(getClass().getResource("../images/return.png")));

        // TODO: Populate ListView function
    }

    @Override
    public void setAccountTO(LogInAccountTO logInAccountTO) {

    }
}
