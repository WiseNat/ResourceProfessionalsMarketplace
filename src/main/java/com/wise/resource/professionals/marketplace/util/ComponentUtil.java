package com.wise.resource.professionals.marketplace.util;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.Pane;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

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
            SkinBase<?> skin = (SkinBase<?>) choiceBox.getSkin();
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

    public void removeNode(Node node) {
        ((Pane) node.getParent()).getChildren().remove(node);
    }

    public String formatBigDecimal(BigDecimal bigDecimal) {
        return String.format("%,.2f", bigDecimal.setScale(2, RoundingMode.HALF_EVEN));
    }
}
