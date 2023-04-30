package com.wise.ResourceProfessionalsMarketplace.component;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import lombok.Getter;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Date;
import java.util.Objects;

@Component
@Getter
@FxmlView("MainSkeleton.fxml")
public class MainSkeletonComponent extends BorderPane {

    @FXML
    private VBox topNavbar;

    @FXML
    private Label title;

    @FXML
    private Label subtext;

    @FXML
    private GridPane mainContent;

    private final FxWeaver fxWeaver;

    private final FxControllerAndView<NavbarButton, Button> logoutButton;

    public MainSkeletonComponent(FxWeaver fxWeaver, FxControllerAndView<NavbarButton, Button> logoutButton) {
        this.fxWeaver = fxWeaver;

        this.logoutButton = logoutButton;
        this.logoutButton.getController().initialize();
    }

    @FXML
    public void initialize() {
        logoutButton.getController().setImageUrl(Objects.requireNonNull(getClass().getResource("../images/logout.png")));
    }

    public void setMainContent(Node content) {
        mainContent.add(content, 0, 0);
    }

    public void setTitle(String mainText, String subText) {
        title.setText(mainText);
        subtext.setText(subText);
    }

    public void setTitle(String text) {
        this.setTitle(text, "");
    }

    public NavbarButton addNavbarButton(URL url) {

//        NavbarButtonComponent navbarButtonComponent = fxWeaver.loadController(NavbarButtonComponent.class);
        NavbarButton navbarButtonComponent = new NavbarButton();
        navbarButtonComponent.setText(url.toString());
//        navbarButtonComponent.setImageUrl(url);

        topNavbar.getChildren().add(navbarButtonComponent);

        return navbarButtonComponent;
    }

}
