package com.wise.resource.professionals.marketplace.modules;

import com.wise.resource.professionals.marketplace.component.NavbarButton;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import lombok.Getter;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Objects;

@Component
@Getter
@FxmlView("MainSkeleton.fxml")
public class MainSkeleton extends BorderPane {

    @FXML
    private VBox topNavbar;

    @FXML
    private Label title;

    @FXML
    private Label subtext;

    @FXML
    private GridPane mainContent;

    @FXML
    private NavbarButton logoutButton;

    @FXML
    public void initialize() {
        logoutButton.setImageUrl(Objects.requireNonNull(getClass().getResource("../images/logout.png")));
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

        NavbarButton navbarButtonComponent = new NavbarButton();
        navbarButtonComponent.setImageUrl(url);

        topNavbar.getChildren().add(navbarButtonComponent);

        return navbarButtonComponent;
    }

}
