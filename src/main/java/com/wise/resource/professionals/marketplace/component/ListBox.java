package com.wise.resource.professionals.marketplace.component;

import com.wise.resource.professionals.marketplace.util.ComponentUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import lombok.Getter;
import lombok.SneakyThrows;

import java.net.URL;

@Getter
public class ListBox extends BorderPane {

    @FXML
    private Label title;

    @FXML
    private Label leftSubtext;

    @FXML
    private Label rightSubtext;

    @FXML
    private ImageView mainImage;

    @FXML
    private ImageView subImage;

    @SneakyThrows
    public ListBox() {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ListBox.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.load();
    }

    public void setTitleText(String text) {
        title.setText(text);
    }

    public void setLeftSubtext(String text) {
        leftSubtext.setText(text);
    }

    public void setRightSubtext(String text) {
        rightSubtext.setText(text);
    }

    @SneakyThrows
    public void setMainImageUrl(URL url) {
        setImageUrl(mainImage, url);
    }

    @SneakyThrows
    public void setSubImageUrl(URL url) {
        setImageUrl(subImage, url);
    }

    @SneakyThrows
    private void setImageUrl(ImageView image, URL url) {
        Image innerImage = new Image(url.toString());
        image.setImage(innerImage);
    }

    public void removeMainImage() {
        removeImage(mainImage);
    }

    public void removeSubImage() {
        removeImage(subImage);
    }

    private void removeImage(ImageView image) {
        ComponentUtil componentUtil = new ComponentUtil();
        componentUtil.removeNode(image);
    }

}
