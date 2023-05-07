package com.wise.resource.professionals.marketplace.util;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.SkinBase;
import org.springframework.stereotype.Component;

@Component
public class ComponentUtil {
    public void safeAddStyleClass(Node node, String styleClass) {
        if (!node.getStyleClass().contains(styleClass)) {
            node.getStyleClass().add(styleClass);
        }
    }

    public <T> void setChoiceBoxPrompt(ChoiceBox<T> choiceBox, String promptText) {
        Platform.runLater(() -> {
            @SuppressWarnings("unchecked")
            SkinBase<ChoiceBox<T>> skin = (SkinBase<ChoiceBox<T>>) choiceBox.getSkin();
            for (Node child : skin.getChildren()) {
                if (child instanceof Label) {
                    Label label = (Label) child;
                    if (label.getText().isEmpty()) {
                        label.setText(promptText);
                    }
                    return;
                }
            }
        });
    }
}
