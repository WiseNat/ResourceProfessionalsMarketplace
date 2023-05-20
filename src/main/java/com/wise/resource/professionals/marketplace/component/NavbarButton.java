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

    @SneakyThrows
    public void setImageUrl(URL url) {
        Image innerImage = new Image(url.toString());
        image.setImage(innerImage);
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;

        if (isActive) {
            componentUtil.safeAddStyleClass(this, NAVBAR_BUTTON_ACTIVE.value);
        } else {
            this.getStyleClass().remove(NAVBAR_BUTTON_ACTIVE.value);
        }
    }
}
