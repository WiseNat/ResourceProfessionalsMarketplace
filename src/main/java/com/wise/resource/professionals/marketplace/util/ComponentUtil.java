package com.wise.resource.professionals.marketplace.util;

import javafx.scene.Node;
import org.springframework.stereotype.Component;

@Component
public class ComponentUtil {
    public void safeAddStyleClass(Node node, String styleClass) {
        if (!node.getStyleClass().contains(styleClass)) {
            node.getStyleClass().add(styleClass);
        }
    }
}
