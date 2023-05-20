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

/**
 * A custom JavaFX component that is used as the crux of complicated list views. It allows setting various properties
 * such as images (main and sub), title text, and subtexts (left and right).
 */
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

    /**
     * Sets the text for the title of this component.
     *
     * @param text text to be set to.
     */
    public void setTitleText(String text) {
        title.setText(text);
    }

    /**
     * Sets the text for the left subtext.
     *
     * @param text text to be set to.
     */
    public void setLeftSubtext(String text) {
        leftSubtext.setText(text);
    }

    /**
     * Sets the text for the right subtext.
     *
     * @param text text to be set to.
     */
    public void setRightSubtext(String text) {
        rightSubtext.setText(text);
    }


    /**
     * Sets the main image as the given image.
     *
     * @param image the image to be set to.
     */
    public void setMainImage(Image image) {
        mainImage.setImage(image);
    }

    /**
     * Sets the sub image as the given image.
     *
     * @param image the image to be set to.
     */
    public void setSubImage(Image image) {
        subImage.setImage(image);
    }

    /**
     * Removes the main image from the ListBox
     */
    public void removeMainImage() {
        removeImage(mainImage);
    }

    /**
     * Removes the sub image from the ListBox
     */
    public void removeSubImage() {
        removeImage(subImage);
    }

    /**
     * Removes a given image from the ListBox.
     *
     * @param image the image within ListBox that should be removed
     */
    private void removeImage(ImageView image) {
        ComponentUtil componentUtil = new ComponentUtil();
        componentUtil.removeNode(image);
    }

}
