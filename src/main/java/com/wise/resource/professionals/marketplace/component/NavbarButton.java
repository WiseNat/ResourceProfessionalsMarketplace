package com.wise.resource.professionals.marketplace.component;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.Getter;
import lombok.SneakyThrows;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.net.URL;

@Component
@Getter
@FxmlView("NavbarButton.fxml")
public class NavbarButton extends Button {

    @FXML
    private ImageView image;

    @FXML
    private Button button;

    public NavbarButton() {
        initialize();
    }

    @FXML
    public void initialize() {
        if (!this.getStyleClass().contains("navbar-button")) {
            this.getStyleClass().add("navbar-button");
        }
    }

    @SneakyThrows
    public void setImageUrl(URL url) {
        Image innerImage = new Image(url.toString());
        image.setImage(innerImage);
    }
}
