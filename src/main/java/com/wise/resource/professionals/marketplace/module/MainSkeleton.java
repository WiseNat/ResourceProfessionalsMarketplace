package com.wise.resource.professionals.marketplace.module;

import com.wise.resource.professionals.marketplace.application.StageHandler;
import com.wise.resource.professionals.marketplace.component.NavbarButton;
import com.wise.resource.professionals.marketplace.controller.LogInController;
import com.wise.resource.professionals.marketplace.util.ComponentUtil;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import lombok.Getter;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Objects;

/**
 * Controller class for the MainSkeleton.fxml module
 */
@Component
@Getter
@FxmlView("MainSkeleton.fxml")
public class MainSkeleton extends BorderPane {

    @Autowired
    private StageHandler stageHandler;

    @Autowired
    private ComponentUtil componentUtil;

    @FXML
    private VBox topNavbar;

    @FXML
    private Label title;

    @FXML
    private Label subtext;

    @FXML
    private GridPane mainContent;

    @FXML
    private GridPane rightContent;

    @FXML
    private NavbarButton logoutButton;

    @FXML
    private ScrollPane scrollpane;

    @FXML
    public void initialize() {
        logoutButton.setImageUrl(Objects.requireNonNull(getClass().getResource("../images/logout.png")));
    }

    /**
     * Sets the child of the {@link MainSkeleton#mainContent} as the given node, removing any existing nodes that
     * previously existed under it
     *
     * @param content the node that will be set.
     */
    public void setMainContent(Node content) {
        mainContent.getChildren().removeIf(n -> GridPane.getColumnIndex(n) == 0 && GridPane.getRowIndex(n) == 0);
        mainContent.add(content, 0, 0);
    }

    /**
     * Sets the child of the {@link MainSkeleton#rightContent} as the given node, removing any existing nodes that
     * previously existed under it
     *
     * @param content the node that will be set.
     */
    public void setRightContent(Node content) {
        rightContent.getChildren().removeIf(n -> GridPane.getColumnIndex(n) == 0 && GridPane.getRowIndex(n) == 0);
        rightContent.add(content, 0, 0);
    }

    /**
     * Sets the {@link MainSkeleton#title} text as the given text.
     *
     * @param text the text to be set to.
     */
    public void setTitle(String text) {
        this.setTitle(text, "");
    }

    /**
     * Sets the {@link MainSkeleton#title} text as the given main text and the {@link MainSkeleton#subtext} text as the
     * given sub text.
     *
     * @param mainText the main title text to be set.
     * @param subText  the sub heading title text to be set.
     */
    public void setTitle(String mainText, String subText) {
        title.setText(mainText);
        subtext.setText(subText);
    }

    /**
     * Removes the {@link MainSkeleton#subtext}.
     */
    public void removeSubtitle() {
        componentUtil.removeNode(subtext);
    }

    /**
     * Removes the {@link MainSkeleton#rightContent}.
     */
    public void removeRightContent() {
        componentUtil.removeNode(rightContent);
    }

    /**
     * Adds a {@link NavbarButton} using the {@code url} when setting the image.
     *
     * @param url a url to an image.
     * @return the created {@link NavbarButton}
     */
    public NavbarButton addNavbarButton(URL url) {

        NavbarButton navbarButtonComponent = new NavbarButton();
        navbarButtonComponent.setImageUrl(url);

        topNavbar.getChildren().add(navbarButtonComponent);

        return navbarButtonComponent;
    }

    /**
     * "Logs out" of the given scene. This just swaps to the {@link LogInController} view.
     */
    public void logout() {
        stageHandler.swapScene(LogInController.class);
    }

}
