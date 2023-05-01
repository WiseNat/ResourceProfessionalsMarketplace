package com.wise.resource.professionals.marketplace.component;

import com.wise.resource.professionals.marketplace.util.ComponentUtil;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.Getter;
import lombok.SneakyThrows;

import java.net.URL;

@Getter
public class NavbarButton extends Button {

    private final ComponentUtil componentUtil;

    private final ImageView image;

    public NavbarButton() {
        super();

        image = new ImageView();
        this.setGraphic(image);

        if (!this.getStyleClass().contains("navbar-button")) {
            this.getStyleClass().add("navbar-button");
        }

        componentUtil = new ComponentUtil();

        setOnMouseClicked(event -> setActive(true));
    }

    @SneakyThrows
    public void setImageUrl(URL url) {
        Image innerImage = new Image(url.toString());
        image.setImage(innerImage);
    }

    public void setActive(boolean isActive) {
        String ACTIVE_STYLE_CLASS = "navbar-button-active";

        if (isActive) {
            componentUtil.safeAddStyleClass(this, ACTIVE_STYLE_CLASS);
        } else {
            this.getStyleClass().remove(ACTIVE_STYLE_CLASS);
        }
    }
}
