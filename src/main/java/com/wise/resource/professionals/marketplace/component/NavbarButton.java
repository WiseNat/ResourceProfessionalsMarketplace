package com.wise.resource.professionals.marketplace.component;

import com.wise.resource.professionals.marketplace.util.ComponentUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.Getter;
import lombok.SneakyThrows;

import java.net.URL;

import static com.wise.resource.professionals.marketplace.constant.StyleEnum.NAVBAR_BUTTON_ACTIVE;

/**
 * A button which is used within the navigation bar.
 */
@Getter
public class NavbarButton extends Button {

    private final ComponentUtil componentUtil;

    @FXML
    private ImageView image;

    private boolean isActive;

    @SneakyThrows
    public NavbarButton() {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("NavbarButton.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.load();

        this.isActive = false;

        componentUtil = new ComponentUtil();
    }

    /**
     * Sets the image within the navbar button as the image found at the given URL.
     *
     * @param url url to an image. This is used when creating an {@link Image}.
     */
    @SneakyThrows
    public void setImageUrl(URL url) {
        Image innerImage = new Image(url.toString());
        image.setImage(innerImage);
    }

    /**
     * Changes the {@link NavbarButton#isActive} to the given value. This is used to determine styling.
     *
     * @param isActive
     */
    public void setActive(boolean isActive) {
        this.isActive = isActive;

        if (isActive) {
            componentUtil.safeAddStyleClass(this, NAVBAR_BUTTON_ACTIVE.value);
        } else {
            this.getStyleClass().remove(NAVBAR_BUTTON_ACTIVE.value);
        }
    }
}
